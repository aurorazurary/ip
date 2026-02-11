package overflow.parser;

import overflow.exception.OverflowException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Parser class.
 */
public class ParserTest {

    @Test
    public void parseCommand_validCommands_returnsCorrectCommand() {
        assertEquals("list", Parser.parseCommand("list"));
        assertEquals("todo", Parser.parseCommand("todo read book"));
        assertEquals("deadline", Parser.parseCommand("deadline return book /by 2024-12-25 1800"));
        assertEquals("event", Parser.parseCommand("event meeting /from 2024-12-20 1400 /to 2024-12-20 1600"));
        assertEquals("mark", Parser.parseCommand("mark 1"));
        assertEquals("unmark", Parser.parseCommand("unmark 2"));
        assertEquals("delete", Parser.parseCommand("delete 3"));
        assertEquals("find", Parser.parseCommand("find book"));
    }

    @Test
    public void parseCommand_unknownCommand_returnsUnknown() {
        assertEquals("unknown", Parser.parseCommand("invalid"));
        assertEquals("unknown", Parser.parseCommand("xyz abc"));
    }

    @Test
    public void parseTodo_validInput_returnsDescription() throws OverflowException {
        assertEquals("read book", Parser.parseTodo("todo read book"));
        assertEquals("buy groceries", Parser.parseTodo("todo buy groceries"));
    }

    @Test
    public void parseTodo_emptyDescription_throwsException() {
        OverflowException exception = assertThrows(OverflowException.class, () -> {
            Parser.parseTodo("todo ");
        });
        assertEquals("OOPS!!! The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseDeadline_validInput_returnsDescriptionAndDateTime() throws OverflowException {
        Object[] result = Parser.parseDeadline("deadline return book /by 2024-12-25 1800");
        assertEquals("return book", result[0]);
        assertTrue(result[1] instanceof LocalDateTime);
    }

    @Test
    public void parseDeadline_emptyDescription_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseDeadline("deadline /by 2024-12-25 1800");
        });
    }

    @Test
    public void parseDeadline_missingByClause_throwsException() {
        OverflowException exception = assertThrows(OverflowException.class, () -> {
            Parser.parseDeadline("deadline return book");
        });
        assertEquals("OOPS!!! The deadline must have a /by time.", exception.getMessage());
    }

    @Test
    public void parseEvent_validInput_returnsDescriptionAndTimes() throws OverflowException {
        Object[] result = Parser.parseEvent("event meeting /from 2024-12-20 1400 /to 2024-12-20 1600");
        assertEquals("meeting", result[0]);
        assertTrue(result[1] instanceof LocalDateTime);
        assertTrue(result[2] instanceof LocalDateTime);
    }

    @Test
    public void parseEvent_emptyDescription_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseEvent("event /from 2024-12-20 1400 /to 2024-12-20 1600");
        });
    }

    @Test
    public void parseEvent_missingFromClause_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseEvent("event meeting /to 2024-12-20 1600");
        });
    }

    @Test
    public void parseEvent_missingToClause_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseEvent("event meeting /from 2024-12-20 1400");
        });
    }

    @Test
    public void parseIndex_validInput_returnsIndex() throws OverflowException {
        assertEquals(1, Parser.parseIndex("mark 1", 4));
        assertEquals(5, Parser.parseIndex("delete 5", 6));
        assertEquals(10, Parser.parseIndex("unmark 10", 6));
    }

    @Test
    public void parseIndex_emptyIndex_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseIndex("mark ", 4);
        });
    }

    @Test
    public void parseIndex_invalidIndex_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseIndex("mark abc", 4);
        });
    }

    @Test
    public void parseKeyword_validInput_returnsKeywords() throws OverflowException {
        assertArrayEquals(new String[]{"book"}, Parser.parseKeyword("find book", 4));
        assertArrayEquals(new String[]{"book", "meeting"}, Parser.parseKeyword("find book meeting", 4));
    }

    @Test
    public void parseKeyword_emptyKeywords_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseKeyword("find ", 4);
        });
    }

    @Test
    public void parseDateTime_now_returnsCurrentDateTime() throws OverflowException {
        LocalDateTime result = Parser.parseDateTime("now");
        LocalDateTime current = LocalDateTime.now();
        
        // Check if the result is within 1 second of current time
        assertTrue(result.isAfter(current.minusSeconds(1)));
        assertTrue(result.isBefore(current.plusSeconds(1)));
    }

    @Test
    public void parseDateTime_today_returnsTodayMidnight() throws OverflowException {
        LocalDateTime result = Parser.parseDateTime("today");
        LocalDateTime expected = LocalDateTime.now().toLocalDate().atStartOfDay();
        assertEquals(expected, result);
    }

    @Test
    public void parseDateTime_timeOnly_returnsTodayWithTime() throws OverflowException {
        LocalDateTime result = Parser.parseDateTime("1430");
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    public void parseDateTime_fullDateTime_returnsCorrectDateTime() throws OverflowException {
        LocalDateTime result = Parser.parseDateTime("2024-12-25 1800");
        assertEquals(2024, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(18, result.getHour());
        assertEquals(0, result.getMinute());
    }

    @Test
    public void parseDateTime_invalidFormat_throwsException() {
        assertThrows(OverflowException.class, () -> {
            Parser.parseDateTime("invalid");
        });
    }
}
