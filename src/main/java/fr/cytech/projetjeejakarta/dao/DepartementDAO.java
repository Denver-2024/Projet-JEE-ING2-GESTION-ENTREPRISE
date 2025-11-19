/*package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class DepartementDAO {
    private EntityManagerFactory sessionFactory;

    public DepartementDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("Projet");
    }

    public Departement fetchDepartement(int id_departement){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Departement departement =(Departement) em.createQuery("select e from Departement e where e.id = :id_departement")
                    .setParameter("id_departement",id_departement)
                    .getSingleResult();

            return departement;

        }catch (NoResultException ex){
            System.out.println("No departement found with id = " + id_departement);
            return null;
        }




    }
}
*/