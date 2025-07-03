package com.cs336.pkg;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        boolean validUser = false;
        String role = null;

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        validUser = true;
                        role = rs.getString("role");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (validUser) {
        	// Store username in session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("role", role);
            
            // Login success — redirect based on role
            switch (role.toLowerCase()) {
            	case "customer":
            		response.sendRedirect("customer.jsp");
            		break;
            	case "rep":
            		response.sendRedirect("rep.jsp");
            		break;
            	case "admin":
            		response.sendRedirect("admin.jsp");
            		break;
            	default:
            		response.sendRedirect("login.jsp"); // fallback page
            		break;
        }
            
            
            
        } else {
            // Login fail — forward back to login.jsp with error message
            request.setAttribute("errorMessage", "Invalid username or password");
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Just redirect GET requests to login page
        response.sendRedirect("login.jsp");
    }
}