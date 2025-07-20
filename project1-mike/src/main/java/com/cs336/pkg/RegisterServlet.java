package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try (Connection con = new ApplicationDB().getConnection()) {

            String sql = """	
            	INSERT INTO Users (username, password, email, phone, role)
            	VALUES (?, ?, ?, ?, 'customer')""";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            int user_id = -1;
            if (rs.next()) {
                user_id = rs.getInt(1);
            }
            
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user_id);
            session.setAttribute("username", username);
            session.setAttribute("role", "customer");

            response.sendRedirect("customer-home.jsp");
        } catch (SQLException e) {
            throw new ServletException("DB registration error", e);
        }
    }
}