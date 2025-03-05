package com.example.filters; // Change this to your actual package

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin.jsp") 
public class AuthFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

    
        if (session == null || session.getAttribute("roles") == null || 
            !"admin".equalsIgnoreCase((String) session.getAttribute("roles"))) {
            
            res.sendRedirect("error.jsp"); 
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {}
}
