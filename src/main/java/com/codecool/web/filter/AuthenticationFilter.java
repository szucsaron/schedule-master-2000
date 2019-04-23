package com.codecool.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private ServletContext context;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        System.out.println("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String contextPath = context.getContextPath();
        String loginURI = contextPath + "/login";
        String requestURI = req.getRequestURI();
        String registerURI = contextPath + "/register";

        if ((session != null && session.getAttribute("user") != null)
            || requestURI.startsWith(contextPath + "/resources/")
            || req.getRequestURI().equals(loginURI) || req.getRequestURI().equals(registerURI)) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(loginURI);
        }
    }
}
