<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>

<html>
<head>
    <title>Liste des projets avec option de recherche</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Rechercher un Projet</h2>
    <form action="../ProjetController" method="get">
        <input type="hidden" name="action" value="rechercher">
        <label for="nomRecherche">Nom :</label>
        <input type="text" name="nom" id="nomRecherche" required>
        <input type="submit" value="Rechercher">
    </form>

    <hr>
    <h2>Liste complète des projets</h2>
    <%
        String messageErreur =(String) request.getAttribute("messageErreur");
        String messageSucces =(String) request.getAttribute("messageSucces");
        if (messageErreur!=null){%>
    <p class="messageErreur"><%=messageErreur%></p>
    <%}
    else if (messageSucces!=null){%>
    <p class="messageSucces"><%=messageSucces%></p>
    <%
        }
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
        <%
            for (Projet p : projets) {
        %>
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
        <%
            }
        %>
    </table>
    <%
    }
    %>
    <hr>
    <a href="Projet/formulaireCreerProjet.jsp">Ajouter un nouveau projet</a>
</div>
</body>
</html>
