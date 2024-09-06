package main.java.com.appleaster.task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Task {
    protected StringProperty description;
    protected BooleanProperty completed;
    protected TaskType type;

    public Task(String description, TaskType type) {
        this.description = new SimpleStringProperty(description);
        this.completed = new SimpleBooleanProperty(false);
        this.type = type;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public TaskType getType() {
        return type;
    }

    @Override
    public abstract String toString();

    public void markAsDone() {
        setCompleted(true);
    }    
}