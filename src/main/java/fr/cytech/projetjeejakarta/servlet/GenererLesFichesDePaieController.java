package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.FicheDePaieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/GenererLesFichesDePaieController")
public class GenererLesFichesDePaieController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        FicheDePaieDAO genFiches = new FicheDePaieDAO();
        genFiches.generateMonthlyFiches();

        request.getRequestDispatcher("Employe/rechercheMatricule.jsp").forward(request, response);

    }
}
