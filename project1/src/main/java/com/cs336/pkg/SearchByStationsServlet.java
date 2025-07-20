package com.cs336.pkg;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SearchByStationsServlet")
public class SearchByStationsServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

		int origin_id = Integer.parseInt(request.getParameter("origin_id")) ;
		int destination_id = Integer.parseInt(request.getParameter("destination_id"));
		String date = request.getParameter("date");
		
		List<Integer> matching_trainlines = new ArrayList<>();
		List<Schedule> scheduleList = new ArrayList<>();
		List<Station> stationList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = """
	        		SELECT tlid
	        		FROM stops
	        		WHERE sid IN (?, ?)
	        		GROUP BY tlid
	        		HAVING COUNT(DISTINCT sid) = 2
	        		""";
	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, origin_id);
                pstmt.setInt(2, destination_id);

                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        matching_trainlines.add(rs.getInt("tlid"));
                    }
                }
            }
	        
	        if (!matching_trainlines.isEmpty()) {
	            String placeholders = matching_trainlines.stream()
	                .map(tlid -> "?")
	                .collect(Collectors.joining(", "));

	            String scheduleSQL =
	                    "SELECT s.scid, s.train AS train, t.name AS trainline, " +
	                    "s.departure_time AS departure_time, s.arrival_time AS arrival_time " +
	                    "FROM schedules s " +
	                    "JOIN trainlines t ON t.tlid = s.trainline " +
	                    "WHERE s.trainline IN (" + placeholders + ") " +
	                    "AND DATE(s.departure_time) = ?";

	            try (PreparedStatement pstmt = con.prepareStatement(scheduleSQL)) {
	                for (int i = 0; i < matching_trainlines.size(); i++) {
	                    pstmt.setInt(i + 1, matching_trainlines.get(i));
	                }

	                pstmt.setDate(matching_trainlines.size() + 1, java.sql.Date.valueOf(date));

	                try (ResultSet rs = pstmt.executeQuery()) {
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
	            }
	            
	            
	        }
	        
	        String stationSql = "SELECT * FROM stations";
	        try (PreparedStatement pstmt = con.prepareStatement(stationSql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                Station station = new Station(
	                    rs.getInt("sid"),
	                    rs.getString("name"),
	                    rs.getString("city"),
	                    rs.getString("state")
	                );
	                stationList.add(station);
	            }
	        }
	        
	    
	        
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Optional: set error message on request
	    }
	    
	    request.setAttribute("origin_id", origin_id);
	    request.setAttribute("destination_id", destination_id);
	    request.setAttribute("stations", stationList);
	    request.setAttribute("schedules", scheduleList);
	    RequestDispatcher rd = request.getRequestDispatcher("customer-make-reservation.jsp");
	    rd.forward(request, response);
    }
}

