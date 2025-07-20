package com.cs336.pkg;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.time.LocalDateTime;

@WebServlet("/ViewReservationsServlet")
public class ViewReservationsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int user_id = (int) session.getAttribute("user_id");
        if (user_id == -1) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<Reservation> reservationList = new ArrayList<>();

        try (Connection con = new ApplicationDB().getConnection()) {
            String sql = """
            		SELECT r.rid, r.reservation_date, r.fare, o.name as origin_name,
            			d.name as destination_name, s.train, t.name as trainline,
            			s.departure_time, s.arrival_time
		            FROM reservations r
		            JOIN schedules s ON r.schedule_id = s.scid
		            JOIN stations o ON r.origin = o.sid
		            JOIN stations d ON r.destination = d.sid
		            JOIN trainlines t ON s.trainline = t.tlid
		            WHERE r.user_id = ?
            		""";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, user_id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Reservation reservation = new Reservation(
                    	rs.getInt("rid"),
                        rs.getString("train"),
                        rs.getString("trainline"),
                        rs.getString("origin_name"),
                        rs.getString("destination_name"),
                        rs.getObject("departure_time", LocalDateTime.class),
                        rs.getObject("arrival_time", LocalDateTime.class),
                        rs.getFloat("fare"),
                        rs.getObject("reservation_date", LocalDateTime.class)
                    );
                    
                    reservationList.add(reservation);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("reservations", reservationList);
        request.getRequestDispatcher("viewReservations.jsp").forward(request, response);
    }
}
