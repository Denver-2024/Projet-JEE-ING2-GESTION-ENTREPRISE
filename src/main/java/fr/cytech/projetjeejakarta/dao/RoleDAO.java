package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

public class RoleDAO {
    private EntityManagerFactory sessionFactory;

    public RoleDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("Role");
    }

    public Role fetchDepartement(int id_role) {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Role role = (Role) em.createQuery("select e from Role e where e.id = :id_role")
                    .setParameter("id_role", id_role)
                    .getSingleResult();

            return role;

        } catch (NoResultException ex) {
            System.out.println("No role found with id = " + id_role);
            return null;
        }
    }

    public Role findById(int id){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
