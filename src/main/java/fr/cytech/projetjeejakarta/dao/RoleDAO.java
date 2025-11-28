package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class RoleDAO {

    public Role fetchRole(int id_role){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("select e from Role e where e.id = :id_role", Role.class)
                    .setParameter("id_role", id_role)
                    .getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("No role found with id = " + id_role);
            return null;
        } finally {
            em.close();
        }
    }

    public List<Role> getAllRoles() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("SELECT r FROM Role r", Role.class)
                    .getResultList();
        } catch (NoResultException ex) {
            System.out.println("No Roles found");
            return List.of();
        } finally {
            em.close();
        }
    }
}
