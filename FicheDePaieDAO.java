package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.FicheDePaie;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


public class FicheDePaieDAO {
    private EntityManagerFactory sessionFactory;

    // Cotisation percentages
    private static final double COTISATIONSALARIALE  = 0.10;
    private static final double COTISATIONPATRONALE = 0.18;
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




}
