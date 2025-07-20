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

@WebServlet("/StationServlet")
public class StationServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String name = request.getParameter("name");
	    String city = request.getParameter("city");
	    String state = request.getParameter("state");

	    int rowsInserted = 0;

	    response.setContentType("text/plain"); // or "application/json" if needed
	    PrintWriter out = response.getWriter();

	    try (Connection con = new ApplicationDB().getConnection()) {
	        String sql = "INSERT INTO stations (name, city, state) VALUES (?, ?, ?)";
	        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, name);
	            pstmt.setString(2, city);
	            pstmt.setString(3, state);

	            rowsInserted = pstmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        out.write("Database error occurred.");
	        return;
	    }

	    if (rowsInserted > 0) {
	    	response.sendRedirect("StationServlet");
	    } else {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        out.write("Failed to add station.");
	    }
	}
	
	@Override
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
	    RequestDispatcher rd = request.getRequestDispatcher("rep-stations.jsp");
	    rd.forward(request, response);
	}


}
