package overflow.storage;

import overflow.task.Deadline;
import overflow.task.Event;
import overflow.task.Task;
import overflow.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the loading and saving of tasks to a file on disk.
 */
public class Storage {
    private String filePath;

    /**
     * Creates a overflow.storage.Storage object with the specified file path.
     *
     * @param filePath Path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file.
     *
     * @return ArrayList of tasks loaded from the file.
     * @throws FileNotFoundException If the file cannot be found.
     */
    public ArrayList<Task> loadTasks() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String[] parts = scanner.nextLine().split(" \\| ");
            String taskType = parts[0];
            String taskStatus = parts[1];
            String taskName = parts[2];

            Task task = null;

            switch (taskType) {
            case "T" -> task = new Todo(taskName);
            case "E" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime startTime = LocalDateTime.parse(parts[3], formatter);
                LocalDateTime endTime = LocalDateTime.parse(parts[4], formatter);
                task = new Event(taskName, startTime, endTime);
            }
            case "D" -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                LocalDateTime deadline = LocalDateTime.parse(parts[3], formatter);
                task = new Deadline(taskName, deadline);
            }
            }

            if (taskStatus.equals("1")) {
                task.mark();
            }

            tasks.add(task);
        }
        scanner.close();

        return tasks;
    }

    /**
     * Saves tasks to the file.
     *
     * @param tasks ArrayList of tasks to save.
     * @throws IOException If there's an error writing to the file.
     */
    public void saveChange(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        File directory = file.getParentFile();

        // Create the directory if it doesn't exist
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        FileWriter writer = new FileWriter(filePath);
        for (Task task: tasks) {
            writer.write(task.toFileFormat() + "\n");
        }

        writer.close();
    }
}
