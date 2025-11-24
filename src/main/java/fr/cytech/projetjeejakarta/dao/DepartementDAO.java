package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class DepartementDAO {

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
        try {
            return em.createQuery("SELECT d FROM Departement d", Departement.class)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    //Rechercher un département par nom
    public List<Departement> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultList();
        } catch (Exception except) {
            except.printStackTrace();
            return Collections.emptyList();
        } finally {
            em.close();
        }
    }

    //Rechercher un département par ID
    public Departement rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Departement.class, id);
        } catch (Exception except) {
            except.printStackTrace();
            return null;
        } finally {
            em.close();
        }
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

    //Liste tous les employés d'un département (avec JOIN FETCH)
    public List<Employe> listeEmployesDepartement(String nom){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Departement> deps = em.createQuery(
                            "SELECT d FROM Departement d LEFT JOIN FETCH d.employes WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultList();

            if (!deps.isEmpty()) {
                return deps.get(0).getEmployes(); // collection déjà chargée
            }
        } catch(Exception except){
            except.printStackTrace();
        } finally {
            em.close();
        }
        return Collections.emptyList();
    }

    //Liste tous les projets d'un département (avec JOIN FETCH)
    public List<Projet> listeProjetsDepartement(String nom){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Departement> deps = em.createQuery(
                            "SELECT d FROM Departement d LEFT JOIN FETCH d.projets WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultList();

            if (!deps.isEmpty()) {
                return deps.get(0).getProjets(); // collection déjà chargée
            }
        } catch(Exception except){
            except.printStackTrace();
        } finally {
            em.close();
        }
        return Collections.emptyList();
    }
}
