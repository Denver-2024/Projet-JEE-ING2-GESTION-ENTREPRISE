package fr.cytech.projetjeejakarta.dao.;

import fr.cytech.projetjeejakarta.model.Projet;


import fr.cytech.projetjeejakarta.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProjetDAO {

    public void creerProjet(Projet p) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            session.saveOrUpdate(p);
            trans.commit();
        } catch (Exception except) {
            if (trans != null) trans.rollback();
            except.printStackTrace();
        }
    }

    public void supprimerProjet(int id) {
        Transaction trans = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trans = session.beginTransaction();
            Projet p = session.get(Projet.class, id);
            if (p != null) session.remove(p);
            trans.commit();
        } catch (Exception except) {
            if (trans != null) trans.rollback();
            except.printStackTrace();
        }
    }

    public List<Projet> rechercherParNomPrenom(String nom, String prenom){
        Transaction trans=null;
        List<Etudiant> etudiants=null;
        try(Session session=HibernateUtil.getSessionFactory().openSession()){
            trans=session.beginTransaction();
            etudiants=session.createQuery("from Etudiant where nom= :nom and prenom= :prenom", Etudiant.class)
                    .setParameter("nom", nom).setParameter("prenom", prenom).list();
            return etudiants;
        }
        catch (Exception except) {
            if (trans!=null) trans.rollback();
            except.printStackTrace();
        }
        return etudiants;


    }

    public List<Projet> afficherTous() {
        Transaction trans=null;
        List<Projet> etudiants=null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            trans=session.beginTransaction();
            projets=session.createQuery("from Projet", Projet.class).list();
            trans.commit();
            return projets;
        }
        catch (Exception except) {
            if (trans!=null) trans.rollback();
            except.printStackTrace();
        }

        return projets;
    }

}
