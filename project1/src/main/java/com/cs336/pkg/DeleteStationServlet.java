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

@WebServlet("/DeleteStationServlet")
public class DeleteStationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sid = Integer.parseInt(request.getParameter("sid"));

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = "DELETE FROM stations WHERE sid = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, sid);
                int rows = pstmt.executeUpdate();
            }
            
            response.sendRedirect("StationServlet");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error.");
        }
    }
}