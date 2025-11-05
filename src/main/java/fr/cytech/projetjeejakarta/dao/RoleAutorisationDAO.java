package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.model.RoleAutorisation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class RoleAutorisationDAO {
    private EntityManagerFactory sessionFactory;

    public RoleAutorisationDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("RoleAutorisation");
    }

    public RoleAutorisation fetchDepartement(int id_roleAutorisation){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            RoleAutorisation roleAutorisation =(RoleAutorisation) em.createQuery("select e from RoleAutorisation e where e.id = :id_roleAutorisation")
                    .setParameter("id_roleAutorisation",id_roleAutorisation)
                    .getSingleResult();

            return roleAutorisation;

        }catch (NoResultException ex){
            System.out.println("No roleAutorisation found with id = " + id_roleAutorisation);
            return null;
        }




    }
}
