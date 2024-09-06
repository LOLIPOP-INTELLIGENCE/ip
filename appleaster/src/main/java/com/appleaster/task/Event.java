package main.java.com.appleaster.task;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private ObjectProperty<LocalDateTime> from;
    private ObjectProperty<LocalDateTime> to;
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy, hh:mm a");

    public Event(String description, String from, String to) {
        super(description, TaskType.EVENT);
        this.from = new SimpleObjectProperty<>(LocalDateTime.parse(from, INPUT_FORMAT));
        this.to = new SimpleObjectProperty<>(LocalDateTime.parse(to, INPUT_FORMAT));
    }

    public LocalDateTime getFrom() {
        return from.get();
    }

    public void setFrom(LocalDateTime from) {
        this.from.set(from);
    }

    public ObjectProperty<LocalDateTime> fromProperty() {
        return from;
    }

    public LocalDateTime getTo() {
        return to.get();
    }

    public void setTo(LocalDateTime to) {
        this.to.set(to);
    }

    public ObjectProperty<LocalDateTime> toProperty() {
        return to;
    }

    @Override
    public String toString() {
        return String.format("[%s][%s] %s (from: %s to: %s)", 
                             getType().getSymbol(), 
                             isCompleted() ? "X" : " ", 
                             getDescription(), 
                             getFrom().format(OUTPUT_FORMAT), 
                             getTo().format(OUTPUT_FORMAT));
    }
}