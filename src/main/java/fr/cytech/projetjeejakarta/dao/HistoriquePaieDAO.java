package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.HistoriquePaie;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class HistoriquePaieDAO {

    public boolean existsSuccessForMonth(int year, int month) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            String jpql = "SELECT COUNT(h) FROM HistoriquePaie h " +
                    "WHERE FUNCTION('YEAR', h.dateExecution) = :year " +
                    "AND FUNCTION('MONTH', h.dateExecution) = :month " +
                    "AND h.status = 'SUCCESS'";

            Long count = em.createQuery(jpql, Long.class)
                    .setParameter("year", year)
                    .setParameter("month", month)
                    .getSingleResult();

            return count > 0;

        } finally {
            em.close();
        }
    }

    public void saveRun(boolean success) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            HistoriquePaie h = new HistoriquePaie();
            h.setDateExecution(new java.sql.Date(System.currentTimeMillis()));
            h.setStatus(success ? "SUCCESS" : "FAILED");

            em.persist(h);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


}