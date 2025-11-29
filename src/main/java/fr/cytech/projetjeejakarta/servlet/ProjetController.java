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
import java.util.List;

@WebServlet("/ProjetController")
public class ProjetController extends HttpServlet {

    private ProjetDAO projetDAO;
    private EmployeDAO employeDAO;
    private DepartementDAO departementDAO;

    @Override
    public void init() throws ServletException {
        projetDAO = new ProjetDAO();
        employeDAO = new EmployeDAO();
        departementDAO=new DepartementDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("liste")) {
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                if (projets.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun projet trouvé");
                }
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

            } else if ("rechercher".equals(action)) {
                String nom = request.getParameter("nom");
                List<Projet> projets = projetDAO.rechercherProjetsParNom(nom);
                request.setAttribute("projets", projets);
                if (projets.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun projet trouvé pour ce critère");
                }
                request.getRequestDispatcher("Projet/resultatRechercheProjet.jsp").forward(request, response);

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
                int id = Integer.parseInt(request.getParameter("id"));
                projetDAO.supprimerProjet(id);
                List<Projet> projets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.setAttribute("messageSucces", "Projet supprimé avec succès");
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

            } else if ("employes".equals(action)) {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                List<Employe> employes = projetDAO.listEmployesProjet(idProjet);
                Projet projet = projetDAO.rechercherProjetParID(idProjet);


                if (projet != null) {
                    request.setAttribute("projet", projet);
                    request.setAttribute("employes", employes);
                    List<Employe> employesDepartement=departementDAO.listeEmployesDepartement(projet.getDepartement().getId_departement());
                    request.setAttribute("employesDepartement", employesDepartement);
                } else {
                    request.setAttribute("messageErreur", "Projet introuvable");
                }
                request.getRequestDispatcher("Projet/listeEmployesProjet.jsp").forward(request, response);

            } else if ("affecterEmploye".equals(action)) {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                int idEmploye = Integer.parseInt(request.getParameter("idEmploye"));

                projetDAO.affecterEmploye(idEmploye, idProjet);
                List<Employe> employes = projetDAO.listEmployesProjet(idProjet);
                request.setAttribute("employes", employes);
                request.setAttribute("messageSucces", "Employé affecté au projet avec succès");
                request.getRequestDispatcher("Projet/listeEmployesProjet.jsp").forward(request, response);

            } else if ("enleverEmploye".equals(action)) {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                int idEmploye = Integer.parseInt(request.getParameter("idEmploye"));

                projetDAO.enleverEmploye(idEmploye, idProjet);
                List<Employe> employes = projetDAO.listEmployesProjet(idProjet);

                request.setAttribute("employes", employes);
                request.setAttribute("messageSucces", "Employé enlevé du projet avec succès");
                request.getRequestDispatcher("Projet/listeEmployesProjet.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur : " + e.getMessage());
            List<Projet> projets = projetDAO.afficherTous();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String chefProjetStr = request.getParameter("chefProjet");
        String departementStr = request.getParameter("departement");
        String action = request.getParameter("action");

        try {
            if ("formulaireCreerProjet".equals(action)) {
                EtatProjet etat = EtatProjet.EN_COURS;

                Employe chefProjet = employeDAO.rechercherParNom(chefProjetStr).stream().findFirst().orElse(null);
                Departement departement = new DepartementDAO().rechercherParNom(departementStr).stream().findFirst().orElse(null);

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
                request.setAttribute("projets", projetDAO.afficherTous());
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);

            } else if ("formulaireModifierProjet".equals(action)) {
                String etatStr = request.getParameter("etat");
                EtatProjet etat = (etatStr != null && !etatStr.isEmpty()) ? EtatProjet.valueOf(etatStr) : EtatProjet.EN_COURS;

                Employe chefProjet = employeDAO.rechercherParNom(chefProjetStr).stream().findFirst().orElse(null);
                Departement departement = new DepartementDAO().rechercherParNom(departementStr).stream().findFirst().orElse(null);

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
                    p.setIdProjet(Integer.parseInt(idStr));
                }

                projetDAO.creerOuModifierProjet(p);

                request.setAttribute("messageSucces", "Projet modifié avec succès");
                request.setAttribute("projets", projetDAO.afficherTous());
                request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("Projet/listeEtRechercheProjets.jsp").forward(request, response);
        }
    }
}
