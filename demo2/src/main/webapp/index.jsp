<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Restaurant Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }
        .button {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            margin: 5px;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button:hover {
            background-color: #0056b3;
        }
        form {
            display: inline;
        }
    </style>
</head>
<body>
<h1>Restaurant Home Page</h1>
<br/>
<a href="sign-in" class="button">Sign In</a>
<a href="sign-up" class="button">Sign Up</a>
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="Logout" class="button" />
</form>
</body>

</html>
