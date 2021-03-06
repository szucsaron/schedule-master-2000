package com.codecool.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/protected/logout")
public final class LogoutServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.setStatus(HttpServletResponse.SC_OK);
        logger.info("Logged out");
    }
}
