import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewTickets {

    public static void viewTicketsByUser(int userId) {

        String sql = "SELECT ticket_id, title, priority, status, category_id, created_at " +
                     "FROM tickets WHERE created_by = ?";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- MY TICKETS -----");

            while (rs.next()) {

                System.out.println("Ticket ID: " + rs.getInt("ticket_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Priority: " + rs.getString("priority"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Category ID: " + rs.getInt("category_id"));
                System.out.println("Created At: " + rs.getTimestamp("created_at"));
                System.out.println("----------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Rahul's user_id
        viewTicketsByUser(1);
    }
}