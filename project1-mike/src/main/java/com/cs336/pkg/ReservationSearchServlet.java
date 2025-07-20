package com.cs336.pkg;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ReservationSearchServlet")
public class ReservationSearchServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


		List<Station> stationList = new ArrayList<>();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = "SELECT * FROM stations";
	        try (PreparedStatement pstmt = con.prepareStatement(sql);
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
	    
	    request.setAttribute("stations", stationList);
	    RequestDispatcher rd = request.getRequestDispatcher("customer-make-reservation.jsp");
	    rd.forward(request, response);
    }
}

