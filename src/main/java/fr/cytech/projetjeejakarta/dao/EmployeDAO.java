/*package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

public class EmployeDAO {
    private EntityManagerFactory sessionFactory;

    public EmployeDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("Projet");
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

    public Employe findById(int id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
*/