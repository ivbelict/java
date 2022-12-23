public enum ViewCommands {
    ADD_TASK("Add task"),
    EDIT_TASK("Edit task"),
    REMOVE_TASK("Remove task"),
    LIST_TASKS("List tasks"),
    LIST_COMPLETED_TASKS("List completed tasks"),
    LIST_UNCOMPLETED_TASKS("List uncompleted tasks"),
    DESCRIBE_TASK("Describe task"),
    QUIT("Quit"),
    SET_COMPLETE_TASK("Set complete task");

    private String name;

    ViewCommands(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
