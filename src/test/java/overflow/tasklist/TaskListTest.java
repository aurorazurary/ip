package overflow.tasklist;

import overflow.exception.OverflowException;
import overflow.task.Task;
import overflow.task.Todo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the TaskList class.
 */
public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }

    @Test
    public void taskList_newList_isEmpty() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void add_singleTask_increasesSize() {
        Task task = new Todo("test task");
        taskList.add(task);
        assertEquals(1, taskList.size());
    }

    @Test
    public void add_multipleTasks_increasesSize() {
        taskList.add(new Todo("task 1"));
        taskList.add(new Todo("task 2"));
        taskList.add(new Todo("task 3"));
        assertEquals(3, taskList.size());
    }

    @Test
    public void get_validIndex_returnsTask() throws OverflowException {
        Task task = new Todo("test task");
        taskList.add(task);
        assertEquals(task, taskList.get(0));
    }

    @Test
    public void get_invalidIndex_throwsException() {
        taskList.add(new Todo("test task"));
        
        assertThrows(OverflowException.class, () -> {
            taskList.get(5);
        });
    }

    @Test
    public void get_negativeIndex_throwsException() {
        assertThrows(OverflowException.class, () -> {
            taskList.get(-1);
        });
    }

    @Test
    public void delete_validIndex_removesTask() throws OverflowException {
        taskList.add(new Todo("task 1"));
        taskList.add(new Todo("task 2"));
        
        Task deleted = taskList.delete(0);
        
        assertEquals(1, taskList.size());
        assertEquals("task 1", deleted.getName());
    }

    @Test
    public void delete_invalidIndex_throwsException() {
        taskList.add(new Todo("test task"));
        
        assertThrows(OverflowException.class, () -> {
            taskList.delete(5);
        });
    }

    @Test
    public void mark_validIndex_marksTask() throws OverflowException {
        taskList.add(new Todo("test task"));
        taskList.mark(0);
        
        assertTrue(taskList.get(0).toString().contains("X"));
    }

    @Test
    public void mark_invalidIndex_throwsException() {
        assertThrows(OverflowException.class, () -> {
            taskList.mark(5);
        });
    }

    @Test
    public void unmark_validIndex_unmarksTask() throws OverflowException {
        Task task = new Todo("test task");
        task.mark();
        taskList.add(task);
        
        taskList.unmark(0);
        
        assertTrue(taskList.get(0).toString().contains("[ ]"));
    }

    @Test
    public void unmark_invalidIndex_throwsException() {
        assertThrows(OverflowException.class, () -> {
            taskList.unmark(5);
        });
    }

    @Test
    public void find_matchingKeyword_returnsMatchingTasks() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("return book"));
        taskList.add(new Todo("buy groceries"));
        
        HashMap<String, ArrayList<Task>> results = taskList.find(new String[]{"book"});
        
        assertTrue(results.containsKey("book"));
        assertEquals(2, results.get("book").size());
    }

    @Test
    public void find_multipleKeywords_groupsByKeyword() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("attend meeting"));
        taskList.add(new Todo("book meeting room"));
        
        HashMap<String, ArrayList<Task>> results = taskList.find(new String[]{"book", "meeting"});
        
        assertTrue(results.containsKey("book"));
        assertTrue(results.containsKey("meeting"));
        assertEquals(2, results.get("book").size());
        assertEquals(2, results.get("meeting").size());
    }

    @Test
    public void find_noMatch_returnsEmptyMap() {
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("buy groceries"));
        
        HashMap<String, ArrayList<Task>> results = taskList.find(new String[]{"meeting"});
        
        assertTrue(results.isEmpty());
    }

    @Test
    public void find_caseInsensitive_findsMatches() {
        taskList.add(new Todo("Read Book"));
        taskList.add(new Todo("RETURN BOOK"));
        
        HashMap<String, ArrayList<Task>> results = taskList.find(new String[]{"book"});
        
        assertEquals(2, results.get("book").size());
    }

    @Test
    public void getTasks_returnsInternalList() {
        Task task1 = new Todo("task 1");
        Task task2 = new Todo("task 2");
        taskList.add(task1);
        taskList.add(task2);
        
        ArrayList<Task> tasks = taskList.getTasks();
        
        assertEquals(2, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
    }
}
