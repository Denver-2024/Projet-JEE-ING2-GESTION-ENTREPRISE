package fr.cytech.projetjeejakarta.dao;


import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class DepartementDAO {
    private EntityManagerFactory sessionFactory;
    public DepartementDAO() {

            sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");

    }
    public Departement fetchDepartement(int id_departement){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("select e from Departement e where e.id = :id_departement", Departement.class)
                    .setParameter("id_departement", id_departement)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No departement found with id = " + id_departement);
            return null;
        } finally {
            em.close();
        }
    }

    public List<Departement> getAllDepartements() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery(
                            "SELECT d FROM Departement d LEFT JOIN FETCH d.directeur",
                            Departement.class)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No Departement found");
            return List.of();
        } finally {
            em.close();
        }
    }
}
