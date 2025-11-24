<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>

<html>
<head>
    <title>Liste des projets</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Liste complète des projets</h2>
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
            <th>Actions</th>
        </tr>
        <%
            for (Projet p : projets) {
        %>
        <tr>
            <td><%= p.getIdProjet() %></td>
            <td><%= p.getNom() %></td>
            <td><%= p.getDescription() %></td>
            <td><%= p.getEtat() %></td>
            <td><%= (p.getChefDeProjet() != null ? p.getChefDeProjet().getNom() : "Non défini") %></td>
            <td>
                <a href="../ProjetController?action=modifier&id=<%= p.getIdProjet() %>">Modifier</a>
                <a href="../ProjetController?action=supprimer&id=<%= p.getIdProjet() %>">Supprimer</a>
            </td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p class="message">Aucun projet enregistré.</p>
    <%
        }
    %>
    <a href="Projet/projetFormulaire.jsp">Retour au formulaire</a>
</div>
</body>
</html>
