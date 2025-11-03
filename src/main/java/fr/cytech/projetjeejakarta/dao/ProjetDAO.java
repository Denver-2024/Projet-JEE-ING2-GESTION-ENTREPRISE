package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Projet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class ProjetDAO {
    private EntityManagerFactory sessionFactory;

    public ProjetDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("Projet");
    }

    public Projet fetchDepartement(int id_projet){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Projet projet =(Projet) em.createQuery("select e from Projet e where e.id = :id_projet")
                    .setParameter("id_projet",id_projet)
                    .getSingleResult();

            return projet;

        }catch (NoResultException ex){
            System.out.println("No projet found with id = " + id_projet);
            return null;
        }




    }
}
