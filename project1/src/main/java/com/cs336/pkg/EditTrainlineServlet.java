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

@WebServlet("/EditTrainlineServlet")
public class EditTrainlineServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tlidStr = request.getParameter("tlid");
        Trainline trainline = null;

        if (tlidStr != null) {
            int tlid = Integer.parseInt(tlidStr);

            try (Connection con = new ApplicationDB().getConnection()) {
                String sql = """
                    SELECT t.tlid, t.name, t.travel_time, t.total_stops, t.fare,
                           o.sid AS origin_sid, o.name AS origin_name, o.city AS origin_city, o.state AS origin_state,
                           d.sid AS destination_sid, d.name AS destination_name, d.city AS destination_city, d.state AS destination_state
                    FROM trainlines t
                    JOIN stations o ON t.origin = o.sid
                    JOIN stations d ON t.destination = d.sid
                    WHERE t.tlid = ?
                """;

                try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                    pstmt.setInt(1, tlid);

                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
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

                            trainline = new Trainline(
                                rs.getInt("tlid"),
                                rs.getString("name"),
                                origin,
                                destination,
                                rs.getInt("travel_time"),
                                rs.getInt("total_stops"),
                                rs.getFloat("fare")
                            );
                        }
                    }
                }
                
                List<Station> stationList = new ArrayList<>();
                String sqlStations = "SELECT * FROM stations";
                try (PreparedStatement pstmt = con.prepareStatement(sqlStations);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Station s = new Station(
                            rs.getInt("sid"),
                            rs.getString("name"),
                            rs.getString("city"),
                            rs.getString("state")
                        );
                        stationList.add(s);
                    }
                }
                request.setAttribute("stations", stationList);
                
                List<Stop> stopList = new ArrayList<>();

                String sqlStops = """
                    SELECT s.sid, s.name, s.city, s.state
                    FROM stops st
                    JOIN stations s ON st.sid = s.sid
                    WHERE st.tlid = ?
                """;

                try (PreparedStatement pstmt = con.prepareStatement(sqlStops)) {
                    pstmt.setInt(1, tlid);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            Station station = new Station(
                                rs.getInt("sid"),
                                rs.getString("name"),
                                rs.getString("city"),
                                rs.getString("state")
                            );
                            
                            Stop stop = new Stop(station, trainline);
                            stopList.add(stop);
                        }
                    }
                }

                request.setAttribute("stops", stopList);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("trainline", trainline);
        RequestDispatcher rd = request.getRequestDispatcher("rep-edit-trainline.jsp");
        rd.forward(request, response);
    }
}
