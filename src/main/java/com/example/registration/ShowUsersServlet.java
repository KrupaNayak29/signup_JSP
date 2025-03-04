package com.example.registration;

import java.io.IOException;
import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/showUser")
public class ShowUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/signup?useSSL=false", "root", "root123");

            String query = "SELECT u.id, u.uname, u.uemail, u.umobile, u.roles, " +
                    "COALESCE(GROUP_CONCAT(a.address_line SEPARATOR ', '), '') AS addresses " +
                    "FROM users u " +
                    "LEFT JOIN addresses a ON u.id = a.id " + 
                    "WHERE u.roles != 'admin' " + 
                    "GROUP BY u.id, u.uname, u.uemail, u.umobile, u.roles";



         
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            request.setAttribute("resultSet", rs);
        
            RequestDispatcher dispatcher = request.getRequestDispatcher("showuser.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
