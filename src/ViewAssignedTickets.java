import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewAssignedTickets {

    public static void viewAssignedTickets(int agentId) {

        String sql = "SELECT ticket_id, title, priority, status, created_by, category_id " +
                     "FROM tickets WHERE assigned_to = ?";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, agentId);

            ResultSet rs = ps.executeQuery();

            System.out.println("----- ASSIGNED TICKETS -----");

            while (rs.next()) {
                System.out.println("Ticket ID: " + rs.getInt("ticket_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Priority: " + rs.getString("priority"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("Created By: " + rs.getInt("created_by"));
                System.out.println("Category ID: " + rs.getInt("category_id"));
                System.out.println("----------------------------");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        viewAssignedTickets(3);
    }
}