import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAllTickets {

    public static void viewAllTickets() {

        String sql = "SELECT t.ticket_id, t.title, t.priority, t.status, " +
                     "u1.name AS created_by_name, " +
                     "u2.name AS assigned_to_name, " +
                     "c.category_name, t.created_at " +
                     "FROM tickets t " +
                     "JOIN users u1 ON t.created_by = u1.user_id " +
                     "LEFT JOIN users u2 ON t.assigned_to = u2.user_id " +
                     "JOIN categories c ON t.category_id = c.category_id";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("===== ALL TICKETS =====");

            while (rs.next()) {

                System.out.println("Ticket ID: " + rs.getInt("ticket_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Priority: " + rs.getString("priority"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Created By: " + rs.getString("created_by_name"));
                System.out.println("Assigned To: " + rs.getString("assigned_to_name"));
                System.out.println("Category: " + rs.getString("category_name"));
                System.out.println("Created At: " + rs.getTimestamp("created_at"));
                System.out.println("-----------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        viewAllTickets();
    }
}