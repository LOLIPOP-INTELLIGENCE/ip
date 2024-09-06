package main.java.com.appleaster.task;

import main.java.com.appleaster.exception.AppleasterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("MMM d yyyy");
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param task The task to be added.
     */
  public void addTask(Task task) {
    tasks.add(task);
    System.out.println("Got it. I've added this task:");
    System.out.println("  " + task);
    System.out.printf("Now you have %d task%s in the list.%n", 
        tasks.size(), tasks.size() == 1 ? "" : "s");
  }

    /**
     * Lists all tasks currently in the list.
     */
  public void listTasks() {
    if (tasks.isEmpty()) {
      System.out.println("Your task list is empty.");
    } else {
      System.out.println("Here are the tasks in your list:");
      for (int i = 0; i < tasks.size(); i++) {
        System.out.printf("%d.%s%n", i + 1, tasks.get(i));
      }
    }
  }

    public List<Task> findTasks(String keyword) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
          if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
            matchingTasks.add(task);
          }
        }
        return matchingTasks;
    }
    
  /**
   * Displays tasks that contain the given keyword in their description.
   *
   * @param keyword The keyword to search for.
   */
  public void displayMatchingTasks(String keyword) {
    List<Task> matchingTasks = findTasks(keyword);
    if (matchingTasks.isEmpty()) {
      System.out.println("No matching tasks found.");
    } else {
      System.out.println("Here are the matching tasks in your list:");
      for (int i = 0; i < matchingTasks.size(); i++) {
        System.out.printf("%d.%s%n", i + 1, matchingTasks.get(i));
      }
    }
  }    

    /**
     * Marks a task as done or not done.
     *
     * @param index The index of the task to be marked.
     * @param isDone True if the task should be marked as done, false otherwise.
     * @throws AppleasterException If the index is invalid.
     */
  public void markTask(int index, boolean isDone) throws AppleasterException {
    if (index < 0 || index >= tasks.size()) {
      throw new AppleasterException("Invalid task number.");
    }
    Task task = tasks.get(index);
    task.setCompleted(isDone);
    System.out.printf("Nice! I've marked this task as %s:%n", 
        isDone ? "done" : "not done");
    System.out.println("  " + task);
  }

    /**
     * Deletes a task from the list.
     *
     * @param index The index of the task to be deleted.
     * @throws AppleasterException If the index is invalid.
     */
  public void deleteTask(int index) throws AppleasterException {
    if (index < 0 || index >= tasks.size()) {
      throw new AppleasterException("Invalid task number.");
    }
    Task deletedTask = tasks.remove(index);
    System.out.println("Noted. I've removed this task:");
    System.out.println("  " + deletedTask);
    System.out.printf("Now you have %d task%s in the list.%n", 
        tasks.size(), tasks.size() == 1 ? "" : "s");
  }


  public List<Task> getTasks() {
    return new ArrayList<>(tasks);
  }

    /**
     * Updates an existing task with new information.
     *
     * @param oldTask The task to be updated.
     * @param newTask The new task information.
     * @throws AppleasterException If the old task is not found in the list.
     */
    public void updateTask(Task oldTask, Task newTask) throws AppleasterException {
      int index = tasks.indexOf(oldTask);
      if (index == -1) {
          throw new AppleasterException("Task not found in the list.");
      }
      tasks.set(index, newTask);
  }

  /**
   * Gets all tasks scheduled for a specific date.
   *
   * @param date The date to filter tasks by.
   * @return A list of tasks scheduled for the given date.
   */
  public List<Task> getTasksOnDate(LocalDate date) {
      List<Task> tasksOnDate = new ArrayList<>();
      for (Task task : tasks) {
          if (task instanceof Deadline) {
              Deadline deadline = (Deadline) task;
              if (deadline.getBy().toLocalDate().equals(date)) {
                  tasksOnDate.add(task);
              }
          } else if (task instanceof Event) {
              Event event = (Event) task;
              if (event.getFrom().toLocalDate().equals(date) 
                  || event.getTo().toLocalDate().equals(date)) {
                  tasksOnDate.add(task);
              }
          }
      }
      return tasksOnDate;
  }

  public void listTasksOnDate(LocalDate date) {
      List<Task> tasksOnDate = getTasksOnDate(date);
      if (tasksOnDate.isEmpty()) {
          System.out.println("There are no tasks on " + date.format(DATE_FORMATTER));
      } else {
          System.out.println("Here are the tasks on " + date.format(DATE_FORMATTER) + ":");
          for (int i = 0; i < tasksOnDate.size(); i++) {
              System.out.printf("%d.%s%n", i + 1, tasksOnDate.get(i));
          }
      }
  }  
}