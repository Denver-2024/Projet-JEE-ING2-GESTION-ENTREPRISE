<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<html>
<head>
    <title>Projets du département</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Projets du département</h2>

    <%
        String messageErreur = (String) request.getAttribute("messageErreur");
        String messageSucces = (String) request.getAttribute("messageSucces");
        Integer idDepartement = (Integer) request.getAttribute("idDepartement");

        if (messageErreur != null) {
    %>
    <p class="messageErreur"><%= messageErreur %></p>
    <% } else if (messageSucces != null) { %>
    <p class="messageSucces"><%= messageSucces %></p>
    <% } %>

    <%
        List<Projet> projets = (List<Projet>) request.getAttribute("projets");
        List<Projet> listeTotaleProjets = (List<Projet>) request.getAttribute("listeTotaleProjets");
        if (projets != null && !projets.isEmpty()) {
    %>
    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
            <th>État</th>
            <th>Chef de projet</th>
            <th>Action</th>
        </tr>
        <% for (Projet p : projets) { %>
        <tr>
            <td><%= p.getIdProjet() %></td>
            <td><%= p.getNom() %></td>
            <td><%= p.getDescription() %></td>
            <td><%= p.getEtat() %></td>
            <td><%= (p.getChefDeProjet() != null ? p.getChefDeProjet().getNom() : "Non défini") %></td>
            <td>

                <a href="../DepartementController?action=retirerProjet&idProjet=<%= p.getIdProjet() %>&idDepartement=<%= idDepartement %>">
                    Retirer
                </a>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else if (messageErreur == null) { %>
    <p class="message">Aucun projet trouvé pour ce département.</p>
    <% } %>

    <hr>

    <h3>Affecter un projet au département</h3>
    <%
        if (listeTotaleProjets != null && !listeTotaleProjets.isEmpty()) {
    %>
    <form action="../DepartementController" method="get">
        <input type="hidden" name="action" value="affecterProjet">
        <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

        <label for="projet_a_affecter">Sélectionner le projet :</label>
        <select name="idProjet" id="projet_a_affecter" required>
            <% for (Projet p : listeTotaleProjets) { %>
            <option value="<%= p.getIdProjet() %>"><%= p.getNom() %></option>
            <% } %>
        </select>

        <input type="submit" value="Affecter">
    </form>
    <% } else { %>
    <form action="../DepartementController" method="get">
        <input type="hidden" name="action" value="affecterProjet">
        <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

        <label for="projet_a_affecter">ID du projet :</label>
        <input type="text" name="idProjet" id="projet_a_affecter" required>

        <input type="submit" value="Affecter">
    </form>
    <% } %>

    <hr>
    <a href="../DepartementController?action=liste">Retour à la liste des départements</a>
</div>
</body>
</html>
