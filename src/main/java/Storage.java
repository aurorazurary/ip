import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        File file = new File(filePath);
        System.out.println("File location: " + file.getAbsolutePath());
    }

    public ArrayList<Task> loadTasks() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // If file doesn't exit, return empty list
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
                    String startTime = parts[3];
                    String endTime = parts[4];
                    task = new Event(taskName, startTime, endTime);
                }
            case "D" -> {
                    String deadline = parts[3];
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

    public void saveChange(ArrayList<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Task task: tasks) {
            writer.write(task.toFileFormat() + "\n");
        }

        writer.close();
    }
}
