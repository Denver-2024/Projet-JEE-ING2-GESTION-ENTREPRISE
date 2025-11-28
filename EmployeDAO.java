package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class EmployeDAO {
    private EntityManagerFactory sessionFactory;

    public EmployeDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public Employe fetchEmploye(int id_employe){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("select e from Employe e where e.id = :id_employe",Employe.class)
                    .setParameter("id_employe",id_employe)
                    .getSingleResult();

        }catch (NoResultException ex){
            System.out.println("No employe found with id = " + id_employe);
            return null;
        } finally {
            em.close();
        }

    }

    public void saveEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employe);
            em.getTransaction().commit();
            System.out.println("Employe saved successfully: " + employe);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Failed to save employe: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void deleteEmploye(int id_employe){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            int deleted = em.createQuery("DELETE FROM Employe e WHERE e.id = :id")
                    .setParameter("id", id_employe)
                    .executeUpdate();

            if (deleted > 0) {
                System.out.println("Employe deleted: " + id_employe);
            } else {
                System.out.println("Employe not found: " + id_employe);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }

    }
    public void updateEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(employe);  // updates existing row in DB

            em.getTransaction().commit();
            System.out.println("Employe updated successfully: " + employe.getId_employe());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Failed to update employe: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }
}