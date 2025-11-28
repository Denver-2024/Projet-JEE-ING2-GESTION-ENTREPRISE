package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Absence;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class AbsenceDAO {
    private EntityManagerFactory sessionFactory;
    ;

    public AbsenceDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public int logAbsenceExcludeWeekends(Employe employe, Date sqlStart, Date sqlEnd) throws Exception {

        LocalDate start = sqlStart.toLocalDate();
        LocalDate end = sqlEnd.toLocalDate();


        int insertedCount = 0;
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Employe managedEmploye = em.find(Employe.class, employe.getId_employe());

        EntityTransaction tx = em.getTransaction();



        try {
            tx.begin();

            LocalDate current = start;

            while (!current.isAfter(end)) {

                // Skip Saturday & Sunday
                DayOfWeek dow = current.getDayOfWeek();
                if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) {
                    current = current.plusDays(1);
                    continue;
                }

                // Check if absence already exists
                Long count = em.createQuery(
                                "SELECT COUNT(a) FROM Absence a WHERE a.employe.id_employe = :empId AND a.id.date = :day",
                                Long.class
                        )
                        .setParameter("empId",managedEmploye.getId_employe())
                        .setParameter("day", current)
                        .getSingleResult();

                if (count == 0) {
                    // Insert new Absence entity
                    Absence absence = new Absence(managedEmploye,Date.valueOf(current));



                    em.persist(absence);
                    insertedCount++;
                }

                current = current.plusDays(1);
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return insertedCount;
    }
}
