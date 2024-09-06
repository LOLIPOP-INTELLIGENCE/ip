package main.java.com.appleaster.gui;

import main.java.com.appleaster.task.Task;
import main.java.com.appleaster.task.TaskList;
import main.java.com.appleaster.storage.Storage;
import main.java.com.appleaster.exception.AppleasterException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainController {

    @FXML private TaskListView taskListView;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button markButton;
    @FXML private TextField searchField;
    @FXML private DatePicker datePicker;

    private TaskList taskList;
    private Storage storage;
    private Stage stage;

    public void initialize() {
        storage = new Storage("data/tasks.txt");
        try {
            taskList = new TaskList(storage.load());
        } catch (AppleasterException e) {
            showError("Error loading tasks", e.getMessage());
            taskList = new TaskList();
        }

        taskListView.setTasks(taskList.getTasks());

        addButton.setOnAction(e -> showAddTaskDialog());
        editButton.setOnAction(e -> showEditTaskDialog());
        deleteButton.setOnAction(e -> deleteSelectedTask());
        markButton.setOnAction(e -> toggleTaskCompletion());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchTasks(newValue));
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> filterTasksByDate(newValue));
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void showAddTaskDialog() {
        TaskDialog dialog = new TaskDialog(stage);
        Task newTask = dialog.showAndWait().orElse(null);
        if (newTask != null) {
            taskList.addTask(newTask);
            updateTaskList();
            saveTaskList();
        }
    }

    private void showEditTaskDialog() {
        Task selectedTask = taskListView.getSelectedTask();
        if (selectedTask != null) {
            TaskDialog dialog = new TaskDialog(stage, selectedTask);
            Task editedTask = dialog.showAndWait().orElse(null);
            if (editedTask != null) {
                try {
                    taskList.updateTask(selectedTask, editedTask);
                    updateTaskList();
                    saveTaskList();
                } catch (AppleasterException e) {
                    showError("Error updating task", e.getMessage());
                }
            }
        }
    }

    private void deleteSelectedTask() {
        Task selectedTask = taskListView.getSelectedTask();
        if (selectedTask != null) {
            try {
                taskList.deleteTask(taskList.getTasks().indexOf(selectedTask));
                updateTaskList();
                saveTaskList();
            } catch (AppleasterException e) {
                showError("Error deleting task", e.getMessage());
            }
        }
    }

    private void toggleTaskCompletion() {
        Task selectedTask = taskListView.getSelectedTask();
        if (selectedTask != null) {
            try {
                taskList.markTask(taskList.getTasks().indexOf(selectedTask), !selectedTask.isCompleted());
                updateTaskList();
                saveTaskList();
            } catch (AppleasterException e) {
                showError("Error marking task", e.getMessage());
            }
        }
    }

    private void searchTasks(String keyword) {
        if (keyword.isEmpty()) {
            taskListView.setTasks(taskList.getTasks());
        } else {
            taskListView.setTasks(taskList.findTasks(keyword));
        }
    }

    private void filterTasksByDate(LocalDate date) {
        if (date == null) {
            taskListView.setTasks(taskList.getTasks());
        } else {
            taskListView.setTasks(taskList.getTasksOnDate(date));
        }
    }

    private void updateTaskList() {
        taskListView.setTasks(taskList.getTasks());
    }

    private void saveTaskList() {
        try {
            storage.save(taskList.getTasks());
        } catch (AppleasterException e) {
            showError("Error saving tasks", e.getMessage());
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}