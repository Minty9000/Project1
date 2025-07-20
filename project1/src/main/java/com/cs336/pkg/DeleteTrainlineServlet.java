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

@WebServlet("/DeleteTrainlineServlet")
public class DeleteTrainlineServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int tlid = Integer.parseInt(request.getParameter("tlid"));

        String sql = "DELETE FROM trainlines WHERE tlid = ?";

        try (Connection con = new ApplicationDB().getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, tlid);

            pstmt.executeUpdate();

            response.sendRedirect("TrainlineServlet");
            
        } catch (Exception e) {
        	response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        e.printStackTrace();
        }
    }
}

