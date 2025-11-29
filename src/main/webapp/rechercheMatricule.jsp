<%--
  Created by IntelliJ IDEA.
  User: Cytech
  Date: 21/11/2025
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Recherche par matricule</title>
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
        .rechercheM{
            background-color: white;
            padding: 25px 40px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .title{
            display: flex;
            justify-content: center;
            align-items: center;
        }


    </style>

</head>
<body>
<div class="rechercheM">
<form action="${pageContext.request.contextPath}/recherche-matricule" method="get">
    <div class="title">
    <h1> Rechercher un employé par son matricule</h1>
    </div>
    <input type="text" name="matricule"><br><br>
    <input type="submit" value="Rechercher">
</form>
<c:if test="${showGenerateButton}">
    <form action="${pageContext.request.contextPath}/generer-fiches-de-paie" method="post">
        <input type="submit" value="Générer les fiches de paie">
    </form>
</c:if>
    <c:if test="${not empty errorMessageEmployeNotFound}">
        <p style="color:red; font-weight:bold;">
                ${errorMessageEmployeNotFound}
        </p>
    </c:if>
    <c:if test="${not empty errorMessageMatriculeNotInt}">
        <p style="color:red; font-weight:bold;">
                ${errorMessageMatriculeNotInt}
        </p>
    </c:if>
    <c:if test="${not empty errorMessageMatriculeNotPositive}">
        <p style="color:red; font-weight:bold;">
                ${errorMessageMatriculeNotPositive}
        </p>
    </c:if>
</div>


</body>
</html>
