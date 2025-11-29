<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>
<%
    Projet p = (Projet) request.getAttribute("projet");
    List<Employe> employes = (List<Employe>) request.getAttribute("employes");
    List<Employe> employesDepartement = (List<Employe>) request.getAttribute("employesDepartement");
    String messageErreur = (String) request.getAttribute("messageErreur");
    String messageSucces = (String) request.getAttribute("messageSucces");
%>

<html>
<head>
    <title>Liste des employés du projet</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">

    <h2>Liste des employés affectés au projet <%= (p != null ? p.getNom() : "") %></h2>

    <% if (messageErreur != null) { %>
    <p class="messageErreur"><%= messageErreur %></p>
    <% } else if (messageSucces != null) { %>
    <p class="messageSucces"><%= messageSucces %></p>
    <% } %>

    <% if (employes != null && !employes.isEmpty()) { %>
    <table>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Email</th>
            <th>Grade</th>
            <th>Action</th>
        </tr>
        <% for (Employe e : employes) { %>
        <tr>
            <td><%= e.getId_employe() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getGrade() %></td>
            <td>
                <a href="../ProjetController?action=enleverEmploye&idProjet=<%= p.getIdProjet() %>&idEmploye=<%= e.getId_employe() %>">
                    Détacher
                </a>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else { %>
    <p>Aucun employé affecté à ce projet.</p>
    <% } %>

    <hr>
    <h2>Affecter un employé du département au projet</h2>
    <% if (employesDepartement != null && !employesDepartement.isEmpty()) { %>
    <form action="ProjetController" method="get">
        <input type="hidden" name="action" value="affecterEmploye">
        <input type="hidden" name="idProjet" value="<%= p.getIdProjet() %>">

        <label for="employe_a_affecter">Sélectionner l'employé :</label>
        <select name="idEmploye" id="employe_a_affecter">
            <% for (Employe e : employesDepartement) { %>
            <option value="<%= e.getId_employe() %>">
                <%= e.getNom() %>, <%= e.getPrenom() %>
            </option>
            <% } %>
        </select>
        <input type="submit" value="Affecter">
    </form>
    <% } else { %>
    <p>Aucun employé disponible dans le département pour être affecté.</p>
    <% } %>

    <hr>
    <a href="../ProjetController?action=liste">Retour à la liste des projets</a>
</div>
</body>
</html>
