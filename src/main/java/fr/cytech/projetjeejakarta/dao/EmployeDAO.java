package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.HibernateUtil;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

public class EmployeDAO {
    private EntityManagerFactory sessionFactory;

    public EmployeDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public Employe fetchEmploye(int id_employe) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Employe employe = (Employe) em.createQuery("select e from Employe e where e.id = :id_employe")
                    .setParameter("id_employe", id_employe)
                    .getSingleResult();

            return employe;

        } catch (NoResultException ex) {
            System.out.println("No cinema found with id = " + id_employe);
            return null;
        }
    }

    public Employe findById(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Employe employe = null;

        try {
            employe = em.find(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return employe;
    }

    public void update(Employe employe) {
        EntityManager em = sessionFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(employe); // Utilise merge() pour la mise à jour
            em.getTransaction().commit();
            System.out.println("Employé mis à jour: " + employe.getId_employe());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
