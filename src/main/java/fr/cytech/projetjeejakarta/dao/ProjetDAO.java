package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.enumeration.EtatProjet;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class ProjetDAO {

    //Créer ou modifier un projet
    public void creerOuModifierProjet(Projet p) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(p); // équivalent de saveOrUpdate
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
        } finally {
            em.close();
        }
    }

    //Supprimer un projet
    public void supprimerProjet(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Projet p = em.find(Projet.class, id);
            if (p != null) {
                em.remove(p);
            }
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
        } finally {
            em.close();
        }
    }

    //Afficher tous les projets (avec relations chargées)
    public List<Projet> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefDeProjet LEFT JOIN FETCH p.departement",
                    Projet.class
            ).getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    //Rechercher projet par ID (avec relations)
    public Projet rechercherProjetParID(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Projet> projets = em.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefDeProjet LEFT JOIN FETCH p.departement WHERE p.idProjet = :id",
                    Projet.class
            ).setParameter("id", id).getResultList();

            return projets.isEmpty() ? null : projets.get(0);
        } catch (Exception except) {
            except.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    //Rechercher projets par nom (avec relations)
    public List<Projet> rechercherProjets(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefDeProjet LEFT JOIN FETCH p.departement WHERE p.nom = :nom",
                    Projet.class
            ).setParameter("nom", nom).getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    //Obtenir l'état d'un projet
    public EtatProjet etatProjet(String nom) {
        List<Projet> projets = rechercherProjets(nom);
        return !projets.isEmpty() ? projets.get(0).getEtat() : null;
    }

    //Obtenir le chef de projet
    public Employe chefProjet(String nom) {
        List<Projet> projets = rechercherProjets(nom);
        return !projets.isEmpty() ? projets.get(0).getChefDeProjet() : null;
    }

    //Obtenir la description
    public String descriptionProjet(String nom) {
        List<Projet> projets = rechercherProjets(nom);
        return !projets.isEmpty() ? projets.get(0).getDescription() : null;
    }

    //Obtenir le département
    public Departement departementProjet(String nom) {
        List<Projet> projets = rechercherProjets(nom);
        return !projets.isEmpty() ? projets.get(0).getDepartement() : null;
    }
}
