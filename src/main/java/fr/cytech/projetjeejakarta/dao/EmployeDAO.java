package fr.cytech.projetjeejakarta.dao;

import fr.cytech.projetjeejakarta.enumeration.Grade;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;
import fr.cytech.projetjeejakarta.model.Role;
import fr.cytech.projetjeejakarta.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // Alias pour compatibilité avec certains servlets
    public void saveEmploye(Employe e) {
        creerOuModifierEmploye(e);
    }

    public void updateEmploye(Employe e) {
        creerOuModifierEmploye(e);
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

    // Alias pour compatibilité
    public void deleteEmploye(int id) {
        supprimerEmploye(id);
    }

    // Afficher tous les employés
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
                            "SELECT e FROM Employe e LEFT JOIN FETCH e.departement LEFT JOIN FETCH e.role WHERE e.id_employe = :id",
                            Employe.class
                    ).setParameter("id", id)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
        return e;
    }

    // Alias pour compatibilité
    public Employe fetchEmploye(int id) {
        return rechercherParId(id);
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

    // Lister les projets d’un employé
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

    // Ajouter un projet à un employé
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

    // Enlever un projet d’un employé
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

    // Promouvoir un employé en chef de département
    public void promoteToChefDeDepartement(Employe newChef) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            int departementId = newChef.getDepartement().getId_departement();

            Employe currentChef = em.createQuery(
                            "SELECT e FROM Employe e WHERE e.departement.id_departement = :dep AND e.role.id_role = 3",
                            Employe.class
                    ).setParameter("dep", departementId)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (currentChef != null && currentChef.getId_employe() != newChef.getId_employe()) {
                Role newRoleChefProjet = em.find(Role.class, 4);
                currentChef.setRole(newRoleChefProjet);
                em.merge(currentChef);
            }

            Role chefRole = em.find(Role.class, 3);
            newChef.setRole(chefRole);
            em.merge(newChef);

            em.createQuery("UPDATE Departement d SET d.chefDepartement.id_employe = :chefId WHERE d.id_departement = :dep")
                    .setParameter("chefId", newChef.getId_employe())
                    .setParameter("dep", departementId)
                    .executeUpdate();

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    // Mettre à jour le chef d’un département
    public void updateChef(int id_departement, int id_employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            Employe oldChef = em.createQuery(
                            "SELECT e FROM Employe e WHERE e.departement.id_departement = :d AND e.role.id_role = 3",
                            Employe.class
                    ).setParameter("d", id_departement)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (oldChef != null && oldChef.getId_employe() != id_employe) {
                oldChef.setRole(em.find(Role.class, 4));
                em.merge(oldChef);
            }

            Employe newChef = em.find(Employe.class, id_employe);
            if (newChef == null) {
                throw new RuntimeException("New chef not found in DB!");
            }
            newChef.setRole(em.find(Role.class, 3));
            em.merge(newChef);

            em.createQuery("UPDATE Departement d SET d.chefDepartement.id_employe = :id WHERE d.id_departement = :dep")
                    .setParameter("id", id_employe)
                    .setParameter("dep", id_departement)
                    .executeUpdate();
            em.getTransaction().commit();

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }

    public Employe fetchNewEmploye(Employe employe) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("select e from Employe e where e.nom = :nom AND e.prenom= :prenom and e.adresse= :adresse and e.email= :email and e.grade = :grade and e.sexe = :sexe and e.role.id_role= :id_role and e.departement.id_departement= :id_departement and e.numero= :numero and e.salaire= :salaire", Employe.class)
                    .setParameter("nom", employe.getNom()).setParameter("prenom", employe.getPrenom()).setParameter("adresse", employe.getAdresse()).setParameter("email", employe.getEmail()).setParameter("grade", employe.getGrade()).setParameter("sexe", employe.getSexe())
                    .setParameter("id_role", employe.getRole().getId_role())
                    .setParameter("id_departement", employe.getDepartement().getId_departement())
                    .setParameter("numero", employe.getNumero())
                    .setParameter("salaire", employe.getSalaire())
                    .getSingleResult();

        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Employe> rechercheEmployes(String nom, String prenom, int id_departement, Grade grade, int id_role) {
        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        try {
            StringBuilder sb = new StringBuilder("SELECT e FROM Employe e WHERE 1=1 ");
            Map<String, Object> params = new HashMap<>();

            if (nom != null && !nom.isBlank()) {
                sb.append("AND e.nom LIKE :nom ");
                params.put("nom", "%" + nom + "%");
            }

            if (prenom != null && !prenom.isBlank()) {
                sb.append("AND e.prenom LIKE :prenom ");
                params.put("prenom", "%" + prenom + "%");
            }

            if (id_departement > 0) {
                sb.append("AND e.departement.id_departement = :dept ");
                params.put("dept", id_departement);
            }

            if (grade != null) {
                sb.append("AND e.grade = :grade ");
                params.put("grade", grade);
            }

            if (id_role > 0) {
                sb.append("AND e.role.id_role = :role ");
                params.put("role", id_role);
            }

            TypedQuery<Employe> query = em.createQuery(sb.toString(), Employe.class);
            params.forEach(query::setParameter);

            return query.getResultList();
        } finally {
            em.close();
        }
    }

}