package com.homeservice;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.*;

@WebServlet("/bookings")
public class BookingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json;charset=UTF-8");

        String customerName = req.getParameter("customerName");
        String phone = req.getParameter("phone");
        String serviceType = req.getParameter("service");
        String bookingDate = req.getParameter("date");
        String bookingTime = req.getParameter("time");
        String address = req.getParameter("address");

        String sql = "INSERT INTO bookings "
                + "(customer_name, phone, service_type, "
                + "booking_date, booking_time, address) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customerName);
            ps.setString(2, phone);
            ps.setString(3, serviceType);
            ps.setString(4, bookingDate);
            ps.setString(5, bookingTime);
            ps.setString(6, address);

            ps.executeUpdate();

            resp.getWriter().write(
                    "{\"success\":\"Booking saved successfully\"}"
            );

        } catch (SQLException e) {
            e.printStackTrace();

            resp.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            resp.getWriter().write(
                    "{\"error\":\""
                            + jsonEscape(e.getMessage())
                            + "\"}"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws IOException {

        resp.setContentType("application/json;charset=UTF-8");

        String sql = "SELECT * FROM bookings ORDER BY id ASC";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":")
                        .append(rs.getInt("id"))
                        .append(",")
                        .append("\"name\":\"")
                        .append(jsonEscape(rs.getString("customer_name")))
                        .append("\",")
                        .append("\"phone\":\"")
                        .append(jsonEscape(rs.getString("phone")))
                        .append("\",")
                        .append("\"service\":\"")
                        .append(jsonEscape(rs.getString("service_type")))
                        .append("\",")
                        .append("\"date\":\"")
                        .append(jsonEscape(rs.getString("booking_date")))
                        .append("\",")
                        .append("\"time\":\"")
                        .append(jsonEscape(rs.getString("booking_time")))
                        .append("\",")
                        .append("\"address\":\"")
                        .append(jsonEscape(rs.getString("address")))
                        .append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            resp.getWriter().write(json.toString());

        } catch (SQLException e) {
            e.printStackTrace();

            resp.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            );

            resp.getWriter().write(
                    "{\"error\":\""
                            + jsonEscape(e.getMessage())
                            + "\"}"
            );
        }
    }

    private String jsonEscape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}