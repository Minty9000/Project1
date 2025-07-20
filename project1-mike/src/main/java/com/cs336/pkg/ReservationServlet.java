package com.cs336.pkg;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ReservationServlet")
public class ReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null || user_id == -1) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int schedule_id = Integer.parseInt(request.getParameter("schedule_id"));
            int origin_id = Integer.parseInt(request.getParameter("origin_id"));
            int destination_id = Integer.parseInt(request.getParameter("destination_id"));
            String passengerType = request.getParameter("passengerType");
            boolean roundTrip = request.getParameter("roundTrip") != null;

            float trainlineFare = 0f;

            // Step 1: Fetch base fare from trainline
            try (Connection con = new ApplicationDB().getConnection()) {
                if (con == null) throw new SQLException("Database connection failed.");

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
                    } else {
                        throw new SQLException("No fare found for schedule ID: " + schedule_id);
                    }
                }
            }

            // Step 2: Calculate discount
            float discount;
            switch (passengerType) {
                case "child", "disabled" -> discount = 0.5f;
                case "senior" -> discount = 0.3f;
                default -> discount = 0f;
            }

            float fare = trainlineFare * (1 - discount);
            if (roundTrip) fare *= 2;
            fare = Math.round(fare * 100) / 100f;

            // Step 3: Insert reservation
            try (Connection con = new ApplicationDB().getConnection()) {
                if (con == null) throw new SQLException("Database connection failed.");

                String insertSQL = """
                    INSERT INTO reservations 
                    (user_id, schedule_id, origin, destination, reservation_date, fare)
                    VALUES (?, ?, ?, ?, NOW(), ?)
                """;

                try (PreparedStatement ps = con.prepareStatement(insertSQL)) {
                    ps.setInt(1, user_id);
                    ps.setInt(2, schedule_id);
                    ps.setInt(3, origin_id);
                    ps.setInt(4, destination_id);
                    ps.setFloat(5, fare);
                    ps.executeUpdate();
                }
            }

            // Redirect to reservation confirmation page
            response.sendRedirect("ViewReservationsServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}