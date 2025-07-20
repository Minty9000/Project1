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

@WebServlet("/UpdateTrainlineServlet")
public class UpdateTrainlineServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tlid = Integer.parseInt(request.getParameter("tlid"));
        String name = request.getParameter("name");
        int origin = Integer.parseInt(request.getParameter("origin"));
        int destination = Integer.parseInt(request.getParameter("destination"));
        int travel_time = Integer.parseInt(request.getParameter("travelTime"));
        float fare = Float.parseFloat(request.getParameter("fare"));

        String sql = "UPDATE trainlines SET name = ?, origin = ?, destination = ?, travel_time = ?, fare = ? WHERE tlid = ?";

        try (Connection con = new ApplicationDB().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, origin);
            pstmt.setInt(3, destination);
            pstmt.setInt(4, travel_time);
            pstmt.setFloat(5, fare);
            pstmt.setInt(6, tlid);

            pstmt.executeUpdate();

            response.sendRedirect("TrainlineServlet");
            
        } catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        e.printStackTrace();
        }
    }
}

