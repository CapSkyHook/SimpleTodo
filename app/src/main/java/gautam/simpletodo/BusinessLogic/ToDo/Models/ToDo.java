package gautam.simpletodo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static gautam.simpletodo.R.id.priority;

/**
 * Created by Gautam on 1/21/17.
 */

public class ToDo implements Comparable<ToDo> {
    private static Integer toDoCount = 0;
    Integer id;
    String title;
    String description;
    Date dueDate;
    Integer size; // In hours
    gautam.simpletodo.Priority priority; // 1 - 3 (Low, Medium, High)
    Boolean addedToDatabase = false;
    private Integer trackingID;

    public ToDo() {
        trackingID = ++toDoCount;
    }

    public ToDo(String title, String description, Date dueDate, Integer size, Integer priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.size = size;
        this.priority = gautam.simpletodo.Priority.determinePriority(priority.intValue());
    }

    @Override
    public int compareTo(ToDo another) {
        Integer comparisonResult = dueDate.compareTo(another.dueDate);
        if (comparisonResult == 0) {
            comparisonResult = priority.compareTo(another.priority);
        }

        return comparisonResult;
    }

    public Integer getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(Integer trackingID) {
        this.trackingID = trackingID;
    }
}
