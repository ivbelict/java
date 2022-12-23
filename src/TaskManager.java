import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskManager {
    public static final int DATE_FORMAT_COUNT = 5;
    private final ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String name, String description, String completeDateFormat) {
        Task task;

        if(tasks.size() == 0) {
            task = new Task(1, name, description, convertDate(completeDateFormat));
        } else {
            task = new Task(tasks.get(tasks.size()-1).getId() + 1, name, description, convertDate(completeDateFormat));
        }

        tasks.add(task);
    }

    public void editTask(int id, String name, String description, String completeDateFormat) {
        Task task = getTask(id);

        task.setName(name);
        task.setDescription(description);
        task.setCompleteDate(convertDate(completeDateFormat));
    }

    public void removeTask(int id) {
        tasks.remove(getTask(id));
    }

    public Task getTask(int id) {
        for(Task task : tasks) {
            if(task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public LocalDateTime convertDate(String dateFormat) {
        int[] date = new int[DATE_FORMAT_COUNT];

        int i = 0;
        for(String dateStr : dateFormat.split(":")) {
            date[i] = Integer.parseInt(dateStr);
            i++;
        }

        return LocalDateTime.of(date[0], date[1], date[2], date[3], date[4]);
    }

    public String convertTaskCreatedDate(Task task) {
        return task.getCreatedDate().format(DateTimeFormatter.ofPattern("y:M:d:H:m"));
    }

    public String convertTaskCompleteDate(Task task) {
        return task.getCompleteDate().format(DateTimeFormatter.ofPattern("y:M:d:H:m"));
    }
}
