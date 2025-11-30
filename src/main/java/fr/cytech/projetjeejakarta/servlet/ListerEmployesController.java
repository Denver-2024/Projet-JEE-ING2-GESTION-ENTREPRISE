package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.DepartementDAO;
import fr.cytech.projetjeejakarta.dao.EmployeDAO;

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

@WebServlet("/ListerEmployesController")
public class ListerEmployesController extends HttpServlet {

    private EmployeDAO employeDAO;

    @Override
    public void init() throws ServletException {

        employeDAO = new EmployeDAO();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
                List<Employe> employes = employeDAO.afficherTous();
                request.setAttribute("employes", employes);

                if (employes.isEmpty()) {
                    request.setAttribute("messageErreur", "Aucun employé trouvé");
                }
                request.getRequestDispatcher("Employe/listerEmploye.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("messageErreur", "Erreur : " + e.getMessage());
            List<Employe> employes = employeDAO.afficherTous();
            request.setAttribute("employes", employes);
            request.getRequestDispatcher("Employe/listerEmploye.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
