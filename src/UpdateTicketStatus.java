import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateTicketStatus {

    public static void updateStatus(int ticketId, String status) {

        String sql = "UPDATE tickets SET status = ? WHERE ticket_id = ?";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, ticketId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Ticket Status Updated Successfully!");
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

        // Ticket 1 → RESOLVED
        updateStatus(1, "RESOLVED");
    }
}