<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
        .menu-section, .dish-section {
            width: 45%;
            padding: 20px;
            background-color: #f0f0f0;
            border-radius: 8px;
        }
        .dish-info {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #ffffff;
            border-radius: 5px;
        }
        .dish-info p {
            margin: 5px 0;
        }
        .form-container form {
            display: flex;
            flex-direction: column;
        }
        .form-container label, .form-container input, .form-container textarea, .form-container button {
            margin-bottom: 10px;
        }
        .form-container button {
            cursor: pointer;
            background-color: #007bff;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
        }
        .form-container button:hover {
            background-color: #0056b3;
        }
        .delete-btn {
            cursor: pointer;
            background-color: #ff4d4d; /* Red color for delete button */
            color: white;
            padding: 5px 10px;
            border: none;
            border-radius: 4px;
            margin-top: 10px;
        }
        .delete-btn:hover {
            background-color: #cc0000; /* Darker red on hover */
        }
    </style>
</head>
<body>

<div class="menu-section">
    <h2>Menu</h2>
    <c:forEach var="dish" items="${dishes}">
        <div class="dish-info">
            <p><strong>Name:</strong> ${dish.getName()}</p>
            <p><strong>Description:</strong> ${dish.getDescription()}</p>
            <p><strong>Price:</strong> ${dish.getPrice()}</p>
            <p><strong>Cooking Time:</strong> ${dish.getComplexityOfExecution()} minutes</p>
            <p><strong>Quantity left:</strong> ${dish.getQuantity() }</p>

            <form action="${pageContext.request.contextPath}/admin/deleteDish" method="post" style="display: inline;">
                <input type="hidden" name="dishId" value="${dish.getId()}"/>
                <button type="submit" class="delete-btn">Delete</button>
            </form>
        </div>
    </c:forEach>
</div>

<div class="dish-section form-container">
    <h2>Add New Dish</h2>
    <form action="${pageContext.request.contextPath}/admin/addDish" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>

        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>

        <label for="quantity">Quantity:</label>
        <input type="number" id="quantity" name="quantity" required>

        <label for="price">Price:</label>
        <input type="text" id="price" name="price" required>

        <label for="complexity">Complexity of Execution (minutes):</label>
        <input type="number" id="complexity" name="complexity" required>

        <button type="submit">Add Dish</button>
    </form>
    <p>${emptyFieldsError}</p>
    <p>${sqlError}</p>
</div>

</body>
</html>
