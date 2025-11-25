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

    @Override
    public void init() throws ServletException {
        projetDAO = new ProjetDAO();
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
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            } catch (Exception ex) {
                request.setAttribute("messageErreur", "Erreur lors du chargement des projets");
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }

        } else if ("rechercher".equals(action)) {
            // Rechercher par nom
            String nom = request.getParameter("nom");
            try {
                List<Projet> projets = projetDAO.rechercherProjets(nom);
                request.setAttribute("projets", projets);
                request.getRequestDispatcher("Projet/resultatRechercheProjet.jsp").forward(request, response);
            } catch (Exception e) {
                request.setAttribute("messageErreur", "Erreur lors de la recherche");
                request.getRequestDispatcher("Projet/resultatRechercheProjet.jsp").forward(request, response);
            }

        } else if ("modifier".equals(action)) {
            // Modifier projet par ID
            int id = Integer.parseInt(request.getParameter("id"));
            Projet projet = projetDAO.rechercherProjetParID(id);

            if (projet != null) {
                String url = "Projet/formulaireModifierProjet.jsp?id=" + id
                        + "&name=" + URLEncoder.encode(projet.getNom(), StandardCharsets.UTF_8)
                        + "&description=" + URLEncoder.encode(projet.getDescription(), StandardCharsets.UTF_8)
                        + "&etat=" + projet.getEtat()
                        + "&chefProjet=" + URLEncoder.encode(projet.getChefDeProjet().getNom(), StandardCharsets.UTF_8)
                        + "&departement=" + URLEncoder.encode(projet.getDepartement().getNom(), StandardCharsets.UTF_8);

                response.sendRedirect(url);
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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Création ou modification d’un projet
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String etatStr = request.getParameter("etat");
        String chefProjetStr = request.getParameter("chefProjet");
        String departementStr = request.getParameter("departement");
        System.out.println("je suis dans doPost de ProjetController");
        try {
            EtatProjet etat = EtatProjet.valueOf(etatStr);

            EmployeDAO employeDAO = new EmployeDAO();
            List<Employe> employes = employeDAO.rechercherParNom(chefProjetStr);
            Employe chefProjet = !employes.isEmpty() ? employes.get(0) : null;
            System.out.println("j'ai trouvé le nom du chef de projet");

            DepartementDAO departementDAO = new DepartementDAO();
            List<Departement> deps = departementDAO.rechercherParNom(departementStr);
            Departement departement = !deps.isEmpty() ? deps.get(0) : null;
            System.out.println("j'ai trouvé le nom du département");

            if (chefProjet == null || departement == null) {
                request.setAttribute("messageErreur", "Chef de projet ou département introuvable");
                request.getRequestDispatcher("Projet/formulaireCreerProjet.jsp").forward(request, response);
                System.out.println("j'arrive pas à trouver le chef de projet ou le département");
                return;
            }

            Projet p = new Projet();
            p.setNom(nom);
            p.setDescription(description);
            p.setEtat(etat);
            p.setDepartement(departement);
            p.setChefDeProjet(chefProjet);

            //Dans le cas de modification du projet
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                int id = Integer.parseInt(idStr);
                p.setIdProjet(id);
            }

            projetDAO.creerOuModifierProjet(p);
            System.out.println("j'ai pu créer le projet");
            request.setAttribute("messageSucces", "Projet enregistré avec succès");
            List<Projet> projets = projetDAO.afficherTous();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur lors de l'enregistrement du projet");
            request.getRequestDispatcher("Projet/formulaireCreerProjet.jsp").forward(request, response);
        }
    }
}
