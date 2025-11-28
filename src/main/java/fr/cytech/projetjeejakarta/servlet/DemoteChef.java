package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.EmployeDAO;
import fr.cytech.projetjeejakarta.model.Employe;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/demote-chef")
public class DemoteChef extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employe newEmploye = (Employe) request.getAttribute("newEmploye");

        if (newEmploye == null) {
            response.getWriter().println("No employee received!");
            return;
        }
        EmployeDAO employeDAO = new EmployeDAO();
        Employe employe = employeDAO.fetchNewEmploye(newEmploye);

        // Do operations with newEmploye
        System.out.println("Received employee: " + employe.getNom()+ employe.getId_employe());

        if(employe.getRole().getId_role()==3){
            employeDAO.updateChef(employe.getId_departement(), employe.getId_employe());
        }

        // Example: send response
        request.setAttribute("messageAjoutSucces","L'employé a été ajouté aves succès");
        request.getRequestDispatcher("ajouterEmploye.jsp").forward(request, response);

    }
}
