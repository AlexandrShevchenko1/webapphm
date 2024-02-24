<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            margin-top: 50px;
        }
        .error-container {
            width: 60%;
            padding: 20px;
            background-color: #f2dede; /* Light red for error background */
            border: 1px solid #ebccd1; /* Slightly darker red border */
            border-radius: 8px;
            color: #a94442; /* Dark red for text to indicate error */
            text-align: center;
        }
        .error-container h1 {
            color: #a94442;
        }
        .error-container p {
            margin: 20px 0;
        }
        .error-container a button {
            cursor: pointer;
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
        }
        .error-container a button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="error-container">
    <h1>Error Occurred</h1>
    <p>Sorry, an unexpected error has occurred. Please try again or go back to the homepage.</p>
    <a href="${pageContext.request.contextPath}/index.jsp"><button type="button">Back to Home</button></a>
</div>

</body>
</html>
