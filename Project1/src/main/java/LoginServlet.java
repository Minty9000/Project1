import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = com.admin.utils.DBUtil.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                HttpSession session = request.getSession();
                session.setAttribute("user_id", rs.getInt("user_id"));
                session.setAttribute("username", username);
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("phone", rs.getString("phone"));
                session.setAttribute("role", role);

                switch (role) {
                    case "admin":
                        response.sendRedirect("admin.jsp");
                        break;
                    case "rep":
                        response.sendRedirect("rep.jsp");
                        break;
                    case "customer":
                    default:
                        response.sendRedirect("customer.jsp");
                        break;
                }
            } else {
                response.sendRedirect("login.jsp?error=true");
            }

        } catch (SQLException e) {
            throw new ServletException("Login error", e);
        }
    }
}