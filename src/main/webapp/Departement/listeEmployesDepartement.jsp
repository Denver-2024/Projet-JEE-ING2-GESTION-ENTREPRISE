<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>
<html>
<head>
    <title>Employés du département</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">
    <h2>Employés du département</h2>

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
        List<Employe> employes = (List<Employe>) request.getAttribute("employes");
        List<Employe> listeTotaleEmployes = (List<Employe>) request.getAttribute("listeTotaleEmployes");
        if (employes != null && !employes.isEmpty()) {
    %>
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

                <a href="../DepartementController?action=retirerEmploye&idEmploye=<%= e.getId_employe() %>&idDepartement=<%= idDepartement %>">
                    Retirer
                </a>
            </td>
        </tr>
        <% } %>
    </table>
    <% } else if (messageErreur == null) { %>
    <p class="message">Aucun employé trouvé pour ce département.</p>
    <% } %>

    <hr>


    <h3>Affecter un employé au département</h3>
    <%
    if (listeTotaleEmployes!=null && !listeTotaleEmployes.isEmpty()){
    %>
    <form action="../DepartementController" method="get">
        <input type="hidden" name="action" value="affecterEmploye">
        <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

        <label for="employe_a_affecter">Sélectionner l'employé:</label>
                <select name="idEmploye" id="employe_a_affecter" required>
                    <%
                        for (Employe e: listeTotaleEmployes){
                    %>
                    <option value="<%=e.getId_employe()%>"><%=e.getNom()%>, <%=e.getPrenom()%></option>
                    <%
            }
        %>
                </select>

        <input type="submit" value="Affecter">
    </form>
    <%} else {%>
        <form action="../DepartementController" method="get">
                <input type="hidden" name="action" value="affecterEmploye">
                <input type="hidden" name="idDepartement" value="<%= idDepartement %>">

    <label for="employe_a_affecter">ID de l'employé: </label>
    <input type="text" name="idEmploye" id="employe_a_affecter" required>
            <%
            }
    %>

    <hr>
    <a href="../DepartementController?action=liste">Retour à la liste des départements</a>
</div>
</body>
</html>
