import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {

        String url = "jdbc:mysql://localhost:3306/helpdesk_db";
        String username = "root";
        String password = "YOUR_ACTUAL_MYSQL_PASSWORD";

        try {
            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        try {
            Connection con = getConnection();

            if (con != null) {
                System.out.println("Database Connected Successfully!");
                con.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}