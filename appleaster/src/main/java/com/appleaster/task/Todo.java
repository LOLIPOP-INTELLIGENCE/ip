package main.java.com.appleaster.task;

public class Todo extends Task {
    public Todo(String description) {
        super(description, TaskType.TODO);
    }

    // No additional properties needed for Todo

    @Override
    public String toString() {
        return String.format("[%s][%s] %s", 
                             getType().getSymbol(), 
                             isCompleted() ? "X" : " ", 
                             getDescription());
    }

    // Clone method for creating a copy of the Todo
    public Todo clone() {
        Todo clonedTodo = new Todo(this.getDescription());
        clonedTodo.setCompleted(this.isCompleted());
        return clonedTodo;
    }

    // equals method for comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Todo todo = (Todo) obj;
        return isCompleted() == todo.isCompleted() &&
               getDescription().equals(todo.getDescription());
    }

    // hashCode method to complement equals
    @Override
    public int hashCode() {
        int result = getDescription().hashCode();
        result = 31 * result + (isCompleted() ? 1 : 0);
        return result;
    }
}