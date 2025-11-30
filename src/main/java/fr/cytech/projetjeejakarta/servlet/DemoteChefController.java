package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/DemoteChefController")
public class DemoteChefController extends HttpServlet {

    private EmployeDAO employeDAO;


    @Override
    public void init() throws ServletException {

        employeDAO = new EmployeDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("Employe/ajouterEmploye.jsp");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Employe newEmploye = (Employe) request.getAttribute("newEmploye");

        if (newEmploye == null) {
            request.setAttribute("messageErreur", "Aucun employé reçu !");
            request.getRequestDispatcher("Employe/ajouterEmploye.jsp").forward(request, response);
            return;
        }

        // Recharger l’employé depuis la base pour être sûr d’avoir l’ID et les relations
        Employe employe = employeDAO.fetchNewEmploye(newEmploye);

        if (employe == null) {
            request.setAttribute("messageErreur", "Impossible de retrouver l'employé en base !");
            request.getRequestDispatcher("Employe/ajouterEmploye.jsp").forward(request, response);
            return;
        }

        System.out.println("Received employee: " + employe.getNom() + " (ID=" + employe.getId_employe() + ")");

        // Si l’employé est chef de département (role 3), on met à jour le chef
        if (employe.getRole() != null && employe.getRole().getId_role() == 3) {
            employeDAO.updateChef(employe.getDepartement().getId_departement(), employe.getId_employe());
        }

        // Message de succès
        request.setAttribute("messageSucces", "L'employé a été ajouté avec succès");
        request.getRequestDispatcher("Employe/ajouterEmploye.jsp").forward(request, response);
    }

}
