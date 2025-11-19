package fr.cytech.projetjeejakarta.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("employe") != null) {
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/auth");
        }
    }
}