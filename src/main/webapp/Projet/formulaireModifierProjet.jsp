<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<%
    Projet projet = (Projet) request.getAttribute("projet");
%>
<html>
<head>
    <title>Gestion des Projets</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Modifier un Projet</h2>

    <%
        String messageErreur = (String) request.getAttribute("messageErreur");
        String messageSucces = (String) request.getAttribute("messageSucces");
        if (messageErreur != null) { %>
    <p class="messageErreur"><%= messageErreur %></p>
    <% } else if (messageSucces != null) { %>
    <p class="messageSucces"><%= messageSucces %></p>
    <% } %>

    <form action="../ProjetController" method="post">
        <input type="hidden" name="action" value="modifier"/>
        <input type="hidden" name="id" value="<%= projet != null ? projet.getIdProjet() : "" %>"/>

        <label for="nom">Nom :</label>
        <input type="text" name="nom" id="nom" value="<%= projet != null ? projet.getNom() : "" %>" required/>

        <label for="description">Description :</label>
        <input type="text" name="description" id="description" value="<%= projet != null ? projet.getDescription() : "" %>" required/>

        <label for="etat">État :</label>
        <select name="etat" id="etat">
            <option value="EN_COURS" <%= projet != null && "EN_COURS".equals(projet.getEtat().name()) ? "selected" : "" %>>EN_COURS</option>
            <option value="TERMINE" <%= projet != null && "TERMINE".equals(projet.getEtat().name()) ? "selected" : "" %>>TERMINE</option>
            <option value="ANNULE" <%= projet != null && "ANNULE".equals(projet.getEtat().name()) ? "selected" : "" %>>ANNULE</option>
        </select>

        <label for="chefProjet">Nom du chef de projet :</label>
        <input type="text" name="chefProjet" id="chefProjet"
               value="<%= (projet != null && projet.getChefDeProjet() != null) ? projet.getChefDeProjet().getNom() : "" %>" required/>

        <label for="departement">Nom du département :</label>
        <input type="text" name="departement" id="departement"
               value="<%= (projet != null && projet.getDepartement() != null) ? projet.getDepartement().getNom() : "" %>" required/>

        <input type="submit" value="Enregistrer"/>
    </form>

    <hr>
    <a href="../ProjetController?action=liste">Afficher tous les projets</a>
</div>
</body>
</html>
