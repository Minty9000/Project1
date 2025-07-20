package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/AddRepServlet")
public class AddRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password"); // plaintext for now

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = "INSERT INTO users (username, password, email, phone, role) VALUES (?, ?, ?, ?, 'rep')";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.executeUpdate();

            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Insert failed", e);
        }
    }
}