import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAO {

    public static void createTicket(Ticket ticket) {

        String sql = "INSERT INTO tickets " +
                     "(title, description, priority, status, created_by, category_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, ticket.getTitle());
            ps.setString(2, ticket.getDescription());
            ps.setString(3, ticket.getPriority());
            ps.setString(4, ticket.getStatus());
            ps.setInt(5, ticket.getCreatedBy());
            ps.setInt(6, ticket.getCategoryId());

            ps.executeUpdate();

            System.out.println("Ticket Created Successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
