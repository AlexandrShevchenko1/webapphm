package com.example.demo2;

import com.zaxxer.hikari.HikariDataSource;
import connection.pool.DataConnectionPool;
import model.Dish;
import model.OrderWithDishName;
import repo.DishRepository;
import repo.OrderWithDishNameRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Timestamp;
import repo.OrderRepository;
import model.OrderStatus;
import service.OrderProcessingService;

@WebServlet(name = "userServlet", value = { "/order", "/order/makeOrder", "/order/deleteOrder"})
public class UserServlet extends HttpServlet {

    private OrderProcessingService orderProcessingService;
    private OrderRepository orderRepository;

    private DishRepository dishRepository;
    private OrderWithDishNameRepository ordersdishesrepo;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.orderRepository = new OrderRepository(dataSource);
        this.dishRepository = new DishRepository(dataSource);
        this.orderProcessingService = new OrderProcessingService(dataSource);
        this.ordersdishesrepo = new OrderWithDishNameRepository(DataConnectionPool.getDataSource());
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String requestURI = request.getRequestURI();

        try {
            if (requestURI.endsWith("/order/makeOrder")) {
                int userId = (Integer) session.getAttribute("userId");
                int dishId = Integer.parseInt(request.getParameter("dishId"));
                Timestamp orderDateTime = new Timestamp(System.currentTimeMillis());
                OrderStatus status = OrderStatus.ACCEPTED;
                int orderId = orderRepository.addNewOrder(userId, dishId, orderDateTime, status.toString());

                int complexityOfExecution = dishRepository.getComplexityOfExecution(dishId);
                orderProcessingService.processOrder(orderId, complexityOfExecution);

                dishRepository.decreaseQuantity(dishId);
            } else if (requestURI.endsWith("/order/deleteOrder")) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                orderRepository.deleteOrder(orderId);
            }
        } catch (SQLException e) {
            request.setAttribute("sqlError", "try again later");
            doGet(request, response);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/order");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false); // Get the session but don't create a new one if it doesn't exist
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Dish> dishes = null;
        try {
            dishes = dishRepository.getAvailableDishes();
        } catch (SQLException e) {
            request.setAttribute("sqlError", "try later again");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<OrderWithDishName> orders = null;
        int userId = (Integer) session.getAttribute("userId");
        try {
            orders = ordersdishesrepo.getOrdersWithDishNames(userId);
        } catch (SQLException e) {
            request.setAttribute("sqlError", "try later again");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("dishes", dishes);
        request.setAttribute("userOrders", orders);
        getServletContext().getRequestDispatcher("/user.jsp").forward(request, response);
    }
}