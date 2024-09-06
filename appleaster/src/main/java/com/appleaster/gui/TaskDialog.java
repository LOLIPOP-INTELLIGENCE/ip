package main.java.com.appleaster.gui;

import main.java.com.appleaster.task.Task;
import main.java.com.appleaster.task.Todo;
import main.java.com.appleaster.task.Deadline;
import main.java.com.appleaster.task.Event;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDialog extends Dialog<Task> {

    private TextField descriptionField;
    private ComboBox<String> typeComboBox;
    private DatePicker datePicker;
    private TextField timeField;
    private DatePicker endDatePicker;
    private TextField endTimeField;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public TaskDialog(Stage owner) {
        this(owner, null);
    }

    public TaskDialog(Stage owner, Task task) {
        setTitle(task == null ? "Add New Task" : "Edit Task");
        setHeaderText(null);
        initOwner(owner);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = createDialogContent();
        getDialogPane().setContent(grid);

        if (task != null) {
            populateFields(task);
        }

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return createTask();
            }
            return null;
        });
    }

    private GridPane createDialogContent() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        descriptionField = new TextField();
        typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Todo", "Deadline", "Event");
        datePicker = new DatePicker();
        timeField = new TextField();
        endDatePicker = new DatePicker();
        endTimeField = new TextField();

        grid.add(new Label("Description:"), 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(typeComboBox, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Time:"), 0, 3);
        grid.add(timeField, 1, 3);
        grid.add(new Label("End Date:"), 0, 4);
        grid.add(endDatePicker, 1, 4);
        grid.add(new Label("End Time:"), 0, 5);
        grid.add(endTimeField, 1, 5);

        typeComboBox.setOnAction(e -> updateFieldVisibility());
        updateFieldVisibility();

        return grid;
    }

    private void updateFieldVisibility() {
        String selectedType = typeComboBox.getValue();
        boolean isDeadline = "Deadline".equals(selectedType);
        boolean isEvent = "Event".equals(selectedType);

        datePicker.setVisible(isDeadline || isEvent);
        datePicker.setManaged(isDeadline || isEvent);
        timeField.setVisible(isDeadline || isEvent);
        timeField.setManaged(isDeadline || isEvent);
        endDatePicker.setVisible(isEvent);
        endDatePicker.setManaged(isEvent);
        endTimeField.setVisible(isEvent);
        endTimeField.setManaged(isEvent);
    }

    private void populateFields(Task task) {
        descriptionField.setText(task.getDescription());
        
        if (task instanceof Todo) {
            typeComboBox.setValue("Todo");
        } else if (task instanceof Deadline) {
            typeComboBox.setValue("Deadline");
            Deadline deadline = (Deadline) task;
            datePicker.setValue(deadline.getBy().toLocalDate());
            timeField.setText(deadline.getBy().format(TIME_FORMATTER));
        } else if (task instanceof Event) {
            typeComboBox.setValue("Event");
            Event event = (Event) task;
            datePicker.setValue(event.getFrom().toLocalDate());
            timeField.setText(event.getFrom().format(TIME_FORMATTER));
            endDatePicker.setValue(event.getTo().toLocalDate());
            endTimeField.setText(event.getTo().format(TIME_FORMATTER));
        }
        
        updateFieldVisibility();
    }

    private Task createTask() {
        String description = descriptionField.getText();
        String type = typeComboBox.getValue();

        switch (type) {
            case "Todo":
                return new Todo(description);
            case "Deadline":
                LocalDateTime deadline = LocalDateTime.of(datePicker.getValue(), LocalDateTime.parse(timeField.getText(), TIME_FORMATTER).toLocalTime());
                return new Deadline(description, deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")));
            case "Event":
                LocalDateTime from = LocalDateTime.of(datePicker.getValue(), LocalDateTime.parse(timeField.getText(), TIME_FORMATTER).toLocalTime());
                LocalDateTime to = LocalDateTime.of(endDatePicker.getValue(), LocalDateTime.parse(endTimeField.getText(), TIME_FORMATTER).toLocalTime());
                return new Event(description, from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")), to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")));
            default:
                throw new IllegalStateException("Unexpected task type: " + type);
        }
    }
}