package com.cs336.pkg;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        int user_id = (int) session.getAttribute("user_id");
        if (user_id == -1) {
            response.sendRedirect("login.jsp");
            return;
        }

        int schedule_id = Integer.parseInt(request.getParameter("schedule_id"));
        String passengerType = request.getParameter("passengerType");
        boolean roundTrip = request.getParameter("roundTrip") != null;
        int origin_id = Integer.parseInt(request.getParameter("origin_id"));
        int destination_id = Integer.parseInt(request.getParameter("destination_id"));


        // Get original fare from train_schedules
        float trainlineFare = 0;
        float fare;
        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = """
            	SELECT t.fare 
            	FROM trainlines t
            	JOIN schedules s ON t.tlid = s.trainline
            	WHERE s.scid = ?
            """;
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, schedule_id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                	trainlineFare = rs.getFloat("fare");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Step 3: Calculate discount
        float discount = 0.0f;
        switch (passengerType) {
            case "child":
            case "disabled":
                discount = 0.5f; // 50% off
                break;
            case "senior":
                discount = 0.3f; // 30% off
                break;
            default:
                discount = 0.0f; // No discount
        }
        fare = trainlineFare * (1 - discount);
        
        if (roundTrip) {
            fare *= 2;
        }
        
        fare = Math.round(fare * 100) / 100f;

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = "INSERT INTO reservations (user_id, schedule_id, origin, destination, reservation_date, fare) " +
                         "VALUES (?, ?, ?, ?, NOW(), ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, user_id);
                ps.setInt(2, schedule_id);
                ps.setInt(3, origin_id);
                ps.setInt(4, destination_id);
                ps.setFloat(5, fare);

                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect to reservation list or confirmation page
        response.sendRedirect("ViewReservationsServlet");
    }
}

