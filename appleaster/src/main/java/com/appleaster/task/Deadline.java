package main.java.com.appleaster.task;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private ObjectProperty<LocalDateTime> by;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");

    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = new SimpleObjectProperty<>(LocalDateTime.parse(by, INPUT_FORMAT));
    }

    public LocalDateTime getBy() {
        return by.get();
    }

    public void setBy(LocalDateTime by) {
        this.by.set(by);
    }

    public ObjectProperty<LocalDateTime> byProperty() {
        return by;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (by: %s)", 
                             getType().getSymbol(), 
                             isCompleted() ? "X" : " ", 
                             getDescription(), 
                             getBy().format(OUTPUT_FORMAT));
    }
}