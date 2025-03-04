package com.example.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uemail = request.getParameter("email");
        Connection conn = null;
        PreparedStatement addressStmt = null;
        PreparedStatement userStmt = null;
        PreparedStatement getUserStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false", "root", "root123");
            conn.setAutoCommit(false); 

           
            String getUserIdQuery = "SELECT id FROM users WHERE uemail = ?";
            getUserStmt = conn.prepareStatement(getUserIdQuery);
            getUserStmt.setString(1, uemail);
            rs = getUserStmt.executeQuery();

            int userId = -1;
            if (rs.next()) {
                userId = rs.getInt("id");
            }

            if (userId != -1) {
             
                String deleteAddressQuery = "DELETE FROM addresses WHERE id = ?";
                addressStmt = conn.prepareStatement(deleteAddressQuery);
                addressStmt.setInt(1, userId);
                addressStmt.executeUpdate();

             
                String deleteUserQuery = "DELETE FROM users WHERE id = ?";
                userStmt = conn.prepareStatement(deleteUserQuery);
                userStmt.setInt(1, userId);
                userStmt.executeUpdate();

                conn.commit(); 
                response.getWriter().write("success");
            } else {
                response.getWriter().write("User not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback(); // Rollback if failure
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            response.getWriter().write("failed");
        } finally {
            try {
                if (rs != null) rs.close();
                if (getUserStmt != null) getUserStmt.close();
                if (addressStmt != null) addressStmt.close();
                if (userStmt != null) userStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
