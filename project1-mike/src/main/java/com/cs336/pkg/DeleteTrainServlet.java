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

@WebServlet("/DeleteTrainServlet")
public class DeleteTrainServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tid = request.getParameter("tid");

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = "DELETE FROM trains WHERE tid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, tid);
                pstmt.executeUpdate();
            }
            
            response.sendRedirect("TrainServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error.");
        }
    }
}