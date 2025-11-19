<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<html>
<head>
    <title>Liste des projets</title>
    <style>
        .table{
            border: 2px;
            mso-cellspacing: 0;
            padding: 5px;}

        #message {
            text-align: center;
            font-style: italic;
            margin-bottom: 15px;
        }
        a {
            text-decoration: none;
            display: block;
            text-align: center;
            margin-top: 15px;
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<h2>Liste complète des projets</h2>
<%
    List<Projet> projets = (List<Projet>) request.getAttribute("projets");
    if (projets != null && !projets.isEmpty()) {
%>

<table class="table">
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
            <a href="ProjetController?action=supprimer&id=<%= p.getIdProjet() %>">Supprimer</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
<%
} else {
%>
<p id="message">Aucun projet enregistré.</p>
<%
    }
%>
<a href="projetFormulaire.jsp">Retour au formulaire</a>
</body>
</html>
