package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.enumeration.EtatProjet;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class ProjetDAO {

    public ProjetDAO() {

       EntityManagerFactory  sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    public void creerOuModifierProjet(Projet p) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(p); // equivalent de saveOrUptade de hibernate
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void supprimerProjet(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Projet p=em.find(Projet.class,id);
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




    public List<Projet> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets = null;
        try {
            projets = em.createQuery("SELECT p FROM Projet p", Projet.class)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return projets;
    }

    public Projet rechercherProjetParID(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Projet projet = null;
        try {
            projet=em.find(Projet.class,id);

        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return projet;
    }


    public List<Projet> rechercherProjets(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets = null;
        try{
            projets=em.createQuery("SELECT p FROM Projet p WHERE p.nom=:nom", Projet.class)
                    .setParameter("nom", nom)
                    .getResultList();
        }
        catch(Exception except){
            except.printStackTrace();
        }
        finally {
            em.close();
        }
        return projets;
    }



    public EtatProjet etatProjet(String nom) {
        Projet p=rechercherProjets(nom).getFirst();
        return (p!=null)? p.getEtat():null;

    }

    public Employe chefProjet(String nom){
        Projet p=rechercherProjets(nom).getFirst();
        return (p!=null)? p.getChefDeProjet():null;
    }

    public String descriptionProjet(String nom){
        Projet p=rechercherProjets(nom).getFirst();
        return (p!=null)? p.getDescription():null;
    }

    public Departement departementProjet(String nom){
        Projet p=rechercherProjets(nom).getFirst();
        return (p!=null)? p.getDepartement():null;
    }

}
