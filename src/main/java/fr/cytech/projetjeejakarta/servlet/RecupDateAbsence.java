package fr.cytech.projetjeejakarta.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/recuperer-date-absence")
public class RecupDateAbsence extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate lastDayOfYear = LocalDate.now().withMonth(12).withDayOfMonth(31);

        HttpSession session = req.getSession(false);
        session.setAttribute("firstDayOfMonth", firstDayOfMonth);
        session.setAttribute("lastDayOfYear", lastDayOfYear);
        req.getRequestDispatcher("ajouterAbsence.jsp").forward(req, resp);
    }
}
