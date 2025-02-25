package com.example.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String uemail=request.getParameter("username");
			String upwd=request.getParameter("password");
			HttpSession session=request.getSession();
			RequestDispatcher dispatcher=null;
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false","root","root123");
	            PreparedStatement pst = conn.prepareStatement("SELECT uname, roles FROM users WHERE uemail=? AND upwd=?");
	            pst.setString(1, uemail);
	            pst.setString(2, upwd);

	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	                String uname = rs.getString("uname");
	                String role = rs.getString("roles");

	                session.setAttribute("name", uname);
	                session.setAttribute("roles", role);

	                if ("admin".equalsIgnoreCase(role)) {
	                    dispatcher = request.getRequestDispatcher("admin.jsp");
	                } else if ("employee".equalsIgnoreCase(role)) {
	                    dispatcher = request.getRequestDispatcher("index.jsp");
	                } else {
	                    dispatcher = request.getRequestDispatcher("index.jsp");
	                }
	            } else {
	                request.setAttribute("status", "failed");
	                dispatcher = request.getRequestDispatcher("login.jsp");
	            }

				dispatcher.forward(request, response);
				} catch (Exception e) {
				e.printStackTrace();
				
			}
	}
	
}




