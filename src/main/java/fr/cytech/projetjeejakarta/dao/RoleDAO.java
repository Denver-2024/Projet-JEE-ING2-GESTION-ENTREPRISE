package fr.cytech.projetjeejakarta.dao;


import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.List;


public class RoleDAO {


    public Role rechercherRole(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Role role = null;

        try {
            role = em.find(Role.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return role;
    }

    public List<Role> getAllRoles() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Role> roles = null;
        try {
            roles= em.createQuery("SELECT r FROM Role r", Role.class)
                    .getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();

        } finally {
            em.close();
        }
        return roles;
    }





}
