package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.ProjetDAO;
import fr.cytech.projetjeejakarta.enumeration.EtatProjet;
import fr.cytech.projetjeejakarta.model.Departement;
import fr.cytech.projetjeejakarta.model.Employe;
import fr.cytech.projetjeejakarta.model.Projet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/ProjetController")
public class ProjetController extends HttpServlet {

    private ProjetDAO projetDAO;
    private EmployeDAO employeDAO;

    @Override
    public void init() throws ServletException {
        projetDAO = new ProjetDAO();
        employeDAO=new EmployeDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("liste")) {
            // Afficher tous les projets
            try {
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                if (projets.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun projet trouvé");
                }
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("messageErreur", "Erreur lors du chargement des projets");
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }

        } else if ("rechercher".equals(action)) {
            // Rechercher par nom
            String nom = request.getParameter("nom");
            try {
                List<Projet> projets = projetDAO.rechercherProjetsParNom(nom);
                request.setAttribute("projets", projets);
                if (projets.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun projet trouvé pour ce critère");
                }
                request.getRequestDispatcher("Projet/resultatRechercheProjet.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors de la recherche");
                request.getRequestDispatcher("Projet/resultatRechercheProjet.jsp").forward(request, response);
            }

        } else if ("modifier".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Projet projet = projetDAO.rechercherProjetParID(id);

            if (projet != null) {
                request.setAttribute("projet", projet);
                request.getRequestDispatcher("Projet/formulaireModifierProjet.jsp").forward(request, response);
            } else {
                request.setAttribute("messageErreur", "Projet introuvable");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }

    } else if ("supprimer".equals(action)) {
            // Supprimer par ID
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                projetDAO.supprimerProjet(id);
                List<Projet> projets = projetDAO.afficherTous(); // recharger la liste
                request.setAttribute("projets", projets);
                request.setAttribute("messageSucces", "Projet supprimé avec succès");
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors de la suppression du projet");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }
        }
        else if ("employes".equals(action)) {
            //Lister tous les employes affectés à un projet
            int idProjet=Integer.parseInt(request.getParameter("idProjet"));
            try {
                Projet projet = projetDAO.rechercherProjetParID(idProjet);
                List<Employe> employes = null;

                if (projet != null) {
                    employes = projet.getEmployes();
                    List<Employe> employesDepartement=projet.getDepartement().getEmployes();
                    request.setAttribute("employes", employes);
                    request.setAttribute("projet", projet);
                    request.setAttribute("employesDepartement", employesDepartement);

                }
                else{
                    request.setAttribute("messageErreur", "Projet introuvable");
                }
                request.getRequestDispatcher("Projet/listeEmployesProjets.jsp").forward(request, response);

            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors du chargement des employes du projet");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }
        }
        else if ("affecterEmploye".equals(action)) {
            int idProjet=Integer.parseInt(request.getParameter("idProjet"));
            int idEmploye=Integer.parseInt(request.getParameter("idEmploye"));
            try{
                Projet projet = projetDAO.rechercherProjetParID(idProjet);
                Employe employe = employeDAO.rechercherParId(idEmploye);
                if (projet != null && employe != null) {
                    projet.affecterEmploye(employe);
                    List<Employe> employes=projet.getEmployes();
                    request.setAttribute("employes", employes);
                    request.setAttribute("messageSucces", "Employé affecté au projet avec succès");
                }
                else{
                    request.setAttribute("messageErreur", "Employé ou projet introuvable");
                }
                request.getRequestDispatcher("Projet/listeEmployesProjets.jsp").forward(request, response);
            }

            catch (Exception e){
                request.setAttribute("messageErreur", "Erreur lors du chargement des employes du projet");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }
        }
        else if ("enleverEmploye".equals(action)) {

            int idProjet=Integer.parseInt(request.getParameter("idProjet"));
            int idEmploye=Integer.parseInt(request.getParameter("idEmploye"));
            try{
                Projet projet = projetDAO.rechercherProjetParID(idProjet);
                Employe employe = employeDAO.rechercherParId(idEmploye);
                if (projet != null && employe != null) {
                    projet.enleverEmploye(employe);
                    List<Employe> employes=projet.getEmployes();
                    request.setAttribute("employes", employes);
                    request.setAttribute("messageSucces", "Employé enlevé du projet avec succès");
                }
                else{
                    request.setAttribute("messageErreur", "Employé ou projet introuvable");
                }
                request.getRequestDispatcher("Projet/listeEmployesProjets.jsp").forward(request, response);
            }

            catch (Exception e){
                request.setAttribute("messageErreur", "Erreur lors du chargement des employes du projet");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Création ou modification d’un projet
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String chefProjetStr = request.getParameter("chefProjet");
        String departementStr = request.getParameter("departement");
        String action = request.getParameter("action");

        //Si création d'un nouveau projet
        if ("formulaireCreerProjet".equals(action)) {
            EtatProjet etat = EtatProjet.EN_COURS;

            try {

                EmployeDAO employeDAO = new EmployeDAO();
                List<Employe> employes = employeDAO.rechercherParNom(chefProjetStr);
                Employe chefProjet = !employes.isEmpty() ? employes.get(0) : null;

                DepartementDAO departementDAO = new DepartementDAO();
                List<Departement> deps = departementDAO.rechercherParNom(departementStr);
                Departement departement = !deps.isEmpty() ? deps.get(0) : null;

                if (chefProjet == null || departement == null) {
                    request.setAttribute("messageErreur", "Chef de projet ou département introuvable");
                    request.getRequestDispatcher("Projet/formulaireCreerProjet.jsp").forward(request, response);

                    return;
                }
                Projet p = new Projet();
                p.setNom(nom);
                p.setDescription(description);
                p.setEtat(etat);
                p.setDepartement(departement);
                p.setChefDeProjet(chefProjet);

                projetDAO.creerOuModifierProjet(p);

                request.setAttribute("messageSucces", "Projet enregistré avec succès");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors de l'enregistrement du projet");
                request.getRequestDispatcher("Projet/formulaireCreerProjet.jsp").forward(request, response);
            }

        }
        else if ("formulaireModifierProjet".equals(action)) {

            //Modification d'un projet déjà existant
            String etatStr = request.getParameter("etat");

            try {
                EtatProjet etat = EtatProjet.EN_COURS;
                if (etatStr != null && !etatStr.isEmpty()) {
                    etat = EtatProjet.valueOf(etatStr);
                }

                EmployeDAO employeDAO = new EmployeDAO();
                List<Employe> employes = employeDAO.rechercherParNom(chefProjetStr);
                Employe chefProjet = !employes.isEmpty() ? employes.get(0) : null;

                DepartementDAO departementDAO = new DepartementDAO();
                List<Departement> deps = departementDAO.rechercherParNom(departementStr);
                Departement departement = !deps.isEmpty() ? deps.get(0) : null;

                if (chefProjet == null || departement == null) {
                    request.setAttribute("messageErreur", "Chef de projet ou département introuvable");
                    request.getRequestDispatcher("Projet/formulaireModifierProjet.jsp").forward(request, response);
                    return;
                }

                Projet p = new Projet();
                p.setNom(nom);
                p.setDescription(description);
                p.setEtat(etat);
                p.setDepartement(departement);
                p.setChefDeProjet(chefProjet);

                String idStr = request.getParameter("id");
                if (idStr != null && !idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    p.setIdProjet(id);
                }

                projetDAO.creerOuModifierProjet(p);

                request.setAttribute("messageSucces", "Projet modifié avec succès");
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors de la modification du projet");
                request.getRequestDispatcher("Projet/formulaireModifierProjet.jsp").forward(request, response);
            }
        }
    }
}
