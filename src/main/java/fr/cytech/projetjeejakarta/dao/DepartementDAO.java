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
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
    }

    //Afficher tous les départements
    public List<Departement> afficherTous() {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List <Departement> departements=null;
        try {
            departements= em.createQuery("SELECT d FROM Departement d", Departement.class)
                    .getResultList();
        } catch (Exception except) {
            throw new RuntimeException(except);

        } finally {
            em.close();
        }
        return departements;
    }

    //Rechercher un département par nom
    public List<Departement> rechercherParNom(String nom) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Departement> departements=null;
        try {
            departements= em.createQuery("SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class)
                    .setParameter("nom", nom)
                    .getResultList();
        } catch (Exception except) {
            throw new RuntimeException(except);

        } finally {
            em.close();
        }
        return departements;
    }

    //Rechercher un département par ID
    public Departement rechercherParId(int id) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Departement departement=null;
        try {
            departement= em.find(Departement.class, id);
        } catch (Exception except) {
            throw new RuntimeException(except);

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
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
    }

    //Liste tous les employés d'un département (avec JOIN FETCH)
    public List<Employe> listeEmployesDepartement(int idDepartement) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Employe> employes=null;
        try {
            List<Departement> deps = em.createQuery(
                            "SELECT d FROM Departement d LEFT JOIN FETCH d.employes WHERE d.id_departement = :idDepartement",
                            Departement.class
                    ).setParameter("idDepartement", idDepartement)
                    .getResultList();

            if (!deps.isEmpty()) {
                employes= deps.get(0).getEmployes();
            }

        } catch(Exception except){
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
        return employes;
    }

    //Liste tous les projets d'un département (avec JOIN FETCH)
    public List<Projet> listeProjetsDepartement(int iDepartement) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        List<Projet> projets=null;
        try {
            List<Departement> deps = em.createQuery(
                            "SELECT d FROM Departement d LEFT JOIN FETCH d.projets WHERE d.id_departement = :idDepartement",
                            Departement.class
                    ).setParameter("idDepartement", iDepartement)
                    .getResultList();

            if (!deps.isEmpty()) {
                projets= deps.get(0).getProjets();
            }

        } catch(Exception except){
            throw new RuntimeException(except);
        } finally {
            em.close();
        }
        return projets;
    }

    public void affecterEmploye(int idEmploye, int idDepartement){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employe employe = em.find(Employe.class, idEmploye);
            Departement departement = em.find(Departement.class, idDepartement);

            if (employe == null || departement == null) {
                throw new IllegalArgumentException("Employé ou departement introuvable");
            }

            // Mettre à jour les deux côtés
            employe.setDepartement(departement);
            departement.getEmployes().add(employe);

            em.merge(employe);
            em.merge(departement);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void retirerEmploye(int idEmploye, int idDepartement){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Employe employe = em.find(Employe.class, idEmploye);
            Departement departement = em.find(Departement.class, idDepartement);

            if (employe == null || departement == null) {
                throw new IllegalArgumentException("Employé ou departement introuvable");
            }

            em.remove(employe);
            departement.getEmployes().remove(employe);
            em.merge(departement);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void ajouterProjet(int idProjet, int idDepartement){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Projet projet = em.find(Projet.class, idProjet);
            Departement departement = em.find(Departement.class, idDepartement);

            if (projet == null || departement == null) {
                throw new IllegalArgumentException("Projet ou departement introuvable");
            }

            // Mettre à jour les deux côtés
            projet.setDepartement(departement);
            departement.getProjets().add(projet);

            em.merge(projet);
            em.merge(departement);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public void supprimerProjet(int idProjet, int idDepartement){
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Projet projet = em.find(Projet.class, idProjet);
            Departement departement = em.find(Departement.class, idDepartement);

            if (projet == null || departement == null) {
                throw new IllegalArgumentException("Projet ou departement introuvable");
            }

            em.remove(projet);
            departement.getProjets().remove(projet);
            em.merge(departement);

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

}
