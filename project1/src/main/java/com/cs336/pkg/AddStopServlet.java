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

@WebServlet("/AddStopServlet")
public class AddStopServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	        int tlid = Integer.parseInt(request.getParameter("tlid"));
	        int sid = Integer.parseInt(request.getParameter("sid"));

	        try (Connection con = new ApplicationDB().getConnection()) {
	            con.setAutoCommit(false);

	            String insertSql = "INSERT INTO stops (tlid, sid) VALUES (?, ?)";
	            try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
	                insertStmt.setInt(1, tlid);
	                insertStmt.setInt(2, sid);
	                insertStmt.executeUpdate();
	            }

	            String updateSql = """
	                UPDATE trainlines
	                SET numOfStops = (
	                    SELECT COUNT(*) FROM stops WHERE tlid = ?
	                )
	                WHERE tlid = ?
	            """;
	            try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
	                updateStmt.setInt(1, tlid);
	                updateStmt.setInt(2, tlid);
	                updateStmt.executeUpdate();
	            }

	            con.commit();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        response.sendRedirect("EditTrainlineServlet?tlid=" + tlid);
	    }
}