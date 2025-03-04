package com.example.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/signup";
    private static final String DB_USER = "root";  
    private static final String DB_PASSWORD = "root123";  

 
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                String sql = "SELECT u.id, u.uname, u.uemail, u.umobile, " +
                             "COALESCE(GROUP_CONCAT(a.address_line SEPARATOR ', '), '') AS addresses " +
                             "FROM users u LEFT JOIN addresses a ON u.id = a.id " +
                             "WHERE u.id = ? GROUP BY u.id";

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, userId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            String name = rs.getString("uname");
                            String email = rs.getString("uemail");
                            String contact = rs.getString("umobile");
                            String addresses = rs.getString("addresses");

                            // Debugging print statements
                            System.out.println("User ID: " + userId);
                            System.out.println("Name: " + name);
                            System.out.println("Email: " + email);
                            System.out.println("Contact: " + contact);
                            System.out.println("Addresses: " + addresses);

                            request.setAttribute("userId", userId);
                            request.setAttribute("name", name);
                            request.setAttribute("email", email);
                            request.setAttribute("contact", contact);
                            request.setAttribute("addresses", addresses);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("adminupdateuserpage.jsp").forward(request, response);
    }

  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

                int userId = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String contact = request.getParameter("contact");
                String[] addresses = request.getParameterValues("address_line");
                
                // Update user details
                String updateUserSQL = "UPDATE users SET uname = ?, uemail = ?, umobile = ? WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(updateUserSQL)) {
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, contact);
                    ps.setInt(4, userId);
                    ps.executeUpdate();
                }

                // Delete old addresses
                String deleteAddressSQL = "DELETE FROM addresses WHERE id = ?";
                try (PreparedStatement ps = con.prepareStatement(deleteAddressSQL)) {
                    ps.setInt(1, userId);
                    ps.executeUpdate();
                }
                
                // Insert new addresses
                if (addresses != null && addresses.length > 0) {
                    String insertAddressSQL = "INSERT INTO addresses (id, address_line) VALUES (?, ?)";
                    try (PreparedStatement ps = con.prepareStatement(insertAddressSQL)) {
                        for (String address : addresses) {
                            ps.setInt(1, userId);
                            ps.setString(2, address);
                            ps.executeUpdate();
                        }
                    }
                }
                
                response.sendRedirect("admin.jsp?success=User updated successfully");
            }
        } catch (ClassNotFoundException e) {
            response.getWriter().write("MySQL Driver not found: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            response.getWriter().write("Invalid user ID format");
            e.printStackTrace();
        } catch (SQLException e) {
            response.getWriter().write("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

