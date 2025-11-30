package fr.cytech.projetjeejakarta.dao;

import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.FicheDePaie;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.*;


import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.*;
import java.io.FileOutputStream;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;


public class FicheDePaieDAO {
    private EntityManagerFactory sessionFactory;

    // Cotisation percentages
    private static final double COTISATIONSALARIALE  = 0.10;
    private static final double COTISATIONPATRONALE = 0.18;
    private static final double COUNTABSENCE = 0.032;
    public FicheDePaieDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public FicheDePaie rechercherFicheDePaie(int id_fiche_de_paie){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("select f from FicheDePaie f where f.id = :id_fiche_de_paie",FicheDePaie.class)
                    .setParameter("id_fiche_de_paie",id_fiche_de_paie)
                    .getSingleResult();

        }catch (NoResultException ex){
            System.out.println("No fiche de paie  found with id = " + id_fiche_de_paie);
            return null;
        } finally {
            em.close();
        }

    }
    public List<FicheDePaie> rechercheFicheDePaiePeriode(Employe employe,Date startDate, Date endDate){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Employe managedEmploye = em.find(Employe.class, employe.getId_employe());

        try {
            return em.createQuery("SELECT f FROM FicheDePaie f WHERE f.employe.id_employe = :id AND f.dateFiche BETWEEN :startDate AND :endDate"
                    ,FicheDePaie.class)
                    .setParameter("id", managedEmploye.getId_employe())
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();

        }catch (NoResultException ex){
            return null;
        }
        finally {
            em.close();
        }

    }

    public FicheDePaie findByEmployeeAndMonth(EntityManager em, int empId, int month, int year) {
        try {
            return em.createQuery(
                            "SELECT f FROM FicheDePaie f WHERE f.employe.id_employe = :id " +
                                    "AND FUNCTION('MONTH', f.dateFiche) = :m " +
                                    "AND FUNCTION('YEAR', f.dateFiche) = :y",
                            FicheDePaie.class)
                    .setParameter("id", empId)
                    .setParameter("m", month)
                    .setParameter("y", year)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

    public void saveOrUpdatePrime(Employe employe, Month month, int primeValue) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        int empId = employe.getId_employe();
        int monthNumber = month.getValue();
        int year = java.time.LocalDate.now().getYear();

        // Check existing fiche
        FicheDePaie fiche = findByEmployeeAndMonth(em, empId, monthNumber, year);

        if (fiche == null) {
            // Create new fiche
            fiche = new FicheDePaie();

            Employe managedEmp = em.find(Employe.class, empId);

            fiche.setEmploye(managedEmp);

            LocalDate lastDay = LocalDate.of(year, monthNumber, 1)
                    .withDayOfMonth(LocalDate.of(year, monthNumber, 1).lengthOfMonth());
            fiche.setDateFiche(Date.valueOf(lastDay));

            fiche.setSalaire_base(managedEmp.getSalaire());
            fiche.setCotisation_salariale(managedEmp.getSalaire() * COTISATIONSALARIALE);
            fiche.setCotisation_patronale(managedEmp.getSalaire() * COTISATIONPATRONALE);
            fiche.setPrime(primeValue);

            em.persist(fiche);
        } else {
            // Update existing prime
            double updatedPrime = fiche.getPrime() + primeValue;
            fiche.setPrime(updatedPrime);
            em.merge(fiche);
        }

        em.getTransaction().commit();
        em.close();
    }

    public int generateMonthlyFiches() {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();

        try {
            LocalDate today = LocalDate.now();
            YearMonth prevMonth = YearMonth.from(today).minusMonths(1);

            LocalDate start = prevMonth.atDay(1);
            LocalDate end = prevMonth.atEndOfMonth();

            List<Employe> employees = em.createQuery(
                    "SELECT e FROM Employe e", Employe.class
            ).getResultList();

            int processedCount = 0;

            for (Employe emp : employees) {

                // Find existing fiche for that employee and month
                TypedQuery<FicheDePaie> q = em.createQuery(
                        "SELECT f FROM FicheDePaie f " +
                                "WHERE f.employe.id_employe = :empId " +
                                "AND f.dateFiche = :dateFiche",
                        FicheDePaie.class
                );

                q.setParameter("empId", emp.getId_employe());
                q.setParameter("dateFiche", java.sql.Date.valueOf(end));

                List<FicheDePaie> existingList = q.getResultList();

                // Count absences for the month
                Long absenceCount = em.createQuery(
                                "SELECT COUNT(a) FROM Absence a " +
                                        "WHERE a.employe.id_employe = :empId " +
                                        "AND a.id.date BETWEEN :start AND :end",
                                Long.class
                        )
                        .setParameter("empId", emp.getId_employe())
                        .setParameter("start", java.sql.Date.valueOf(start))
                        .setParameter("end", java.sql.Date.valueOf(end))
                        .getSingleResult();

                if (!existingList.isEmpty()) {
                    // FICHE ALREADY EXISTS → Update absence count
                    FicheDePaie fiche = existingList.get(0);
                    fiche.setNombre_absences(absenceCount.intValue());
                    em.merge(fiche);

                } else {
                    // CREATE NEW FICHE
                    int salaireBase = emp.getSalaire();

                    FicheDePaie fiche = new FicheDePaie();
                    fiche.setEmploye(emp);
                    fiche.setDateFiche(java.sql.Date.valueOf(end));
                    fiche.setSalaire_base(salaireBase);
                    fiche.setCotisation_salariale(salaireBase * COTISATIONSALARIALE);
                    fiche.setCotisation_patronale(salaireBase * COTISATIONPATRONALE);
                    fiche.setPrime(0.0);
                    fiche.setNombre_absences(absenceCount.intValue());

                    em.persist(fiche);
                }

                processedCount++;
            }

            em.getTransaction().commit();
            return processedCount;

        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive())
                em.getTransaction().rollback();
            throw ex;

        } finally {
            em.close();
        }
    }


    public byte[] generateFicheDePaie(String logoPath, String signaturePath, Employe employe, FicheDePaie fiche) {
        Color BLUE_HEADER = new Color(210, 230, 255);
        Color ROW_ZEBRA = new Color(245, 245, 245);
        Color TOTAL_GREY = new Color(230, 230, 230);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            // Activer le gestionnaire de pagination
            FooterPageEvent event = new FooterPageEvent();
            writer.setPageEvent(event);

            document.open();

            // ---------------------------------------
            // 1. LOGO + TITRE
            // ---------------------------------------
            PdfPTable titleTable = new PdfPTable(2);
            titleTable.setWidthPercentage(100);
            titleTable.setWidths(new float[]{1, 4});

            Image logo = Image.getInstance(logoPath);
            logo.scaleToFit(100, 100);
            PdfPCell logoCell = new PdfPCell(logo, false);
            logoCell.setBorder(Rectangle.NO_BORDER);
            logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

            Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD);
            Paragraph title = new Paragraph("FICHE DE PAIE", titleFont);
            title.setAlignment(Element.ALIGN_LEFT);

            PdfPCell titleCell = new PdfPCell(title);
            titleCell.setBorder(Rectangle.NO_BORDER);
            titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);

            titleTable.addCell(logoCell);
            titleTable.addCell(titleCell);

            document.add(titleTable);
            document.add(new Paragraph("\n"));

            // ---------------------------------------
            // 2. HEADER TABLE (Employeur + période)
            // ---------------------------------------
            PdfPTable headerTable = new PdfPTable(2);
            headerTable.setWidthPercentage(100);

            PdfPCell employerCell = new PdfPCell(new Phrase("Employeur : FoodNCo\n\nAdresse : 55 Rue du Faubourg Saint-Honoré, 75008 Paris"));
            employerCell.setPadding(8);
            employerCell.setBorderWidth(1);

            LocalDate ficheDate = fiche.getDateFiche().toLocalDate();
            LocalDate firstDay = ficheDate.withDayOfMonth(1);

            PdfPCell periodCell = new PdfPCell(new Phrase("Période: du " + firstDay + " au " + fiche.getDateFiche()));
            periodCell.setPadding(8);
            periodCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            periodCell.setBorderWidth(1);

            headerTable.addCell(employerCell);
            headerTable.addCell(periodCell);

            document.add(headerTable);
            document.add(new Paragraph("\n"));

            // ---------------------------------------
            // 3. EMPLOYEE INFORMATION TABLE
            // ---------------------------------------
            PdfPTable employeeTable = new PdfPTable(2);
            employeeTable.setWidthPercentage(100);
            employeeTable.setSpacingAfter(15);

            addCell(employeeTable, "Nom :", true);
            addCell(employeeTable, employe.getNom(), false);

            addCell(employeeTable, "Prénom :", true);
            addCell(employeeTable, employe.getPrenom(), false);

            addCell(employeeTable, "Adresse :", true);
            addCell(employeeTable, employe.getAdresse(), false);

            addCell(employeeTable, "Grade :", true);
            addCell(employeeTable, employe.getGrade().toString(), false);

            addCell(employeeTable, "Poste :", true);
            addCell(employeeTable, employe.getRole().getNom(), false);

            document.add(employeeTable);

            // ---------------------------------------
            // 4. SALARY BREAKDOWN TABLE
            // ---------------------------------------
            PdfPTable payTable = new PdfPTable(4);
            payTable.setWidthPercentage(100);
            payTable.setWidths(new float[]{40, 20, 20, 20});
            payTable.setSpacingBefore(15);

            String[] headers = {"Cotisation / Élément", "Base (€)", "Taux (%)", "Montant (€)"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(BLUE_HEADER);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                cell.setBorderWidth(1);
                payTable.addCell(cell);
            }

            AtomicInteger rowIndex = new AtomicInteger(0);
            BiConsumer<String[], Boolean> addRow = (vals, bold) -> {
                Font font = bold ? new Font(Font.HELVETICA, 12, Font.BOLD) : new Font(Font.HELVETICA, 11);
                Color bg = (rowIndex.getAndIncrement() % 2 == 0) ? ROW_ZEBRA : Color.WHITE;
                for (String val : vals) {
                    PdfPCell c = new PdfPCell(new Phrase(val, font));
                    c.setBackgroundColor(bg);
                    c.setPadding(7);
                    c.setBorderWidth(1);
                    payTable.addCell(c);
                }
            };

            addRow.accept(new String[]{"Salaire Brut", format(fiche.getSalaire_base()), "-", format(fiche.getSalaire_base())}, false);
            addRow.accept(new String[]{"Prime", format(fiche.getPrime()), "-", format(fiche.getPrime())}, false);
            addRow.accept(new String[]{"Sécurité Sociale (URSSAF)", format(fiche.getSalaire_base()), format(COTISATIONSALARIALE), "-" + format(fiche.getCotisation_salariale())}, false);
            addRow.accept(new String[]{"CSG / CRDS", format(fiche.getSalaire_base()), format(COTISATIONPATRONALE), "-" + format(fiche.getCotisation_patronale())}, false);
            addRow.accept(new String[]{"Assurance Chômage", format(fiche.getSalaire_base()), "4.05", "-" + format(fiche.getSalaire_base() * 0.0405)}, false);
            addRow.accept(new String[]{"Absence (" + fiche.getNombre_absences() + " jours)", "-", "-", "-" + format(fiche.getNombre_absences() * COUNTABSENCE)}, false);

            PdfPCell subtotal = new PdfPCell(new Phrase("NET IMPOSABLE", new Font(Font.HELVETICA, 12, Font.BOLD)));
            subtotal.setColspan(4);
            subtotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
            subtotal.setBackgroundColor(TOTAL_GREY);
            subtotal.setPadding(10);
            subtotal.setBorderWidth(1);
            payTable.addCell(subtotal);

            document.add(payTable);

            // ---------------------------------------
            // 5. FINAL NET PAY
            // ---------------------------------------
            double total = (fiche.getSalaire_base() + fiche.getPrime())
                    - fiche.getCotisation_salariale()
                    - fiche.getCotisation_patronale()
                    - (fiche.getNombre_absences() * (fiche.getSalaire_base() * COUNTABSENCE))
                    - (fiche.getSalaire_base() * 0.0405);

            Paragraph net = new Paragraph("NET À PAYER : " + format(total) + " €", new Font(Font.HELVETICA, 16, Font.BOLD));
            net.setAlignment(Element.ALIGN_RIGHT);
            net.setSpacingBefore(20);
            document.add(net);

            // ---------------------------------------
            // 6. SIGNATURE
            // ---------------------------------------
            PdfPTable signatureTable = new PdfPTable(1);
            signatureTable.setWidthPercentage(40);
            signatureTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            signatureTable.setSpacingBefore(3);

            PdfPCell label = new PdfPCell(new Phrase("Signature de l'employeur"));
            label.setPadding(10);
            label.setHorizontalAlignment(Element.ALIGN_CENTER);
            label.setBackgroundColor(BLUE_HEADER);
            label.setBorderWidth(1);
            signatureTable.addCell(label);

            Image signature = Image.getInstance(signaturePath);
            signature.scaleToFit(140, 60);

            PdfPCell signCell = new PdfPCell(signature, false);
            signCell.setPadding(8);
            signCell.setBorderWidth(1);
            signatureTable.addCell(signCell);

            document.add(signatureTable);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


