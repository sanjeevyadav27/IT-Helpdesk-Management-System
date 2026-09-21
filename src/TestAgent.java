public class TestAgent {

    public static void main(String[] args) {

        User agent = new User(
            "Support Agent",
            "agent@gmail.com",
            "12345",
            "AGENT"
        );

        UserDAO.addUser(agent);
    }
}
