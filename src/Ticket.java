public class Ticket {

    private int ticketId;
    private String title;
    private String description;
    private String priority;
    private String status;
    private int createdBy;
    private int categoryId;

    public Ticket(String title, String description, String priority,
                  int createdBy, int categoryId) {

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdBy = createdBy;
        this.categoryId = categoryId;
        this.status = "OPEN";
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getCategoryId() {
        return categoryId;
    }
}