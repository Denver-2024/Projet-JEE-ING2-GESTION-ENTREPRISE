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
