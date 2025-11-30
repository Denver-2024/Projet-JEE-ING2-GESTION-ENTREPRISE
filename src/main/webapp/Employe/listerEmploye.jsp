<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>

<html>

<head>
    <title>Liste complète des employés et Recherche par critères</title>
    <style>
        header {
            background-color: #343a40;
            color: white;
            padding: 1rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-radius: 10px 10px 0 0;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<header>
    <div style="display: flex; align-items: center; gap: 1rem;">
        <a href="${pageContext.request.contextPath}/dashboard" style="text-decoration: none; display: flex; align-items: center; gap: 1rem;">
            <img src="${pageContext.request.contextPath}/images/Logo.png"
                 alt="Logo Gestion Entreprise"
                 style="height: 60px; width: auto;">
            <h1 style="color: white; margin: 0;">Tableau de Bord - Gestion Entreprise</h1>
        </a>
    </div>
    <div>
        <span>Bienvenue, ${sessionScope.employe.prenom} ${sessionScope.employe.nom}</span>
        <span style="margin-left: 1rem;">Rôle: ${sessionScope.role}</span>
        <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Déconnexion</a>
    </div>
</header>
<div class="page">
    <div class="container">
        <h2>Rechercher un employé</h2>
        <a href="${pageContext.request.contextPath}/Employe/rechercheEmploye.jsp">Recherche par critères</a>

        <hr>
        <h2>Liste complète des employés</h2>
        <%
            String messageErreur = (String) request.getAttribute("messageErreur");
            String messageSucces = (String) request.getAttribute("messageSucces");
            if (messageErreur != null) {
        %>
        <p class="messageErreur"><%= messageErreur %></p>
        <% } else if (messageSucces != null) { %>
        <p class="messageSucces"><%= messageSucces %></p>
        <% } %>

        <%
            List<Employe> employes = (List<Employe>) request.getAttribute("employes");
            if (employes != null && !employes.isEmpty()) {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Email</th>
                <th>Département</th>
                <th>Grade</th>
                <th>Role</th>
            </tr>
            <% for (Employe e : employes) { %>
            <tr>
                <td><%= e.getId_employe() %></td>
                <td><%= e.getNom() %></td>
                <td><%= e.getPrenom() %></td>
                <td><%= e.getEmail() %></td>
                <td><%= e.getDepartement().getNom() %></td>
                <td><%= e.getGrade().toString() %></td>
                <td><%= e.getRole().getNom() %></td>

            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>Aucun employé trouvé.</p>
        <% } %>

    </div>
</div>
</body>
</html>
