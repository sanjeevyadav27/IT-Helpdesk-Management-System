import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class HelpdeskServer {

    public static void main(String[] args) throws IOException {


        HttpServer server =
            HttpServer.create(
                new InetSocketAddress(8080),
                0
            );


        // =========================
        // TEST API
        // =========================

        server.createContext(
            "/api/test",
            exchange -> {

                String response =
                    "IT Helpdesk Backend is Running!";


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.getBytes(
                        StandardCharsets.UTF_8
                    );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // LOGIN API
        // =========================

        server.createContext(
            "/api/login",
            exchange -> {

                String query =
                    exchange.getRequestURI()
                           .getQuery();


                String email =
                    query.split("email=")[1]
                         .split("&")[0];


                String password =
                    query.split("password=")[1];


                email =
                    URLDecoder.decode(
                        email,
                        StandardCharsets.UTF_8
                    );


                password =
                    URLDecoder.decode(
                        password,
                        StandardCharsets.UTF_8
                    );


                String sql =
                    "SELECT name, role FROM users " +
                    "WHERE email = ? AND password = ?";


                String response;


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ps.setString(
                        1,
                        email
                    );

                    ps.setString(
                        2,
                        password
                    );


                    ResultSet rs =
                        ps.executeQuery();


                    if (rs.next()) {

                        response =
                            "Login Successful! Welcome " +
                            rs.getString("name") +
                            " | Role: " +
                            rs.getString("role");

                    } else {

                        response =
                            "Invalid Email or Password!";
                    }


                    rs.close();
                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response =
                        "Database Error!";

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.getBytes(
                        StandardCharsets.UTF_8
                    );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // CREATE TICKET API
        // =========================

        server.createContext(
            "/api/tickets",
            exchange -> {

                String query =
                    exchange.getRequestURI()
                           .getQuery();


                String title =
                    query.split("title=")[1]
                         .split("&")[0];


                String description =
                    query.split("description=")[1]
                         .split("&")[0];


                String priority =
                    query.split("priority=")[1]
                         .split("&")[0];


                String categoryId =
                    query.split("categoryId=")[1]
                         .split("&")[0];


                title =
                    URLDecoder.decode(
                        title,
                        StandardCharsets.UTF_8
                    );


                description =
                    URLDecoder.decode(
                        description,
                        StandardCharsets.UTF_8
                    );


                String sql =
                    "INSERT INTO tickets " +
                    "(title, description, priority, status, " +
                    "created_by, category_id) " +
                    "VALUES (?, ?, ?, 'OPEN', 1, ?)";


                String response;


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ps.setString(
                        1,
                        title
                    );


                    ps.setString(
                        2,
                        description
                    );


                    ps.setString(
                        3,
                        priority
                    );


                    ps.setInt(
                        4,
                        Integer.parseInt(categoryId)
                    );


                    ps.executeUpdate();


                    response =
                        "Ticket Created Successfully!";


                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response =
                        "Failed to create ticket!";

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.getBytes(
                        StandardCharsets.UTF_8
                    );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // VIEW MY TICKETS API
        // =========================

        server.createContext(
            "/api/my-tickets",
            exchange -> {

                String sql =
                    "SELECT t.ticket_id, t.title, " +
                    "t.priority, t.status, " +
                    "c.category_name, t.created_at " +
                    "FROM tickets t " +
                    "JOIN categories c " +
                    "ON t.category_id = c.category_id " +
                    "WHERE t.created_by = ?";


                StringBuilder response =
                    new StringBuilder();


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    // Rahul's user ID
                    ps.setInt(
                        1,
                        1
                    );


                    ResultSet rs =
                        ps.executeQuery();


                    while (rs.next()) {

                        response
                            .append("Ticket ID: ")
                            .append(
                                rs.getInt("ticket_id")
                            )

                            .append(" | Title: ")
                            .append(
                                rs.getString("title")
                            )

                            .append(" | Priority: ")
                            .append(
                                rs.getString("priority")
                            )

                            .append(" | Status: ")
                            .append(
                                rs.getString("status")
                            )

                            .append(" | Category: ")
                            .append(
                                rs.getString("category_name")
                            )

                            .append("\n\n");
                    }


                    rs.close();
                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response.append(
                        "Database Error!"
                    );

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.toString()
                            .getBytes(
                                StandardCharsets.UTF_8
                            );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // ADMIN - VIEW ALL TICKETS
        // =========================

        server.createContext(
            "/api/all-tickets",
            exchange -> {

                String sql =
                    "SELECT t.ticket_id, t.title, " +
                    "t.priority, t.status, " +
                    "u1.name AS created_by_name, " +
                    "u2.name AS assigned_to_name, " +
                    "c.category_name, t.created_at " +
                    "FROM tickets t " +
                    "JOIN users u1 " +
                    "ON t.created_by = u1.user_id " +
                    "LEFT JOIN users u2 " +
                    "ON t.assigned_to = u2.user_id " +
                    "JOIN categories c " +
                    "ON t.category_id = c.category_id";


                StringBuilder response =
                    new StringBuilder();


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ResultSet rs =
                        ps.executeQuery();


                    while (rs.next()) {

                        response
                            .append("Ticket ID: ")
                            .append(
                                rs.getInt("ticket_id")
                            )

                            .append(" | Title: ")
                            .append(
                                rs.getString("title")
                            )

                            .append(" | Priority: ")
                            .append(
                                rs.getString("priority")
                            )

                            .append(" | Status: ")
                            .append(
                                rs.getString("status")
                            )

                            .append(" | Created By: ")
                            .append(
                                rs.getString(
                                    "created_by_name"
                                )
                            )

                            .append(" | Assigned To: ")
                            .append(
                                rs.getString(
                                    "assigned_to_name"
                                )
                            )

                            .append(" | Category: ")
                            .append(
                                rs.getString(
                                    "category_name"
                                )
                            )

                            .append("\n\n");
                    }


                    rs.close();
                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response.append(
                        "Database Error!"
                    );

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.toString()
                            .getBytes(
                                StandardCharsets.UTF_8
                            );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // ADMIN - ASSIGN TICKET
        // =========================

        server.createContext(
            "/api/assign-ticket",
            exchange -> {

                String query =
                    exchange.getRequestURI()
                           .getQuery();


                String ticketId =
                    query.split("ticketId=")[1]
                         .split("&")[0];


                String agentId =
                    query.split("agentId=")[1]
                         .split("&")[0];


                String sql =
                    "UPDATE tickets " +
                    "SET assigned_to = ?, " +
                    "status = 'IN_PROGRESS' " +
                    "WHERE ticket_id = ?";


                String response;


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ps.setInt(
                        1,
                        Integer.parseInt(agentId)
                    );


                    ps.setInt(
                        2,
                        Integer.parseInt(ticketId)
                    );


                    int rows =
                        ps.executeUpdate();


                    if (rows > 0) {

                        response =
                            "Ticket Assigned Successfully!";

                    } else {

                        response =
                            "Ticket Not Found!";
                    }


                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response =
                        "Failed to assign ticket: " +
                        e.getMessage();

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.getBytes(
                        StandardCharsets.UTF_8
                    );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // AGENT - VIEW ASSIGNED TICKETS
        // =========================

        server.createContext(
            "/api/assigned-tickets",
            exchange -> {

                String query =
                    exchange.getRequestURI()
                           .getQuery();


                String agentId =
                    query.split("agentId=")[1]
                         .split("&")[0];


                String sql =
                    "SELECT t.ticket_id, t.title, " +
                    "t.priority, t.status, " +
                    "c.category_name, t.created_at " +
                    "FROM tickets t " +
                    "JOIN categories c " +
                    "ON t.category_id = c.category_id " +
                    "WHERE t.assigned_to = ?";


                StringBuilder response =
                    new StringBuilder();


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ps.setInt(
                        1,
                        Integer.parseInt(agentId)
                    );


                    ResultSet rs =
                        ps.executeQuery();


                    while (rs.next()) {

                        response
                            .append("Ticket ID: ")
                            .append(
                                rs.getInt("ticket_id")
                            )

                            .append(" | Title: ")
                            .append(
                                rs.getString("title")
                            )

                            .append(" | Priority: ")
                            .append(
                                rs.getString("priority")
                            )

                            .append(" | Status: ")
                            .append(
                                rs.getString("status")
                            )

                            .append(" | Category: ")
                            .append(
                                rs.getString(
                                    "category_name"
                                )
                            )

                            .append(" | Created At: ")
                            .append(
                                rs.getTimestamp(
                                    "created_at"
                                )
                            )

                            .append("\n\n");
                    }


                    rs.close();
                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response.append(
                        "Database Error!"
                    );

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.toString()
                            .getBytes(
                                StandardCharsets.UTF_8
                            );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // AGENT - UPDATE TICKET STATUS
        // =========================

        server.createContext(
            "/api/update-status",
            exchange -> {

                String query =
                    exchange.getRequestURI()
                           .getQuery();


                String ticketId =
                    query.split("ticketId=")[1]
                         .split("&")[0];


                String status =
                    query.split("status=")[1]
                         .split("&")[0];


                String sql =
                    "UPDATE tickets " +
                    "SET status = ? " +
                    "WHERE ticket_id = ?";


                String response;


                try {

                    Connection con =
                        DatabaseConnection.getConnection();


                    PreparedStatement ps =
                        con.prepareStatement(sql);


                    ps.setString(
                        1,
                        status
                    );


                    ps.setInt(
                        2,
                        Integer.parseInt(ticketId)
                    );


                    int rows =
                        ps.executeUpdate();


                    if (rows > 0) {

                        response =
                            "Ticket Status Updated Successfully!";

                    } else {

                        response =
                            "Ticket Not Found!";
                    }


                    ps.close();
                    con.close();


                } catch (Exception e) {

                    response =
                        "Failed to update ticket status: " +
                        e.getMessage();

                    e.printStackTrace();
                }


                exchange.getResponseHeaders()
                        .add(
                            "Access-Control-Allow-Origin",
                            "*"
                        );


                byte[] bytes =
                    response.getBytes(
                        StandardCharsets.UTF_8
                    );


                exchange.sendResponseHeaders(
                    200,
                    bytes.length
                );


                OutputStream os =
                    exchange.getResponseBody();

                os.write(bytes);
                os.close();
            }
        );


        // =========================
        // START SERVER
        // =========================

        server.start();


        System.out.println(
            "Helpdesk Server started at " +
            "http://localhost:8080"
        );
    }
}