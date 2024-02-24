package com.example.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

import com.zaxxer.hikari.HikariDataSource;
import model.Dish;
import repo.DishRepository;
import connection.pool.DataConnectionPool;
import repo.OrderRepository;
import repo.ReviewRepository;
import service.OrderProcessingService;

import java.util.List;
import java.util.Map;

@WebServlet(name = "adminServlet", value = {"/admin", "/admin/deleteDish", "/admin/addDish", "/admin/deleteReviews"})
public class AdminServlet extends HttpServlet {
    private DishRepository dishRepository;
    private OrderRepository orderRepository;
    private ReviewRepository reviewRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.dishRepository = new DishRepository(dataSource);
        this.orderRepository = new OrderRepository(dataSource);
        this.reviewRepository = new ReviewRepository(dataSource);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requestURI.endsWith("/admin/addDish")) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String quantity = request.getParameter("quantity");
            String price = request.getParameter("price");
            String time = request.getParameter("complexity");

            // Check for empty fields
            if (name.isEmpty() || description.isEmpty() || quantity.isEmpty() || price.isEmpty() || time.isEmpty()) {
                request.setAttribute("emptyFieldsError", "Fill all information");
                doGet(request, response);
                return;
            }

            try {
                dishRepository.addNewDish(name, description, quantity, price, time);
            } catch (SQLException e) {
                request.setAttribute("sqlError", "Fill the form with other data");
                doGet(request, response);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (requestURI.endsWith("/admin/deleteDish")) {
            int dishId = Integer.parseInt(request.getParameter("dishId"));
            try {
                dishRepository.deleteDish(dishId);
            } catch (SQLException e) {
                String errorMessage = e.getMessage();
                // Optional: Remove the "java.sql.SQLException:" part from the message
                errorMessage = errorMessage.replace("java.sql.SQLException: ", "");
                request.setAttribute("sqlError", errorMessage);
                doGet(request, response);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (requestURI.endsWith("/admin/deleteReviews")) {
            try {
                reviewRepository.deleteAllReviews();
            } catch (SQLException e) {
                request.setAttribute("sqlError4", "Try deleting all reviews later");
                doGet(request, response);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        response.sendRedirect(request.getContextPath() + "/admin");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false); // Get the session but don't create a new one if it doesn't exist
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (!isAdmin) {
            response.sendRedirect("index.jsp");
            return;
        }

        List<Dish> dishes = null;
        try {
            dishes = dishRepository.getDishes();
        } catch (SQLException e) {
            request.setAttribute("sqlError1", "Unable to get dishes, try again later");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int totalNumberOfOrders = 0;
        try {
            totalNumberOfOrders = orderRepository.getTotalNumberOfOrders();
            request.setAttribute("totalNumberOfOrders", totalNumberOfOrders);
        } catch (SQLException e) {
            request.setAttribute("sqlError2", "Unable to get total number of orders, try again later");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<Integer, Double> averageRatingForEachDish = null;
        try {
            averageRatingForEachDish = dishRepository.getAverageRatingForEachDish();
            request.setAttribute("averageRatingForEachDish", averageRatingForEachDish);
        } catch (SQLException e) {
            request.setAttribute("sqlError3", "Unable to get total number of orders, try again later");
        }

        request.setAttribute("dishes", dishes);
        getServletContext().getRequestDispatcher("/admin.jsp").forward(request, response);
    }
}