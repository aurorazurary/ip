# Overflow User Guide

![Overflow GUI](Ui.png)

Overflow is a task management chatbot with a pixel-art themed GUI that helps you keep track of your todos, deadlines, and events. Never forget what you need to do again!

---

## Features

### Adding a Todo: `todo`

Adds a simple task without any date/time.

**Format:** `todo DESCRIPTION`

**Example:** `todo buy groceries`

**Expected output:**
```
Got it! I've added the task:
  [T][ ] buy groceries
Currently you have 1 tasks.
```

---

**Date/Time formats supported:**
- `now` - Current time
- `today` - The start of today
- `tonight` - Today 23:59
- `HHmm` - Today at specific time (e.g., `1400`)
- `MM-dd` - This year on specific date
- `MM-dd HHmm` - This year on specific date and time
- `yyyy-MM-dd` - Specific date
- `yyyy-MM-dd HHmm` - Specific date and time

---

### Adding a Deadline: `deadline`

Adds a task with a deadline.

**Format:** `deadline DESCRIPTION /by DATETIME`

**Example:** `deadline submit report /by 2026-03-15 2359`

**Expected output:**
```
Got it! I've added the task:
  [D][ ] submit report (by: Mar 15 2026, 11:59pm)
Currently you have 2 tasks.
```

---

### Adding an Event: `event`

Adds a task with start and end times.

**Format:** `event DESCRIPTION /from DATETIME /to DATETIME`

**Example:** `event team meeting /from 2026-03-10 1400 /to 2026-03-10 1600`

**Expected output:**
```
Got it! I've added the task:
  [E][ ] team meeting (from: Mar 10 2026, 2:00pm to: Mar 10 2026, 4:00pm)
Currently you have 3 tasks.
```

---

### Listing All Tasks: `list`

Shows all your current tasks.

**Format:** `list`

**Expected output:**
```
1. [T][ ] buy groceries
2. [D][ ] submit report (by: Mar 15 2026, 11:59pm)
3. [E][ ] team meeting (from: Mar 10 2026, 2:00pm to: Mar 10 2026, 4:00pm)
```

---

### Marking a Task as Done: `mark`

Marks a task as completed.

**Format:** `mark INDEX`

**Example:** `mark 1`

**Expected output:**
```
Marked!
[T][X] buy groceries
```

---

### Unmarking a Task: `unmark`

Marks a completed task as not done.

**Format:** `unmark INDEX`

**Example:** `unmark 1`

**Expected output:**
```
Unmarked!
[T][ ] buy groceries
```

---

### Deleting a Task: `delete`

Removes a task from your list.

**Format:** `delete INDEX`

**Example:** `delete 2`

**Expected output:**
```
Deleted!
[D][ ] submit report (by: Mar 15 2026, 11:59pm)
Currently you have 2 tasks.
```

---

### Finding Tasks: `find`

Searches for tasks containing specific keywords. Shows results grouped by keyword.

**Format:** `find KEYWORD [MORE_KEYWORDS...]`

**Example:** `find meeting report`

**Expected output:**
```
Tasks matching "meeting":
1. [E][ ] team meeting (from: Mar 10 2026, 2:00pm to: Mar 10 2026, 4:00pm)

Tasks matching "report":
1. [D][ ] submit report (by: Mar 15 2026, 11:59pm)
```

---

### Undoing Last Action: `undo`

Reverses the last action (add, delete, mark, or unmark).

**Format:** `undo`

**Expected output:**
```
Undone! Restored to previous state.
```

**Note:** Only the most recent action can be undone.

---

### Exiting the Program: `bye`

Closes the application after a 2-second farewell message.

**Format:** `bye`

**Expected output:**
```
Looking for the next time we meet!
```

*Window closes after 2 seconds*

---

## Data Storage

Your tasks are automatically saved to `./data/tasks.txt` after every change. The file is loaded automatically when you start Overflow, so your tasks persist between sessions.

---

## Command Summary

| Command | Format | Example |
|---------|--------|---------|
| Todo | `todo DESCRIPTION` | `todo read book` |
| Deadline | `deadline DESCRIPTION /by DATETIME` | `deadline assignment /by 2026-12-31 2359` |
| Event | `event DESCRIPTION /from DATETIME /to DATETIME` | `event concert /from today /to today` |
| List | `list` | `list` |
| Mark | `mark INDEX` | `mark 1` |
| Unmark | `unmark INDEX` | `unmark 1` |
| Delete | `delete INDEX` | `delete 2` |
| Find | `find KEYWORD...` | `find book meeting` |
| Undo | `undo` | `undo` |
| Exit | `bye` | `bye` |

## Possible issues
**Message stating directory creation isn't successful**
Solution: Manually create a `overflow_data` file in the same directory as the chatbot
**Others**
Contact me directly at
`Telegram` @aurorazurary
`Email` z.z@u.nus.edu