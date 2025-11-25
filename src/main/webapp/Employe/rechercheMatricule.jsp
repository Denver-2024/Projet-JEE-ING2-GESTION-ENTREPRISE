<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Recherche par matricule</title>
    <style>
        body {
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
        form { margin-bottom: 20px; }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"] {
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
        input[type="submit"]:hover { background-color: #333; }
        .recherche {
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            width: 400px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        .error {
            color: red;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<div class="recherche">
    <h1>Rechercher un employé</h1>
    <form action="../EmployeController" method="post">
        <input type="hidden" name="action" value="rechercher"/>
        <label for="matricule">Matricule :</label>
        <input type="text" id="matricule" name="matricule" placeholder="Entrez le matricule"/>
        <input type="submit" value="Rechercher"/>
    </form>

    <!-- Messages d'erreur -->
    <c:if test="${not empty errorMessageEmployeNotFound}">
        <p class="error">${errorMessageEmployeNotFound}</p>
    </c:if>
    <c:if test="${not empty errorMessageMatriculeNotInt}">
        <p class="error">${errorMessageMatriculeNotInt}</p>
    </c:if>
    <c:if test="${not empty errorMessageMatriculeNotPositive}">
        <p class="error">${errorMessageMatriculeNotPositive}</p>
    </c:if>
</div>

</body>
</html>
