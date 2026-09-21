public class TestUser {

    public static void main(String[] args) {

        User user = new User(
            "Rahul Sharma",
            "rahul@gmail.com",
            "12345",
            "EMPLOYEE"
        );

        UserDAO.addUser(user);
    }
}