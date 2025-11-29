<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>

<html>
<head>
    <title>Résultat de la recherche des projets</title>

    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
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
</body>
</html>
