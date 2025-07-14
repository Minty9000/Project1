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

@WebServlet("/TrainlineServlet")
public class TrainlineServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

		String name = request.getParameter("name");
	    String originStr = request.getParameter("origin");
	    String destinationStr = request.getParameter("destination");
	    String travelTimeStr = request.getParameter("travelTime");
	    String fareStr = request.getParameter("fare");

	    try {
	        int origin = Integer.parseInt(originStr);
	        int destination = Integer.parseInt(destinationStr);
	        int travelTime = Integer.parseInt(travelTimeStr);
	        float fare = Float.parseFloat(fareStr);

	        try (Connection con = new ApplicationDB().getConnection()) {
	            String sql = "INSERT INTO trainlines (name, origin, destination, travelTime, fare) VALUES (?, ?, ?, ?, ?)";
	            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	                pstmt.setString(1, name);
	                pstmt.setInt(2, origin);
	                pstmt.setInt(3, destination);
	                pstmt.setInt(4, travelTime);
	                pstmt.setFloat(5, fare);

	                int rowsInserted = pstmt.executeUpdate();

	                if (rowsInserted > 0) {
	                    response.sendRedirect("StationServlet"); // Or wherever you want
	                } else {
	                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	                }
	            }
	        }

	    } catch (NumberFormatException e) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    } catch (SQLException e) {
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        e.printStackTrace();
	    }
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    List<Trainline> trainlineList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = """
	            SELECT t.tlid, t.name, t.travelTime, t.numOfStops, t.fare,
	                   o.sid AS origin_sid, o.name AS origin_name, o.city AS origin_city, o.state AS origin_state,
	                   d.sid AS destination_sid, d.name AS destination_name, d.city AS destination_city, d.state AS destination_state
	            FROM trainlines t
	            JOIN stations o ON t.origin = o.sid
	            JOIN stations d ON t.destination = d.sid
	        """;

	        try (PreparedStatement pstmt = con.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                Station origin = new Station(
	                    rs.getInt("origin_sid"),
	                    rs.getString("origin_name"),
	                    rs.getString("origin_city"),
	                    rs.getString("origin_state")
	                );

	                Station destination = new Station(
	                    rs.getInt("destination_sid"),
	                    rs.getString("destination_name"),
	                    rs.getString("destination_city"),
	                    rs.getString("destination_state")
	                );

	                Trainline trainline = new Trainline(
	                    rs.getInt("tlid"),
	                    rs.getString("name"),
	                    origin,
	                    destination,
	                    rs.getInt("travelTime"),
	                    rs.getInt("numOfStops"),
	                    rs.getFloat("fare")
	                );

	                trainlineList.add(trainline);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Optional: request.setAttribute("error", "Could not load trainlines");
	    }

	    request.setAttribute("trainlines", trainlineList);
	    RequestDispatcher rd = request.getRequestDispatcher("rep-trainlines.jsp");
	    rd.forward(request, response);
	}


}
