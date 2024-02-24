package com.example.demo2;

import com.zaxxer.hikari.HikariDataSource;
import connection.pool.DataConnectionPool;
import model.Dish;
import model.OrderStatus;
import model.OrderWithDishName;
import repo.DishRepository;
import repo.OrderRepository;
import repo.OrderWithDishNameRepository;
import repo.ReviewRepository;
import service.OrderProcessingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "UserReviewServlet", value = { "/order/review"})
public class UserReviewServlet extends HttpServlet {

//    private OrderProcessingService orderProcessingService;
//    private OrderRepository orderRepository;
//
//    private DishRepository dishRepository;
//    private OrderWithDishNameRepository ordersdishesrepo;
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        HikariDataSource dataSource = DataConnectionPool.getDataSource();
        this.reviewRepository = new ReviewRepository(dataSource);
        this.orderRepository = new OrderRepository(dataSource);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String rating = request.getParameter("rating");
        String comment = request.getParameter("comment");
        if (comment == null) {
            comment = ""; // Replace null with an empty string
        }
        int dishId = Integer.parseInt(request.getParameter("orderDishId"));
        if (rating.isEmpty()) {
            request.setAttribute("emptyFieldsError","Provide rating before submitting the form");
            doGet(request, response);
            return;
        }
        try {
            reviewRepository.addNewReview(dishId, Integer.parseInt(rating), comment);
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
        try {
            orderRepository.deleteOrder(Integer.parseInt(request.getParameter("orderId")));
        } catch (SQLException e) {
            request.setAttribute("paymentError", "Payment Error occured,try later");
            getServletContext().getRequestDispatcher(request.getContextPath() + "/order").forward(request, response);
            return;
        }
        getServletContext().getRequestDispatcher("/review.jsp").forward(request, response);
    }
}