package gautam.simpletodo;

/**
 * Created by Gautam on 1/28/17.
 */

public enum Priority {
    HIGH  (3),
    MEDIUM  (2),
    LOW  (1),
    NONE (0);

    private int priorityNumber;

    public int getPriorityNumber() {
        return priorityNumber;
    }

    public String toString() {
        switch (priorityNumber) {
            case 1:
                return "LOW";
            case 2:
                return "MEDIUM";
            case 3:
                return "HIGH";
            default:
                return "None";
        }
    }

    public static Priority determinePriority(int value) {
        switch (value) {
            case 1:
                return Priority.HIGH;
            case 2:
                return Priority.MEDIUM;
            case 3:
                return Priority.LOW;
            default:
                return Priority.NONE;
        }
    }

    public static Priority determinePriorityFromString(String value) {
        switch (value) {
            case "HIGH":
                return Priority.HIGH;
            case "MEDIUM":
                return Priority.MEDIUM;
            case "LOW":
                return Priority.LOW;
            default:
                return Priority.NONE;
        }
    }

    public static String[] getAllPriorityLevels() {
        return new String[] {
                Priority.HIGH.toString(), Priority.MEDIUM.toString(), Priority.LOW.toString()
        };
    }

    Priority(int priorityNumber) {
        this.priorityNumber = priorityNumber;
    }
}