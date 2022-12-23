import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Task {
    private final int id;

    private String name;
    private String description;

    private final LocalDateTime createdDate;
    private LocalDateTime completeDate;

    private boolean completed;

    public Task(int id, String name, String description, LocalDateTime completeDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.completeDate = completeDate;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDateTime completeDate) {
        this.completeDate = completeDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String completed = (this.completed) ? "completed" : "not completed";
        return String.format(
                "Id: %d, Name: %s, Description: %s, Completed: %s\nCreated at: %s, Complete Date: %s",
                id, name, description, completed,
                createdDate.format(DateTimeFormatter.ofPattern("y:M:d:H:m")),
                completeDate.format(DateTimeFormatter.ofPattern("y:M:d:H:m")));
    }
}
