package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.FicheDePaie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class FicheDePaieDAO {
    private EntityManagerFactory sessionFactory;

    public FicheDePaieDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("FicheDePaie");
    }

    public FicheDePaie fetchFicheDePaie(int id_ficheDePaie){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            FicheDePaie ficheDePaie =(FicheDePaie) em.createQuery("select e from FicheDePaie e where e.id = :id_ficheDePaie")
                    .setParameter("id_ficheDePaie",id_ficheDePaie)
                    .getSingleResult();

            return ficheDePaie;

        }catch (NoResultException ex){
            System.out.println("No ficheDePaie found with id = " + id_ficheDePaie);
            return null;
        }




    }
}
