<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Projet" %>
<%@ page import="fr.cytech.projetjeejakarta.model.Employe" %>

<html>
<head>
    <title>Liste des employés du projet</title>
    <link rel="stylesheet" type="text/css" href="../CSS/style.css">
</head>
<body>
<div class="container">

    <hr>
    <h2>Liste des employés affectés au projet</h2>
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
        List<Employe> employes = (List<Employe>) request.getAttribute("employes");
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
        <%
            for (Employe e : employes) {
        %>
        <tr>
            <td><%= e.getId_employe() %></td>
            <td><%= e.getNom() %></td>
            <td><%= e.getPrenom() %></td>
            <td><%= e.getEmail() %></td>
            <td><%= e.getGrade() %></td>
            <td>
                <a href="../ProjetController?action=enleverEmploye&id=<%= e.getId_employe()%>">Détacher</a><br>
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
    <a href="../ProjetController?action=affecterEmploye">Affecter un employé au projet</a>
</div>
</body>
</html>
