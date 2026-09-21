import java.sql.Connection;
import java.sql.PreparedStatement;

public class AssignTicket {

    public static void assignTicket(int ticketId, int agentId) {

        String sql = "UPDATE tickets SET assigned_to = ?, status = 'IN_PROGRESS' " +
                     "WHERE ticket_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, agentId);
            ps.setInt(2, ticketId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Ticket Assigned Successfully!");
            } else {
                System.out.println("Ticket Not Found!");
            }

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Ticket 1 → Agent 2
        assignTicket(1, 3);
    }
}