// Classe interne pour la pagination
// ---------------------------------------
static class FooterPageEvent extends PdfPageEventHelper {
            PdfTemplate total;
            Font font = new Font(Font.HELVETICA, 10);

            @Override
            public void onOpenDocument(PdfWriter writer, Document document) {
                total = writer.getDirectContent().createTemplate(25, 16);
            }

            @Override
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                Phrase footer = new Phrase("Page " + writer.getPageNumber() + " / ", font);
                ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer,
                        document.right() - 100, document.bottom() - 20, 0);
                cb.addTemplate(total, document.right() - 50, document.bottom() - 20);
            }

            @Override
            public void onCloseDocument(PdfWriter writer, Document document) {
                Phrase totalPages = new Phrase(String.valueOf(writer.getPageNumber() - 1), font);
                ColumnText.showTextAligned(total, Element.ALIGN_LEFT, totalPages, 0, 0, 0);
            }
        }

        // ---------------------------------------
        // Utility methods
        // ---------------------------------------

        private String format(double v) {
            return String.format("%.2f", v);
        }

        private void addCell(PdfPTable table, String text, boolean bold) {
            Font font = bold ? new Font(Font.HELVETICA, 12, Font.BOLD) : new Font(Font.HELVETICA, 12);
            PdfPCell cell = new PdfPCell(new Phrase(text, font));
            cell.setPadding(8);
            cell.setBorderWidth(1);
            table.addCell(cell);
        }

        private void addHeaderCell(PdfPTable table, String text) {
            Font font = new Font(Font.HELVETICA, 12, Font.BOLD);
            PdfPCell cell = new PdfPCell(new Phrase(text, font));
            cell.setBackgroundColor(Color.LIGHT_GRAY);
            cell.setPadding(8);
            cell.setBorderWidth(1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

}