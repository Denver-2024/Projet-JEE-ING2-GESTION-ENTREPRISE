package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;
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

    @Override
    public void init() throws ServletException {
        departementDAO = new DepartementDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("liste")) {
            // Afficher tous les départements
            List<Departement> departements = departementDAO.afficherTous();
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("Departement/listeDepartements.jsp").forward(request, response);

        } else if (action.equals("rechercher")) {
            // Rechercher par nom
            String nom = request.getParameter("nom");
            List<Departement> departements = departementDAO.rechercherParNom(nom);
            request.setAttribute("departements", departements);
            request.getRequestDispatcher("Departement/resultatRechercheDepartement.jsp").forward(request, response);

        }

        else if (action.equals("modifier")) {
            // Modifier département par ID
            int id = Integer.parseInt(request.getParameter("id"));
            Departement departement = departementDAO.rechercherParId(id);
            response.sendRedirect("Departement/departementFormulaire.jsp?id=" + id
                    + "&name=" + departement.getNom()
                    + "&description=" + departement.getDescription()
                    + "&chefDepartement=" + departement.getChefDepartement().getNom());
        }

        else if (action.equals("supprimer")) {
            // Supprimer par ID
            int id = Integer.parseInt(request.getParameter("id"));
            departementDAO.supprimerDepartement(id);
            response.sendRedirect("../DepartementController?action=liste");

        } else if (action.equals("employes")) {
            // Liste des employés d’un département
            String nom = request.getParameter("nom");
            List<Employe> employes = departementDAO.listeEmployesDepartement(nom);
            request.setAttribute("employes", employes);
            request.getRequestDispatcher("Departement/listeEmployesDepartement.jsp").forward(request, response);

        } else if (action.equals("projets")) {
            // Liste des projets d’un département
            String nom = request.getParameter("nom");
            List<Projet> projets = departementDAO.listeProjetsDepartement(nom);
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("Departement/listeProjetsDepartement.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Création ou modification d’un département
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String chefDepartementStr = request.getParameter("chefDepartement");

        Departement d = new Departement();
        d.setNom(nom);
        d.setDescription(description);

        if (!chefDepartementStr.isEmpty()) {
            EmployeDAO employeDAO = new EmployeDAO();
            Employe chefDepartement=employeDAO.rechercherParNom(chefDepartementStr).get(0);
            d.setChefDepartement(chefDepartement);
        }

        departementDAO.creerOuModifierDepartement(d);

        // Après création/modification → retour à la liste
        response.sendRedirect("../DepartementController?action=liste");
    }
}
