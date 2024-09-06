package main.java.com.appleaster.storage;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import main.java.com.appleaster.exception.AppleasterException;
import main.java.com.appleaster.task.Deadline;
import main.java.com.appleaster.task.Event;
import main.java.com.appleaster.task.Task;
import main.java.com.appleaster.task.Todo;

public class Storage {
    private static final String DATA_DIRECTORY = "data";
    private final Path filePath;
    private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    public List<Task> load() throws AppleasterException {
        try {
            createDataDirectoryIfNotExists();
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }

            List<Task> tasks = new ArrayList<>();
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    tasks.add(parseTask(line));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new AppleasterException("Error loading tasks: " + e.getMessage());
        }
    }

    public void save(List<Task> tasks) throws AppleasterException {
        try {
            createDataDirectoryIfNotExists();
            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (Task task : tasks) {
                    writer.write(formatTask(task));
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new AppleasterException("Error saving tasks: " + e.getMessage());
        }
    }

    private void createDataDirectoryIfNotExists() throws IOException {
        Files.createDirectories(filePath.getParent());
    }

    private String formatTask(Task task) {
        if (task instanceof Todo) {
            return String.format("T | %d | %s", task.isCompleted() ? 1 : 0, task.getDescription());
        } else if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return String.format("D | %d | %s | %s", task.isCompleted() ? 1 : 0, task.getDescription(), deadline.getBy().format(FILE_DATE_FORMAT));
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return String.format("E | %d | %s | %s | %s", task.isCompleted() ? 1 : 0, task.getDescription(), 
                                 event.getFrom().format(FILE_DATE_FORMAT), event.getTo().format(FILE_DATE_FORMAT));
        }
        throw new IllegalArgumentException("Unknown task type");
    }

    private Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        String type = parts[0];
        boolean isCompleted = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
            case "T":
                task = new Todo(description);
                break;
            case "D":
                task = new Deadline(description, parts[3]);
                break;
            case "E":
                task = new Event(description, parts[3], parts[4]);
                break;
            default:
                throw new IllegalArgumentException("Unknown task type: " + type);
        }

        if (isCompleted) {
            task.setCompleted(true);
        }
        return task;
    }
}