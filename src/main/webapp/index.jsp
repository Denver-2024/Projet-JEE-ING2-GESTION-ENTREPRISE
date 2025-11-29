<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
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
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>

<a href="Employe/ajouterEmploye.jsp">Ajouter un employé</a><br>
<a href="Employe/rechercheMatricule.jsp">Rechercher un employé par son matricule</a>
<a href="Employe/rechercherEmploye.jsp">Recherche employé</a>
</body>
</html>