package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class EmployeDAO {

    //Créer ou modifier un employé
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

    // Afficher tous les employés (avec leur département chargé)
    public List<Employe> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement", Employe.class
            ).getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // Rechercher un employé par son Id (avec son département)
    public Employe rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Employe> employes = em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.id_employe = :id",
                    Employe.class
            ).setParameter("id", id).getResultList();

            return employes.isEmpty() ? null : employes.get(0);
        } catch (Exception except) {
            except.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Rechercher un employé par nom
    public List<Employe> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.nom = :nom",
                    Employe.class
            ).setParameter("nom", nom).getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    // Rechercher des employés par département
    public List<Employe> rechercherParDepartement(int idDepartement) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Employe e LEFT JOIN FETCH e.departement WHERE e.departement.id_departement = :idDep",
                    Employe.class
            ).setParameter("idDep", idDepartement).getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }
}
