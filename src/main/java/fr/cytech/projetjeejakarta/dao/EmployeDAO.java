package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class EmployeDAO {

    // Créer ou modifier un employé
    public void creerOuModifierEmploye(Employe e) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(e);
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
    }

    // Supprimer un employé par ID
    public void supprimerEmploye(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Employe e = em.find(Employe.class, id);
            if (e != null) {
                em.remove(e);
            }
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
    }

    // Afficher tous les employés (avec relations chargées)
    public List<Employe> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes;
        try {
            employes = em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement LEFT JOIN FETCH e.role",
                    Employe.class
            ).getResultList();
        } finally {
            em.close();
        }
        return employes != null ? employes : Collections.emptyList();
    }

    // Rechercher un employé par son Id
    public Employe rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Employe e;
        try {
                e = em.createQuery(
                        "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.id_employe = :id",
                        Employe.class
                ).setParameter("id", id)
                .getSingleResult();

        } finally {
            em.close();
        }
        return e;
    }

    // Rechercher un employé par nom
    public List<Employe> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> e;
        try {
            e = em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.nom = :nom",
                    Employe.class
            ).setParameter("nom", nom).getResultList();
        } finally {
            em.close();
        }
        return e != null ? e : Collections.emptyList();
    }

    // Lister les projets d’un employé (sécurisé avec JOIN FETCH)
    public List<Projet> listeProjets(int idEmploye) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets;
        try {
            Employe e = em.createQuery(
                            "SELECT e FROM Employe e LEFT JOIN FETCH e.projets WHERE e.id_employe = :idEmploye",
                            Employe.class
                    ).setParameter("idEmploye", idEmploye)
                    .getSingleResult();

            projets = e.getProjets() != null ? List.copyOf(e.getProjets()) : Collections.emptyList();
        } finally {
            em.close();
        }
        return projets;
    }

    // Ajouter un projet à un employé (relation N,N cohérente)
    public void ajouterProjet(int idEmploye, int idProjet) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Employe employe = em.find(Employe.class, idEmploye);
            Projet projet = em.find(Projet.class, idProjet);

            if (employe == null || projet == null) {
                throw new IllegalArgumentException("Employé ou projet introuvable");
            }

            employe.getProjets().add(projet);
            projet.getEmployes().add(employe);

            em.merge(employe);
            em.merge(projet);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    // Enlever un projet d’un employé (relation N,N cohérente)
    public void enleverProjet(int idEmploye, int idProjet) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employe employe = em.find(Employe.class, idEmploye);
            Projet projet = em.find(Projet.class, idProjet);

            if (employe == null || projet == null) {
                throw new IllegalArgumentException("Employé ou projet introuvable");
            }

            employe.getProjets().remove(projet);
            projet.getEmployes().remove(employe);

            em.merge(employe);
            em.merge(projet);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
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