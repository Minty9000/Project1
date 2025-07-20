package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/TrainServlet")
public class TrainServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String tid = request.getParameter("tid");
	    String trainline = request.getParameter("trainline");

	    int rowsInserted = 0;

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = "INSERT INTO trains (tid, trainline) VALUES (?, ?)";
	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, tid);
	            pstmt.setString(2, trainline);

	            rowsInserted = pstmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        return;
	    }

	    if (rowsInserted > 0) {
	    	response.sendRedirect("TrainServlet");
	    } else {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	    }
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    List<Train> trainList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = """
	            SELECT *
	            FROM trains
	        """;

	        try (PreparedStatement pstmt = con.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                Train train = new Train(
	                    rs.getString("tid")
	                );

	                trainList.add(train);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    List<Trainline> trainlineList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = """
	            SELECT t.tlid, t.name, t.travel_time, t.total_stops, t.fare,
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
	                    rs.getInt("travel_time"),
	                    rs.getInt("total_stops"),
	                    rs.getFloat("fare")
	                );

	                trainlineList.add(trainline);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    List<Schedule> scheduleList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = """
	            SELECT s.train as train, t.name as trainline, s.departure_time as departure_time,
	        		    	    s.arrival_time as arrival_time
	            FROM schedules s
	        	JOIN trainlines t ON t.tlid = s.trainline
	        """;

	        try (PreparedStatement pstmt = con.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                Schedule schedule = new Schedule(
	                	rs.getInt("scid"),
	                    rs.getString("train"),
	                    rs.getString("trainline"),
	                    rs.getObject("departure_time", LocalDateTime.class),
	                    rs.getObject("arrival_time", LocalDateTime.class)
	                );

	                scheduleList.add(schedule);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    request.setAttribute("schedules", scheduleList);
	    request.setAttribute("trainlines", trainlineList);
	    request.setAttribute("trains", trainList);
	    RequestDispatcher rd = request.getRequestDispatcher("rep-trains.jsp");
	    rd.forward(request, response);
	}


}
