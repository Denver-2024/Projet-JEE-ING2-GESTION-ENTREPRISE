package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.JpaUtil;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeDAO {
    private EntityManagerFactory sessionFactory;

    public EmployeDAO() {
        sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public Employe fetchEmploye(int id_employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            return em.createQuery("select e from Employe e where e.id = :id_employe", Employe.class)
                    .setParameter("id_employe", id_employe)
                    .getSingleResult();

        } catch (NoResultException ex) {
            System.out.println("No employe found with id = " + id_employe);
            return null;
        } finally {
            em.close();
        }

    }
    public Employe fetchNewEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try{
            return em.createQuery("select e from Employe e where e.nom = :nom AND e.prenom= :prenom and e.adresse= :adresse and e.email= :email and e.grade = :grade and e.sexe = :sexe and e.role.id_role= :id_role and e.id_departement= :id_departement and e.numero= :numero and e.salaire= :salaire", Employe.class)
                    .setParameter("nom", employe.getNom()).setParameter("prenom", employe.getPrenom()).setParameter("adresse", employe.getAdresse()).setParameter("email", employe.getEmail()).setParameter("grade",employe.getGrade()).setParameter("sexe",employe.getSexe())
                    .setParameter("id_role", employe.getRole().getId_role())
                    .setParameter("id_departement",employe.getId_departement())
                    .setParameter("numero",employe.getNumero())
                    .setParameter("salaire",employe.getSalaire())
                    .getSingleResult();

        }catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public void saveEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            // First transaction: insert the employe so its row exists
            em.getTransaction().begin();
            em.persist(employe);
            em.getTransaction().commit(); // EMPLOYEE IS NOW IN DATABASE



        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;

        } finally {
            em.close();
        }
    }

    public void deleteEmploye(int id_employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            int deleted = em.createQuery("DELETE FROM Employe e WHERE e.id = :id")
                    .setParameter("id", id_employe)
                    .executeUpdate();

            if (deleted > 0) {
                System.out.println("Employe deleted: " + id_employe);
            } else {
                System.out.println("Employe not found: " + id_employe);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void updateEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            em.merge(employe);  // updates existing row in DB

            em.getTransaction().commit();
            System.out.println("Employe updated successfully: " + employe.getId_employe());

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Failed to update employe: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            em.close();
        }
    }

    public List<Employe> rechercheEmployes(String nom, String prenom, int id_departement, Grade grade, int id_role) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        StringBuilder sb = new StringBuilder("SELECT e FROM Employe e WHERE 1=1 ");
        Map<String, Object> params = new HashMap<>();

        if (nom != null && !nom.isBlank()) {
            sb.append("AND e.nom LIKE :nom ");
            params.put("nom", "%" + nom + "%");
        }

        if (prenom != null && !prenom.isBlank()) {
            sb.append("AND e.prenom LIKE :prenom ");
            params.put("prenom", "%" + prenom + "%");
        }

        if (id_departement > 0) {
            sb.append("AND e.id_departement = :dept ");
            params.put("dept", id_departement);
        }

        if (grade != null) {
            sb.append("AND e.grade = :grade ");
            params.put("grade", grade);
        }

        if (id_role > 0) {
            sb.append("AND e.role.id = :role ");
            params.put("role", id_role);
        }

        TypedQuery<Employe> query = em.createQuery(sb.toString(), Employe.class);

        params.forEach(query::setParameter);

        return query.getResultList();
    }


    public void promoteToChefDeDepartement(Employe newChef) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            int departementId = newChef.getId_departement();
            // 1. Fetch current chef of this department
            Employe currentChef = em.createQuery("SELECT e FROM Employe e WHERE e.id_departement = :dep AND e.role.id_role = 3",
                            Employe.class)
                    .setParameter("dep", departementId)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            // 2. If a chef exists, demote them to chef_de_projet (role 4)
            if (currentChef != null && currentChef.getId_employe() != newChef.getId_employe()) {
                Role newRoleChefProjet = em.find(Role.class, 4);
                currentChef.setRole(newRoleChefProjet);
                em.merge(currentChef);
            }
            // 3. Promote selected employee to chef_de_departement (role 3)
            Role chefRole = em.find(Role.class, 3);
            newChef.setRole(chefRole);
            em.merge(newChef);
            // 4. Update DEPARTEMENT table → set id_employe (chef)
            em.createQuery("UPDATE Departement d SET d.directeur.id_employe = :chefId WHERE d.id_departement = :dep")
                    .setParameter("chefId", newChef.getId_employe())
                    .setParameter("dep", departementId)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void updateChef(int id_departement, int id_employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        try {
            em.getTransaction().begin();

            // Old chef
            Employe oldChef = em.createQuery(
                            "SELECT e FROM Employe e WHERE e.id_departement = :d AND e.role.id_role = 3",
                            Employe.class
                    ).setParameter("d", id_departement)
                    .getResultStream().findFirst().orElse(null);

            if (oldChef != null && oldChef.getId_employe() != id_employe) {
                oldChef.setRole(em.find(Role.class, 4));
                em.merge(oldChef);
            }

            // New chef
            Employe newChef = em.find(Employe.class, id_employe);
            if (newChef == null) {
                throw new RuntimeException("New chef not found in DB!");
            }
            newChef.setRole(em.find(Role.class, 3));
            em.merge(newChef);

            // Update department
            em.createQuery("UPDATE Departement d SET d.directeur.id_employe = :id WHERE d.id_departement = :dep")
                    .setParameter("id", id_employe)
                    .setParameter("dep", id_departement)
                    .executeUpdate();

            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }

    }
}