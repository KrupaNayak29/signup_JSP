package com.example.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddAddressServlet")
public class AddAddressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user ID and new address
        String userIdStr = request.getParameter("userId");
        String newAddress = request.getParameter("address");

        // Debugging logs
        System.out.println("Received userId: " + userIdStr);
        System.out.println("Received address: " + newAddress);

        // Validate inputs
        if (userIdStr == null || userIdStr.isEmpty() || newAddress == null || newAddress.trim().isEmpty()) {
            response.getWriter().write("error: Invalid input");
            return;
        }

        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            response.getWriter().write("error: Invalid user ID");
            return;
        }

        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/signup";
        String dbUser = "root";
        String dbPassword = "root123";

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // SQL query to insert address
            String sql = "INSERT INTO addresses (id, address_line) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, newAddress);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("error: Insert failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error: Database issue - " + e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
