package gautam.simpletodo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDo implements Comparable<ToDo> {
    private static Integer toDoCount = 0;
    Integer id;
    private Integer todoID;
    String title;
    String description;
    Date dueDate;
    Integer size; // In hours
    Priority priority; // 1 - 3 (Low, Medium, High)
    Boolean addedToDatabase = false;

    public ToDo() {
        toDoCount++;
        todoID = toDoCount;
    }

    public ToDo(String title, String description, Date dueDate, Integer size, Integer priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.size = size;
        this.priority = Priority.determinePriority(priority.intValue());
        toDoCount++;
        todoID = toDoCount;
    }

    @Override
    public int compareTo(ToDo another) {
        Integer comparisonResult = dueDate.compareTo(another.dueDate);
        if (comparisonResult == 0) {
            comparisonResult = priority.compareTo(another.priority);
        }

        return comparisonResult;
    }

    public Integer getTodoID() {
        return todoID;
    }

    public void setTodoID(int id) {
        todoID = id;
    }
}
