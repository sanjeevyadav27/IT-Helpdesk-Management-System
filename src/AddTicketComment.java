import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddTicketComment {

    public static void addComment(int ticketId, int userId, String comment) {

        String sql = "INSERT INTO ticket_comments " +
                     "(ticket_id, user_id, comment) VALUES (?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, ticketId);
            ps.setInt(2, userId);
            ps.setString(3, comment);

            ps.executeUpdate();

            System.out.println("Comment Added Successfully!");

            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // Ticket 1, Agent 3
        addComment(
            1,
            3,
            "Laptop power issue resolved after checking the adapter."
        );
    }
}