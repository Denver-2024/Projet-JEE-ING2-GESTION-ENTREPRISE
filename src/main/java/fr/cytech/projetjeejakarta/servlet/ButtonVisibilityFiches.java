package fr.cytech.projetjeejakarta.servlet;

import fr.cytech.projetjeejakarta.dao.HistoriquePaieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;

public class ButtonVisibilityFiches extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HistoriquePaieDAO histDAO = new HistoriquePaieDAO();

        LocalDate now = LocalDate.now();
        boolean done = histDAO.existsSuccessForMonth(now.getYear(), now.getMonthValue());

        req.setAttribute("showGenerateButton", !done);

        req.getRequestDispatcher("/rechercheMatricule.jsp").forward(req, resp);
    }
}
