package com.example.registration;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegistrationServlet
 */
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String upwd = request.getParameter("pass");
		String umobile = request.getParameter("contact");
		String roles = request.getParameter("roles");
		RequestDispatcher dispatcher= null;
		Connection conn = null;
		
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false","root","root123");
		PreparedStatement pst = conn.prepareStatement("insert into users(uname,upwd,uemail,umobile,roles) values(?,?,?,?,?)");
		pst.setString(1, uname);
		pst.setString(2, upwd);
		pst.setString(3, uemail);
		pst.setString(4,umobile);
		pst.setString(5,roles);
		
		int rowCount=pst.executeUpdate();
		dispatcher = request.getRequestDispatcher("registration.jsp");
		if(rowCount > 0) {
			request.setAttribute("status","success");
			
		}else {
			request.setAttribute("status","failed");
		}
		dispatcher.forward(request, response);
	}catch(Exception e) {
		e.printStackTrace();
	}finally {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}
