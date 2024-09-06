package main.java.com.appleaster.gui;

import main.java.com.appleaster.task.Task;
import javafx.scene.control.ListView;

import java.util.List;

public class TaskListView extends ListView<Task> {

    public TaskListView() {
        setCellFactory(param -> new TaskListCell());
    }

    public void setTasks(List<Task> tasks) {
        getItems().clear();
        getItems().addAll(tasks);
    }

    public Task getSelectedTask() {
        return getSelectionModel().getSelectedItem();
    }

    private static class TaskListCell extends javafx.scene.control.ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);

            if (empty || task == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(task.toString());
            }
        }
    }
}