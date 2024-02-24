<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .form-container {
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }
        input[type=text], input[type=password] {
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type=submit] {
            background-color: #007bff;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type=submit]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Sign Up</h2>
    <form action="${pageContext.request.contextPath}/sign-up" method="post">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div>
            <label for="login">Login:</label>
            <input type="text" id="login" name="login" required>
        </div>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="repeatPassword">Repeat Password:</label>
            <input type="password" id="repeatPassword" name="repeatPassword" required>
        </div>
        <div>
            <input type="submit" value="Sign Up">
        </div>
        <p>${passwordError}</p>
        <p>${loginError}</p>
        <p>${emptyFieldsError}</p>
        <p>${sqlError}</p>
    </form>
</div>

</body>
</html>
