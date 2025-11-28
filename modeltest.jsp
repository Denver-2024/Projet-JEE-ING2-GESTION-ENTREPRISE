<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 19/11/2025
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Test Model</title>
    <style>

        body{
            background-image: linear-gradient(to right top, #d16ba5, #c777b9, #ba83ca, #aa8fd8, #9a9ae1, #8aa7ec, #79b3f4, #69bff8, #52cffe, #41dfff, #46eefa, #5ffbf1);
            background-repeat: no-repeat;
            background-attachment: fixed;
            background-size: cover;

            font-family: 'Times New Roman', serif;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            padding-top: 50px;
        }


        .container {
            background: white;
            padding: 25px 40px;
            width: 900px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #eaeaea;
        }
        .message {
            text-align: center;
            font-style: italic;
            color: red;
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
        a:hover {
            background-color: #333;
        }

        hr {
            margin: 15px 0;
        }

        form {
            margin-bottom: 30px;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        input[type="submit"] {
            background-color: black;
            border: none;
            color: white;
            padding: 10px;
            font-size: 16px;
            width: 100%;
            cursor: pointer;
            border-radius: 5px;
        }
        input[type="submit"]:hover {
            background-color: #333;
        }

        .projet {
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            background-color: #fafafa;
        }
        .projet b {
            color: #555;
        }
    </style>
</head>
<%
    session.getAttribute("employeFound");

%>
<form  action="${pageContext.request.contextPath}/employe" method="post">
    <input value="Employé" type="submit">
    <label>${sessionScope.employeFound.id_employe}</label>
    <label>${sessionScope.employeFound.nom}</label>
    <label>${sessionScope.employeFound.prenom}</label>
    <label>${sessionScope.employeFound.adresse}</label>
    <label>${sessionScope.employeFound.id_departement}</label>
    <label>${sessionScope.employeFound.numero}</label>
    <label>${sessionScope.employeFound.email}</label>
    <label>${sessionScope.employeFound.sexe}</label>
    <label>${sessionScope.employeFound.grade}</label>
    <label>${sessionScope.employeFound.role}</label>
</form>
<%
    session.getAttribute("departementFound");

%>
<form  action="${pageContext.request.contextPath}/departement" method="post">
    <input value ="Departement" type="submit">
    <label>${sessionScope.departementFound.id_departement}</label>
    <label>${sessionScope.departementFound.nom}</label>
    <label>${sessionScope.departementFound.description}</label>
    <label>${sessionScope.departementFound.directeur}</label>

</form>

<%
    session.getAttribute("roleFound");

%>
<form  action="${pageContext.request.contextPath}/search-role" method="post">
    <input value ="Role" type="submit">
    <label>${sessionScope.roleFound.id_role}</label>
    <label>${sessionScope.roleFound.nom}</label>
    <label>${sessionScope.roleFound.description}</label>

</form>
<body>

</body>
</html>
