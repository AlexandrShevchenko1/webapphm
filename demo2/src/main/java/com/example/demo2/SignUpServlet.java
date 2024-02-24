package com.example.demo2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import connection.pool.DataConnectionPool;
import repo.DishRepository;
import repo.UserRepository;

@WebServlet(name = "signUpServlet", value = "/sign-up")
public class SignUpServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        // Check for empty fields
        if (name.isEmpty() || login.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            request.setAttribute("emptyFieldsError", "Fill all information");
            doGet(request, response);
            return;
        }
        // Check if passwords match
        if (!password.equals(repeatPassword)) {
            request.setAttribute("passwordError", "Passwords should match");
            doGet(request, response);
            return;
        }
        // Check if user with such login already exists
        try {
            UserRepository userRepository = new UserRepository(DataConnectionPool.getDataSource());
            if (userRepository.doesUserExist(login)) {
                request.setAttribute("loginError", "User with such login already exists");
                doGet(request, response);
                return;
            } else {
                userRepository.createUser(name, login, password);
            }
        } catch (SQLException e) {
            request.setAttribute("sqlError", "Incorrect data, fill the form with other data");
            doGet(request, response);
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect(request.getContextPath() + "/sign-in");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        getServletContext().getRequestDispatcher("/sign-up.jsp").forward(request, response);
    }
}