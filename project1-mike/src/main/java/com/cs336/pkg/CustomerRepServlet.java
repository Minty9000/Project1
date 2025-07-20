package com.cs336.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ListReps")
public class CustomerRepServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	List<User> repList = new ArrayList<>();

        try (Connection con = new ApplicationDB().getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM Users WHERE role = 'rep'");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role")
                );
                repList.add(user);
            }
            response.getWriter().println("</ul>");
            
            request.setAttribute("reps", repList);
    	    RequestDispatcher rd = request.getRequestDispatcher("admin-view-reps.jsp");
    	    rd.forward(request, response);

        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}