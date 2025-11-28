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
        List<Employe> employes = (List<Employe>) request.getAttribute("employes");
        if (employes != null && !employes.isEmpty()) {
    %>
    <table>
        <tr><th>ID</th><th>Nom</th><th>Prénom</th><th>Email</th><th>Grade</th></tr>
        <%
            for (Employe e : employes) {
        %>
        <tr>
            <td><%= e.getId_employe() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= e.getRole() %></td>
            <td><%= e.getGrade() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <%
    } else {
    %>
    <p class="message">Aucun employé trouvé pour ce département.</p>
    <%
        }
    %>
    <a href="../DepartementController?action=liste">Retour aux départements</a>
</div>
</body>
</html>
