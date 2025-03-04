package com.example.registration;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import java.util.Random;

@WebServlet("/admin")
public class AdminAddUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
    private static final int PASSWORD_LENGTH = 8;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String umobile = request.getParameter("contact");
        String roles = request.getParameter("roles");
        String[] addressLines = request.getParameterValues("address_line");

        // Generate random password
        String upwd = generateRandomPassword();
        System.out.println("Generated Password: " + upwd);
        
        RequestDispatcher dispatcher = null;
        Connection conn = null;
        boolean isUserInserted = false;
        boolean areAddressesInserted = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false", "root", "root123");
            if (conn != null) {
                System.out.println("Database connection established!");
            }

            String userQuery = "INSERT INTO users(uname, upwd, uemail, umobile, roles) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userQuery, PreparedStatement.RETURN_GENERATED_KEYS);

            userStmt.setString(1, uname);
            userStmt.setString(2, upwd);
            userStmt.setString(3, uemail);
            userStmt.setString(4, umobile);
            userStmt.setString(5, roles);

            int rowCount = userStmt.executeUpdate();
            System.out.println("User insert row count: " + rowCount);

            if (rowCount > 0) {
                isUserInserted = true;

                ResultSet rs = userStmt.getGeneratedKeys();
                int userId = 0;
                if (rs.next()) {
                    userId = rs.getInt(1);
                    System.out.println("Generated User ID: " + userId);
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
                    System.out.println("Addresses inserted: " + areAddressesInserted);
                }
            }

         // In AdminAddUserServlet.java
            if (isUserInserted && (areAddressesInserted || addressLines == null)) {
                request.getSession().setAttribute("status", "success");
            } else {
                request.getSession().setAttribute("status", "failed");
            }
            //response.sendRedirect("adminadduserpage.jsp");


        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("status", "failed");
        } finally {
            dispatcher = request.getRequestDispatcher("admin.jsp");
            dispatcher.forward(request, response);
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Database connection closed.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }
        return password.toString();
    }
}
