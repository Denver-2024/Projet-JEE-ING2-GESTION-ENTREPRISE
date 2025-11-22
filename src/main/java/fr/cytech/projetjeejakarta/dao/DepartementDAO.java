package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DepartementDAO {

    public DepartementDAO() {
        EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    //Créer ou modifier un département
    public void creerOuModifierDepartement(Departement d) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(d);
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
        } finally {
            em.close();
        }
    }

    //Afficher tous les départements
    public List<Departement> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Departement> departements = null;
        try {
            departements = em.createQuery("SELECT d FROM Departement d", Departement.class)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return departements;
    }

    //Rechercher un département par nom
    public List<Departement> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Departement> departements = null;
        try {
            departements = em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return departements;
    }

    //Rechercher un département par ID
    public Departement rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Departement departement = null;
        try {
            departement = em.find(Departement.class, id);
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return departement;
    }

    // Supprimer un département par ID
    public void supprimerDepartement(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Departement d = em.find(Departement.class, id);
            if (d != null) {
                em.remove(d);
            }
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
        } finally {
            em.close();
        }
    }


    //Liste tous les employés d'un département
    public List<Employe> listeEmployesDepartement(String nom){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes = null;
        try{
            employes=rechercherParNom(nom).getFirst().getEmployes();
        }
        catch(Exception except){
            except.printStackTrace();
        }
        finally {
            em.close();
        }
        return employes;
    }

    public List<Projet> listeProjetsDepartement(String nom){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets = null;
        try{
            projets=rechercherParNom(nom).getFirst().getProjets();
        }
        catch(Exception except){
            except.printStackTrace();
        }
        finally {
            em.close();
        }
        return projets;
    }
}
