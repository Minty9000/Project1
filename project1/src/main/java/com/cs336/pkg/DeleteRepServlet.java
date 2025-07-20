package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/DeleteRepServlet")
public class DeleteRepServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));

        try (Connection con = new ApplicationDB().getConnection()) {
            // Only delete if the user is a rep
            PreparedStatement stmt = con.prepareStatement(
                "DELETE FROM Users WHERE user_id = ? AND role = 'rep'");
            stmt.setInt(1, userId);
            stmt.executeUpdate();

            response.sendRedirect("manageReps.jsp");
        } catch (Exception e) {
            throw new ServletException("Delete failed", e);
        }
    }
}