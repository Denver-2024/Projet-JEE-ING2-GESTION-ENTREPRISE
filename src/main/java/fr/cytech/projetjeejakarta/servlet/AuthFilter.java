/*package fr.cytech.projetjeejakarta.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        String loginURI = httpRequest.getContextPath() + "/auth";
        String loginPage = httpRequest.getContextPath() + "/login.jsp";
        boolean loggedIn = session != null && session.getAttribute("employe") != null;
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI) ||
                httpRequest.getRequestURI().equals(loginPage);

        if (loggedIn || loginRequest ||
                httpRequest.getRequestURI().endsWith(".css") ||
                httpRequest.getRequestURI().endsWith(".js") ||
                httpRequest.getRequestURI().endsWith("index.jsp")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(loginURI);
        }
    }
}

*/