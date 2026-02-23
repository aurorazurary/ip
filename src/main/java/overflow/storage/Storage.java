package overflow.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import overflow.task.Deadline;
import overflow.task.Event;
import overflow.task.Task;
import overflow.task.Todo;

/**
 * Handles the loading and saving of tasks to a file on disk.
 */
public class Storage {
    private String filePath;

    /**
     * Creates a Storage object with the specified file path.
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
        int lineNumber = 0;

        while (scanner.hasNext()) {
            lineNumber++;
            String line = scanner.nextLine();

            try {
                String[] parts = line.split(" \\| ");

                // Validate minimum parts
                if (parts.length < 3) {
                    System.err.println("Warning: Skipping corrupted line " + lineNumber + ": " + line);
                    continue;
                }

                String taskType = parts[0];
                String taskStatus = parts[1];
                String taskName = parts[2];

                Task task = null;
                switch (taskType) {
                case "T" -> task = new Todo(taskName);
                case "E" -> {
                    if (parts.length < 5) {
                        System.err.println("Warning: Skipping incomplete event at line " + lineNumber);
                        continue;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime startTime = LocalDateTime.parse(parts[3], formatter);
                    LocalDateTime endTime = LocalDateTime.parse(parts[4], formatter);
                    task = new Event(taskName, startTime, endTime);
                }
                case "D" -> {
                    if (parts.length < 4) {
                        System.err.println("Warning: Skipping incomplete deadline at line " + lineNumber);
                        continue;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
                    LocalDateTime deadline = LocalDateTime.parse(parts[3], formatter);
                    task = new Deadline(taskName, deadline);
                }
                default -> {
                    System.err.println("Warning: Unknown task type '" + taskType + "' at line " + lineNumber);
                    continue;
                }
                }

                if (taskStatus.equals("1")) {
                    task.mark();
                }

                tasks.add(task);

            } catch (DateTimeParseException e) {
                System.err.println("Warning: Invalid date format at line " + lineNumber + ": " + e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("Warning: Corrupted data at line " + lineNumber + ": " + line);
            } catch (Exception e) {
                System.err.println("Warning: Error loading task at line " + lineNumber + ": " + e.getMessage());
            }
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
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: " + directory.getAbsolutePath()
                + "\nTry creating the data folder manually in the same directory as the jar file."
                + "\nSorry for the inconvenience!");
            }
        }

        FileWriter writer = new FileWriter(filePath);
        for (Task task: tasks) {
            writer.write(task.toFileFormat() + "\n");
        }

        writer.close();
    }
}

