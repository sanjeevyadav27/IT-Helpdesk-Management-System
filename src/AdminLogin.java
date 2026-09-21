import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin {

    public static void loginAdmin(String email, String password) {

        String sql = "SELECT name, role FROM users " +
                     "WHERE email = ? AND password = ? AND role = 'ADMIN'";

        try {
            Connection con = DatabaseConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("Admin Login Successful!");
                System.out.println("Welcome, " + rs.getString("name"));
                System.out.println("Role: " + rs.getString("role"));

            } else {

                System.out.println("Invalid Admin Credentials!");

            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        loginAdmin("admin@gmail.com", "12345");
    }
}