package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.simple.SimpleUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends AbstractServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            UserDao userDao = new SimpleUserDao(connection);
            LoginService loginService = new LoginService(userDao);

            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String repassword = req.getParameter("repassword");
            String name = req.getParameter("name");

            if(password.equals(repassword)) {
            User user = loginService.loginUser(email, password);
            req.getSession().setAttribute("user", user);
            }

            sendMessage(resp, HttpServletResponse.SC_OK, user);
        } catch (ServiceException ex) {
            sendMessage(resp, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
