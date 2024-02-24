<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
        .menu-section, .order-section {
            width: 45%;
            padding: 20px;
            background-color: #f0f0f0;
            border-radius: 8px;
        }
        .item, .order-item {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #ffffff;
            border-radius: 5px;
        }
        .item p, .order-item p {
            margin: 5px 0;
        }
        .form-container form {
            display: flex;
            flex-direction: column;
        }
        .form-container button {
            cursor: pointer;
            background-color: #007bff;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            margin-top: 10px;
        }
        .form-container button:hover {
            background-color: #0056b3;
        }
        .pay-btn {
            cursor: pointer;
            background-color: #4CAF50; /* Green color for the pay button */
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            margin-left: 10px; /* Add some space between Cancel and Pay buttons */
        }
        .pay-btn:hover {
            background-color: #45a049; /* Darker green on hover */
        }
        .margin-down {
            position: relative;
            top: 5px;
        }
    </style>
</head>
<body>
<div class="menu-section">
    <h2>Menu</h2>
    <c:forEach var="dish" items="${dishes}">
        <div class="item">
            <p><strong>Name:</strong> ${dish.getName()}</p>
            <p><strong>Description:</strong> ${dish.getDescription()}</p>
            <p><strong>Price:</strong> ${dish.getPrice()}</p>
            <p><strong>Cooking Time:</strong> ${dish.getComplexityOfExecution()} minutes</p>
            <p><strong>Quantity left:</strong> ${dish.getQuantity() }</p>

            <form action="${pageContext.request.contextPath}/order/makeOrder" method="post">
                <input type="hidden" name="dishId" value="${dish.getId()}" />
                <button type="submit">Order</button>
            </form>
        </div>
    </c:forEach>
</div>
<div class="order-section">
    <h2>Your Orders</h2>
    <c:forEach var="order" items="${userOrders}">
        <div class="order-item">
            <p>Dish Name: ${order.getDishName()}</p> <!-- Assume dishName comes from joined query -->
            <p>Order Date/Time: ${order.getOrderDateTime()}</p>
            <p>Status: ${order.getStatus()}</p>
            <form action="${pageContext.request.contextPath}/order/deleteOrder" method="post">
                <input type="hidden" name="orderId" value="${order.getOrderId()}" />
                <button type="submit">Cancel</button>
            </form>
            <c:if test="${order.status == 'READY'}">
                <form action="${pageContext.request.contextPath}/order/review" method="get" style="display:inline;">
                    <input type="hidden" name="orderDishId" value="${order.getDishId()}" />
                    <input type="hidden" name="orderId" value="${order.getOrderId()}" />
                    <button type="submit" class="pay-btn margin-down">Pay</button>
                </form>
            </c:if>
            <p>${paymentError}</p>
        </div>
    </c:forEach>
</div>
</body>
</html>
