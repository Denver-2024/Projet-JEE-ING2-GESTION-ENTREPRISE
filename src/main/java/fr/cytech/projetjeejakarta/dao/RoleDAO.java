package fr.cytech.projetmodel.dao;

import fr.cytech.projetmodel.model.Employe;
import fr.cytech.projetmodel.model.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;

public class RoleDAO {
    private EntityManagerFactory sessionFactory;

    public RoleDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("Projet");
    }

    public Role fetchRole(int id_role){
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        try {
            Role role =(Role) em.createQuery("select e from Role e where e.id = :id_role")
                    .setParameter("id_role",id_role)
                    .getSingleResult();

            return role;

        }catch (NoResultException ex){
            System.out.println("No role found with id = " + id_role);
            return null;
        }




    }
}
