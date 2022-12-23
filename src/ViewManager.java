import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ViewManager {
    private static Scanner scanner;
    private static TaskManager taskManager;

    public static void start() {
        scanner = new Scanner(System.in);
        taskManager = new TaskManager();

        taskManager.addTask("Test task1", "This is description of the task", "2023:01:01:18:00");
        taskManager.addTask("Test task2", "This is task", "2022:06:30:23:12");

        while(true) {
            System.out.println("\nSelect an action: ");
            listActions();

            try{
                int taskNumber = Integer.parseInt(scanner.nextLine());
                executeTask(taskNumber-1);
            } catch(Exception exception) {
                if(exception instanceof NumberFormatException) {
                    System.out.println("Please enter a number!");
                } else if(exception instanceof DateTimeException) {
                    System.out.println("Please enter a valid date time!");
                } else {
                    System.out.println("Something went wrong!");
                }
            }
        }
    }

    private static void listActions() {
        ViewCommands[] commands = ViewCommands.values();

        for(int i = 0; i < commands.length; i++) {
            int commandNumber = i+1;
            System.out.println("    " + commandNumber + ") " + commands[i].toString());
        }
    }

    private static void executeTask(int number) {
        ViewCommands command = ViewCommands.values()[number];

        switch(command) {
            case ADD_TASK:
                addTask();
                break;
            case EDIT_TASK:
                editTask();
                break;
            case REMOVE_TASK:
                removeTask();
                break;
            case SET_COMPLETE_TASK:
                setCompleteTask();
                break;
            case LIST_TASKS:
                listTasks();
                break;
            case LIST_COMPLETED_TASKS:
                listCompletedOrIncompletedTasks(true);
                break;
            case LIST_UNCOMPLETED_TASKS:
                listCompletedOrIncompletedTasks(false);
                break;
            case DESCRIBE_TASK:
                listTask();
                break;
            case QUIT:
                System.out.println("Exit");
                System.exit(0);
                break;
        }
    }

    private static HashMap<String, String> inputTask() {
        HashMap<String, String> taskInfo = new HashMap<>();

        System.out.print("Input a name: ");
        String name = scanner.nextLine();
        taskInfo.put("name", name);

        System.out.print("Input a description: ");
        String description = scanner.nextLine();
        taskInfo.put("description", description);

        System.out.print("Input complete date(Y:M:D:H:M): ");
        String completeDateFormat = scanner.nextLine();
        taskInfo.put("date", completeDateFormat);

        return taskInfo;
    }

    private static void addTask() {
        HashMap<String, String> taskInfo = inputTask();

        taskManager.addTask(taskInfo.get("name"), taskInfo.get("description"), taskInfo.get("date"));
        System.out.println("Task added!");
    }

    private static boolean checkTask(int id) {
        if(taskManager.getTask(id) != null) {
            return true;
        }

        System.out.println("Task doesn't exists!");
        return false;
    }

    private static void editTask() {
        System.out.print("Input a task id: ");
        int id = Integer.parseInt(scanner.nextLine());

        if(!checkTask(id)) {
            return;
        }

        HashMap<String, String> taskInfo = inputTask();
        taskManager.editTask(id, taskInfo.get("name"), taskInfo.get("description"), taskInfo.get("date"));
        System.out.println("Task edited!");
    }

    private static void removeTask() {
        System.out.print("Input a task id: ");
        int id = Integer.parseInt(scanner.nextLine());

        if(!checkTask(id)) {
            return;
        }

        taskManager.removeTask(id);
        System.out.println("Task has removed!");
    }

    private static ArrayList<Task> getTasksByCompleteDate(String formatDate) {
        ArrayList<Task> sortTasks = new ArrayList<>();
        formatDate = taskManager.convertDate(formatDate).format(DateTimeFormatter.ofPattern("y:M:d:H:m"));

        for(Task task : taskManager.getTasks()) {
            if(taskManager.convertTaskCompleteDate(task).equals(formatDate)) {
                sortTasks.add(task);
            }
        }

        return sortTasks;
    }

    private static ArrayList<Task> getTasksByCreatedDate(String formatDate) {
        ArrayList<Task> sortTasks = new ArrayList<>();
        formatDate = taskManager.convertDate(formatDate).format(DateTimeFormatter.ofPattern("y:M:d:H:m"));

        for (Task task : taskManager.getTasks()) {
            if (taskManager.convertTaskCreatedDate(task).equals(formatDate)) {
                sortTasks.add(task);
            }
        }

        return sortTasks;
    }

    private static int getDateTypeOfTask() {
        System.out.println("1)By Created Date\n2)By Complete Date\nOr any number to view all tasks");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void listTasks() {
        ArrayList<Task> tasks;
        int type = getDateTypeOfTask();

        if(type == 1 || type == 2) {
            System.out.println("Input date(Y:M:D:H:M):");
            String formatDate = scanner.nextLine();
            if(type == 1) {
                tasks = getTasksByCreatedDate(formatDate);
            } else {
                tasks = getTasksByCompleteDate(formatDate);
            }
        } else {
            tasks = taskManager.getTasks();
        }

        System.out.println("TASK LIST: ");
        for(Task task : tasks) {
            System.out.println("    " + task.toString() + "\n");
        }
    }

    private static void listCompletedOrIncompletedTasks(boolean isCompleted) {
        ArrayList<Task> tasks;
        int type = getDateTypeOfTask();

        if(type == 1 || type == 2) {
            System.out.println("Input date(Y:M:D:H:M):");
            String formatDate = scanner.nextLine();
            if(type == 1) {
                tasks = getTasksByCreatedDate(formatDate);
            } else {
                tasks = getTasksByCompleteDate(formatDate);
            }
        } else {
            tasks = taskManager.getTasks();
        }

        System.out.println("TASK LIST: ");
        for(Task task : tasks) {
            if((task.isCompleted() && isCompleted) || (!task.isCompleted() && !isCompleted)) {
                System.out.println("    " + task + "\n");
            }
        }
    }

    private static void listTask() {
        System.out.print("Input a task id: ");
        int id = Integer.parseInt(scanner.nextLine());

        if(!checkTask(id)) {
            return;
        }

        System.out.println("Task: \n    " + taskManager.getTask(id).toString());
    }

    private static void setCompleteTask() {
        System.out.print("Input a task id: ");
        int id = Integer.parseInt(scanner.nextLine());

        if(!checkTask(id)) {
            return;
        }

        taskManager.getTask(id).setCompleted(true);
        System.out.println("Task completed!");
    }
}
