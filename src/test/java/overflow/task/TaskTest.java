package overflow.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests for Task classes (Task, Todo, Deadline, Event).
 */
public class TaskTest {

    @Test
    public void task_newTask_isNotDone() {
        Task task = new Task("test task");
        assertFalse(task.toString().contains("X"));
    }

    @Test
    public void task_mark_becomesMarked() {
        Task task = new Task("test task");
        task.mark();
        assertTrue(task.toString().contains("X"));
    }

    @Test
    public void task_unmark_becomesUnmarked() {
        Task task = new Task("test task");
        task.mark();
        task.unmark();
        assertFalse(task.toString().contains("X"));
    }

    @Test
    public void task_getName_returnsCorrectName() {
        Task task = new Task("buy groceries");
        assertEquals("buy groceries", task.getName());
    }

    @Test
    public void task_toString_correctFormat() {
        Task task = new Task("test task");
        assertEquals("[ ] test task", task.toString());
        
        task.mark();
        assertEquals("[X] test task", task.toString());
    }

    @Test
    public void todo_toString_hasCorrectFormat() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void todo_toFileFormat_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("T | 0 | read book", todo.toFileFormat());
        
        todo.mark();
        assertEquals("T | 1 | read book", todo.toFileFormat());
    }

    @Test
    public void deadline_toString_hasCorrectFormat() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline task = new Deadline("return book", deadline);
        assertTrue(task.toString().contains("[D][ ] return book"));
        assertTrue(task.toString().contains("Dec 25 2024"));
    }

    @Test
    public void deadline_toFileFormat_correctFormat() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline task = new Deadline("return book", deadline);
        assertEquals("D | 0 | return book | 2024-12-25 1800", task.toFileFormat());
    }

    @Test
    public void deadline_mark_updatesFileFormat() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline task = new Deadline("return book", deadline);
        task.mark();
        assertEquals("D | 1 | return book | 2024-12-25 1800", task.toFileFormat());
    }

    @Test
    public void deadline_getDeadline_returnsCorrectDeadline() {
        LocalDateTime deadline = LocalDateTime.of(2024, 12, 25, 18, 0);
        Deadline task = new Deadline("return book", deadline);
        assertEquals(deadline, task.getDeadline());
    }

    @Test
    public void event_toString_hasCorrectFormat() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 20, 14, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 20, 16, 0);
        Event event = new Event("meeting", start, end);
        
        assertTrue(event.toString().contains("[E][ ] meeting"));
        assertTrue(event.toString().contains("Dec 20 2024"));
    }

    @Test
    public void event_toFileFormat_correctFormat() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 20, 14, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 20, 16, 0);
        Event event = new Event("meeting", start, end);
        
        assertEquals("E | 0 | meeting | 2024-12-20 1400 | 2024-12-20 1600", event.toFileFormat());
    }

    @Test
    public void event_mark_updatesFileFormat() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 20, 14, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 20, 16, 0);
        Event event = new Event("meeting", start, end);
        event.mark();
        
        assertEquals("E | 1 | meeting | 2024-12-20 1400 | 2024-12-20 1600", event.toFileFormat());
    }

    @Test
    public void event_getTimes_returnsCorrectTimes() {
        LocalDateTime start = LocalDateTime.of(2024, 12, 20, 14, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 20, 16, 0);
        Event event = new Event("meeting", start, end);
        
        assertEquals(start, event.getStartTime());
        assertEquals(end, event.getEndTime());
    }
}

