package fr.cytech.projetjeejakarta.servlet.EmployeController;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/RecupMoisController")
public class RecupMoisController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. Get the current month
        Month currentMonth = LocalDate.now().getMonth();

        // 2. Get list of all months
        Month[] allMonths = Month.values();

        // 3. Build list of remaining months in year
        List<Month> remainingMonths = new ArrayList<>();

        for (Month m : allMonths) {
            if (m.getValue() >= currentMonth.getValue()) {
                remainingMonths.add(m);
            }
        }


        HttpSession session = request.getSession(false);
        session.setAttribute("months", remainingMonths);
        request.getRequestDispatcher("Employe/ajouterPrime.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
