package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.dao.ProjetDAO;
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

@WebServlet("/DepartementController")
public class DepartementController extends HttpServlet {

    private DepartementDAO departementDAO;
    private EmployeDAO employeDAO;
    private ProjetDAO projetDAO;

    @Override
    public void init() throws ServletException {
        departementDAO = new DepartementDAO();
        employeDAO = new EmployeDAO();
        projetDAO = new ProjetDAO();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {
            if (action == null || action.equals("liste")) {
                List<Departement> departements = departementDAO.afficherTous();
                request.setAttribute("departements", departements);
                if (departements.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun département trouvé");
                }
                request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);

            } else if ("rechercher".equals(action)) {
                String nom = request.getParameter("nom");
                List<Departement> departements = departementDAO.rechercherParNom(nom);
                request.setAttribute("departements", departements);
                if (departements.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun département trouvé pour ce critère");
                }
                request.getRequestDispatcher("Departement/resultatRechercheDepartement.jsp").forward(request, response);

            } else if ("modifier".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Departement departement = departementDAO.rechercherParId(id);
                if (departement != null) {
                    request.setAttribute("departement", departement);
                    request.getRequestDispatcher("Departement/formulaireModifierDepartement.jsp").forward(request, response);
                } else {
                    request.setAttribute("messageErreur", "Département introuvable");
                    List<Departement> departements = departementDAO.afficherTous();
                    request.setAttribute("departements", departements);
                    request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);
                }

            } else if ("supprimer".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                departementDAO.supprimerDepartement(id);
                List<Departement> departements = departementDAO.afficherTous();
                request.setAttribute("departements", departements);
                request.setAttribute("messageSucces", "Département supprimé avec succès");
                request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);

            } else if ("employes".equals(action)) {
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                List<Employe> employes = departementDAO.listeEmployesDepartement(idDepartement);
                List<Employe>listeTotaleEmployes = employeDAO.afficherTous();
                request.setAttribute("employes", employes);
                request.setAttribute("listeTotaleEmployes", listeTotaleEmployes);
                if (employes.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun employé trouvé pour ce département");
                }
                request.getRequestDispatcher("Departement/listeEmployesDepartement.jsp").forward(request, response);

            } else if ("projets".equals(action)) {
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                List<Projet> projets = departementDAO.listeProjetsDepartement(idDepartement);
                List<Projet>listeTotaleProjets = projetDAO.afficherTous();
                request.setAttribute("projets", projets);
                request.setAttribute("listeTotaleProjets", listeTotaleProjets);
                if (projets.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun projet trouvé pour ce département");
                }
                request.getRequestDispatcher("Departement/listeProjetsDepartement.jsp").forward(request, response);


            } else if ("affecterEmploye".equals(action)) {
                int idEmploye = Integer.parseInt(request.getParameter("idEmploye"));
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                departementDAO.affecterEmploye(idEmploye, idDepartement);
                request.setAttribute("messageSucces", "Employé ajouté au département avec succès");
                request.setAttribute("employes", departementDAO.listeEmployesDepartement(idDepartement));
                request.getRequestDispatcher("Departement/listeEmployesDepartement.jsp").forward(request, response);


            } else if ("retirerEmploye".equals(action)) {
                int idEmploye = Integer.parseInt(request.getParameter("idEmploye"));
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                departementDAO.retirerEmploye(idEmploye, idDepartement);
                request.setAttribute("messageSucces", "Employé supprimé du département avec succès");
                request.setAttribute("employes", departementDAO.listeEmployesDepartement(idDepartement));
                request.getRequestDispatcher("Departement/listeEmployesDepartement.jsp").forward(request, response);


            } else if ("ajouterProjet".equals(action)) {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                departementDAO.ajouterProjet(idProjet, idDepartement);
                request.setAttribute("messageSucces", "Projet ajouté au département avec succès");
                request.setAttribute("projets", departementDAO.listeProjetsDepartement(idDepartement));
                request.getRequestDispatcher("Departement/listeProjetsDepartement.jsp").forward(request, response);


            } else if ("supprimerProjet".equals(action)) {
                int idProjet = Integer.parseInt(request.getParameter("idProjet"));
                int idDepartement = Integer.parseInt(request.getParameter("idDepartement"));
                departementDAO.supprimerProjet(idProjet, idDepartement);
                request.setAttribute("messageSucces", "Projet supprimé du département avec succès");
                request.setAttribute("projets", departementDAO.listeProjetsDepartement(idDepartement));
                request.getRequestDispatcher("Departement/listeProjetsDepartement.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur: " + e.getMessage());
            List<Departement> departements = departementDAO.afficherTous();
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String chefDepartementStr = request.getParameter("chefDepartement");
        String action = request.getParameter("action");

        try {
            if ("formulaireCreerDepartement".equals(action)) {
                // Création d’un nouveau département
                Departement d = new Departement();
                d.setNom(nom);
                d.setDescription(description);

                if (chefDepartementStr != null && !chefDepartementStr.isEmpty()) {
                    List<Employe> employes = employeDAO.rechercherParNom(chefDepartementStr);
                    Employe chefDepartement = !employes.isEmpty() ? employes.get(0) : null;
                    d.setChefDepartement(chefDepartement);
                }

                departementDAO.creerOuModifierDepartement(d);

                request.setAttribute("messageSucces", "Département créé avec succès");
                List<Departement> departements = departementDAO.afficherTous();
                request.setAttribute("departements", departements);
                request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);

            } else if ("formulaireModifierDepartement".equals(action)) {
                // Modification d’un département existant
                String idStr = request.getParameter("id");
                Departement d = new Departement();
                d.setNom(nom);
                d.setDescription(description);

                if (chefDepartementStr != null && !chefDepartementStr.isEmpty()) {
                    List<Employe> employes = employeDAO.rechercherParNom(chefDepartementStr);
                    Employe chefDepartement = !employes.isEmpty() ? employes.get(0) : null;
                    d.setChefDepartement(chefDepartement);
                }

                if (idStr != null && !idStr.isEmpty()) {
                    d.setId_departement(Integer.parseInt(idStr));
                }

                departementDAO.creerOuModifierDepartement(d);

                request.setAttribute("messageSucces", "Département modifié avec succès");
                List<Departement> departements = departementDAO.afficherTous();
                request.setAttribute("departements", departements);
                request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur : " + e.getMessage());
            request.getRequestDispatcher("Departement/listeEtRechercheDepartements.jsp").forward(request, response);
        }
    }

}
