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
            throw new RuntimeException(except);
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
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
    }

    //Afficher tous les projets (avec relations chargées)
    public List<Projet> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets=null;
        try {
            projets= em.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefDeProjet LEFT JOIN FETCH p.departement",
                    Projet.class
            ).getResultList();
        } catch (Exception except) {
            throw new RuntimeException(except);

        } finally {
            em.close();
        }
        return  projets;
    }

    //Rechercher projet par ID (avec relations)
    public Projet rechercherProjetParID(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Projet projet=null;
        try {
            projet = em.find(Projet.class, id);
        } catch (Exception except) {
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
        return projet;
    }

    //Rechercher projets par nom (avec relations)
    public List<Projet> rechercherProjetsParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets=null;
        try {
             projets=em.createQuery(
                    "SELECT p FROM Projet p LEFT JOIN FETCH p.chefDeProjet LEFT JOIN FETCH p.departement WHERE p.nom = :nom",
                    Projet.class
            ).setParameter("nom", nom).getResultList();
        } catch (Exception except) {
            throw new  RuntimeException(except);
        } finally {
            em.close();
        }
        return  projets;
    }


    public List<Employe> listEmployesProjet(int idProjet) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes = Collections.emptyList();
        try {
            Projet projet = em.createQuery(
                            "SELECT p FROM Projet p LEFT JOIN FETCH p.employes WHERE p.idProjet = :idProjet",
                            Projet.class
                    ).setParameter("idProjet", idProjet)
                    .getSingleResult();

            employes = projet.getEmployes() != null ? List.copyOf(projet.getEmployes()) : Collections.emptyList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
        return employes;
    }


    public void affecterEmploye(int idEmploye, int idProjet){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employe employe = em.find(Employe.class, idEmploye);
            Projet projet = em.find(Projet.class, idProjet);

            if (employe == null || projet == null) {
                throw new IllegalArgumentException("Employé ou projet introuvable");
            }

            // Mettre à jour les deux côtés
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

    public void enleverEmploye(int idEmploye, int idProjet){
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
