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
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ScheduleServlet")
public class ScheduleServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String train = request.getParameter("train");
	    int trainline = Integer.parseInt(request.getParameter("trainline"));
	    String departure_time_str = request.getParameter("departure_time");
	    LocalDateTime departure_time = LocalDateTime.parse(departure_time_str);
	    int travel_time = -1;
	    LocalDateTime arrival_time = null;

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String trainlineSQL = "SELECT travel_time FROM trainlines WHERE tlid = ?";
	        try (PreparedStatement pstmt = con.prepareStatement(trainlineSQL)) {
	            pstmt.setInt(1, trainline);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    travel_time = rs.getInt("travel_time");
	                }
	            }       
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        return;
		    }
	        
	        arrival_time = departure_time.plusMinutes(travel_time);
	        
	        String scheduleSQL = """
	        	INSERT INTO schedules (train, trainline, departure_time, arrival_time)
	        	VALUES (?, ?, ?, ?)
	        """;
	        try (PreparedStatement pstmt = con.prepareStatement(scheduleSQL)) {
	        	pstmt.setString(1, train);
	            pstmt.setInt(2, trainline);
	            pstmt.setTimestamp(3, Timestamp.valueOf(departure_time));
	            pstmt.setTimestamp(4, Timestamp.valueOf(arrival_time));

	            pstmt.executeUpdate();
	            
		    } catch (SQLException e) {
		        e.printStackTrace();
		        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		        return;
		    }

	        response.sendRedirect("TrainServlet");
	    
	    } catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
