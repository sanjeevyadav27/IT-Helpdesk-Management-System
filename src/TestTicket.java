public class TestTicket {

    public static void main(String[] args) {

        Ticket ticket = new Ticket(
            "Laptop not starting",
            "My office laptop does not turn on.",
            "HIGH",
            1,
            1
        );

        TicketDAO.createTicket(ticket);
    }
}