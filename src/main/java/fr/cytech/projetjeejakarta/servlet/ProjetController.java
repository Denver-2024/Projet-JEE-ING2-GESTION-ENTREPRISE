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
            List<Projet> projets = projetDAO.afficherTous();
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("Projet/listeProjets.jsp").forward(request, response);

        } else if (action.equals("rechercher")) {
            // Rechercher par nom
            String nom = request.getParameter("nom");
            List<Projet> projets = projetDAO.rechercherProjets(nom);
            request.setAttribute("projets", projets);
            request.getRequestDispatcher("Projet/resultatRecherche.jsp").forward(request, response);

        }

        else if (action.equals("modifier")) {
            //Modifier projet par ID
            int id = Integer.parseInt(request.getParameter("id"));
            Projet projet=projetDAO.rechercherProjetParID(id);
            response.sendRedirect("Projet/projetFormulaire.jsp?id="+id
                    +"&name="+projet.getNom()
                    +"&description="+projet.getDescription()
                    +"&etat="+projet.getEtat()
                    +"&chefProjet="+projet.getChefDeProjet().getNom()
                    +"&departement="+projet.getDepartement().getNom());
        }

        else if (action.equals("supprimer")) {
            // Supprimer par ID
            int id = Integer.parseInt(request.getParameter("id"));
            projetDAO.supprimerProjet(id);
            response.sendRedirect("ProjetController?action=liste");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Création ou modification d’un projet
        String nom = request.getParameter("nom");
        String description = request.getParameter("description");
        String etatStr = request.getParameter("etat");
        String chefProjetStr=request.getParameter("chefProjet");
        String departementStr=request.getParameter("departement");

        EtatProjet etat = EtatProjet.valueOf(etatStr);

        EmployeDAO employeDAO=new EmployeDAO();
        Employe chefProjet=employeDAO.rechercherParNom(chefProjetStr).getFirst();
        DepartementDAO departementDAO=new DepartementDAO();
        Departement departement= departementDAO.rechercherParNom(departementStr).getFirst();
        Projet p = new Projet();
        p.setNom(nom);
        p.setDescription(description);
        p.setEtat(etat);
        p.setDepartement(departement);
        p.setChefDeProjet(chefProjet);

        projetDAO.creerOuModifierProjet(p);

        // Après création/modification → retour à la liste
        response.sendRedirect("ProjetController?action=liste");
    }
}
