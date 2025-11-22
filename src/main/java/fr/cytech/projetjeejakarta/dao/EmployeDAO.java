package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class EmployeDAO {

    public EmployeDAO(){
        EntityManagerFactory sessionFactory = Persistence.createEntityManagerFactory("jeejakartaUtil");
    }

    //Création ou modification d'employé

    public void creerOuModifierEmploye(Employe e) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(e);
            trans.commit();
        } catch (Exception except) {
            if (trans.isActive()) trans.rollback();
            except.printStackTrace();
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
            except.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Afficher tous les employés
    public List<Employe> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes = null;
        try {
            employes = em.createQuery("SELECT e FROM Employe e", Employe.class)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return employes;
    }

    // Rechercher un employé par son Id
    public Employe rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Employe employe = null;
        try {
            employe = em.find(Employe.class, id);
        } catch (Exception except) {
            System.out.println("Aucun employé trouvé avec l'id: "+id);
        } finally {
            em.close();
        }
        return employe;
    }


    // Rechercher un employé par nom
    public List<Employe> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes = null;
        try {
            employes = em.createQuery("SELECT e FROM Employe e WHERE e.nom = :nom", Employe.class)
                    .setParameter("nom", nom)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return employes;
    }


    // Rechercher des employés par département
    public List<Employe> rechercherParDepartement(int idDepartement) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes = null;
        try {
            employes = em.createQuery("SELECT e FROM Employe e WHERE e.departement.id_departement = :idDep", Employe.class)
                    .setParameter("idDep", idDepartement)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
        } finally {
            em.close();
        }
        return employes;
    }

}
