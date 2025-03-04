package com.example.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upwd = request.getParameter("pass");
        String umobile = request.getParameter("contact");
        String roles = request.getParameter("roles");
        String[] addressLines = request.getParameterValues("address_line");

        RequestDispatcher dispatcher = null;
        Connection conn = null;
        boolean isUserInserted = false;
        boolean areAddressesInserted = false;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false", "root", "root123");
            
            // Insert user data into 'users' table
            String userQuery = "INSERT INTO users(uname, upwd, uemail, umobile, roles) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            
            userStmt.setString(1, uname);
            userStmt.setString(2, upwd);
            userStmt.setString(3, uemail);
            userStmt.setString(4, umobile);
            userStmt.setString(5, roles);
            
            int rowCount = userStmt.executeUpdate();

            if (rowCount > 0) {
                isUserInserted = true;
                
                
                ResultSet rs = userStmt.getGeneratedKeys();
                int userId = 0;
                if (rs.next()) {
                    userId = rs.getInt(1);
                }

               
                if (addressLines != null && addressLines.length > 0) {
                    String addressQuery = "INSERT INTO addresses(id, address_line) VALUES (?, ?)";
                    PreparedStatement addressStmt = conn.prepareStatement(addressQuery);

                    for (String address : addressLines) {
                        addressStmt.setInt(1, userId);
                        addressStmt.setString(2, address);
                        addressStmt.addBatch();
                    }
                    int[] addressRowCount = addressStmt.executeBatch();
                    areAddressesInserted = addressRowCount.length == addressLines.length;
                }
            }

            if (isUserInserted && areAddressesInserted) {
                request.setAttribute("status", "success");
                
            } else {
                request.setAttribute("status", "failed");
            }
            
            dispatcher = request.getRequestDispatcher("login.jsp");
            dispatcher.forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "failed");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
