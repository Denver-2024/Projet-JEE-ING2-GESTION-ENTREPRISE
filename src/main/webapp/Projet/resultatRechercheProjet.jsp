<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>

<html>
<head>
    <title>Résultat de la recherche des projets</title>
    <style>
        header {
        background-color: #343a40;
        color: white;
        padding: 1rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-radius: 10px 10px 0 0;
    }</style>
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
        <h2>Résultat de la recherche des projets</h2>

        <%
            List<Projet> projets = (List<Projet>) request.getAttribute("projets");
            if (projets != null && !projets.isEmpty()) {
        %>
        <table>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Description</th>
                <th>État</th>
                <th>Chef de projet</th>
                <th>Département</th>
                <th>Actions</th>
            </tr>
            <% for (Projet p : projets) { %>
            <tr>
                <td><%= p.getIdProjet() %></td>
                <td><%= p.getNom() %></td>
                <td><%= p.getDescription() %></td>
                <td><%= p.getEtat() %></td>
                <td><%= (p.getChefDeProjet() != null ? p.getChefDeProjet().getNom() : "Non défini") %></td>
                <td><%= (p.getDepartement() != null ? p.getDepartement().getNom() : "Non défini") %></td>
                <td>

                    <a href="../ProjetController?action=modifier&id=<%= p.getIdProjet() %>">Modifier</a><br>
                    <a href="../ProjetController?action=supprimer&id=<%= p.getIdProjet() %>">Supprimer</a>
                </td>
            </tr>
            <% } %>
        </table>
        <% } else {
            String messageErreur = (String) request.getAttribute("messageErreur");
            if (messageErreur != null) { %>
        <p class="messageErreur"><%= messageErreur %></p>
        <% } else { %>
        <p>Aucun projet trouvé.</p>
        <% }
        } %>

        <hr>

        <a href="Projet/formulaireCreerProjet.jsp">Ajouter un nouveau projet</a> <br>
        <a href="../ProjetController?action=liste">Liste complète des projets</a>
    </div>
</div>
</body>
</html>
