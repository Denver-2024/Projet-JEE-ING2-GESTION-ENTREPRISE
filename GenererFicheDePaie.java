package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.HelloServlet;
import fr.cytech.projetjeejakarta.dao.FicheDePaieDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/generer-fiches-de-paie")
public class GenererFicheDePaie extends HelloServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        FicheDePaieDAO genFiches = new FicheDePaieDAO();
        genFiches.generateMonthlyFiches();

        request.getRequestDispatcher("rechercheMatricule.jsp").forward(request, response);


    }
}
