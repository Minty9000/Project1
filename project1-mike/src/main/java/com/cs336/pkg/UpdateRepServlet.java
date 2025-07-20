package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UpdateRepServlet")
public class UpdateRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try (Connection con = new ApplicationDB().getConnection()) {
            PreparedStatement stmt = con.prepareStatement(
                "UPDATE users SET username = ?, email = ?, phone = ? WHERE user_id = ?");
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setInt(4, user_id);
            stmt.executeUpdate();
            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Update failed", e);
        }
    }
}
