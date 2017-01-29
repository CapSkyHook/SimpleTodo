package gautam.simpletodo;

/**
 * Created by Gautam on 1/28/17.
 */

public enum Months {
    JANUARY  (1),
    FEBRUARY  (2),
    MARCH  (3),
    APRIL (4),
    MAY (5),
    JUNE (6),
    JULY (7),
    AUGUST (8),
    SEPTEMBER (9),
    OCTOBER (10),
    NOVEMBER (11),
    DECEMBER (12);

    private int monthNumber;

    public int getMonthNumber() {
        return monthNumber;
    }

    public static Months determineMonth(String value) {
        switch (value) {
            case "January":
                return JANUARY;
            case "February":
                return FEBRUARY;
            case "March":
                return MARCH;
            case "April":
                return APRIL;
            case "May":
                return MAY;
            case "June":
                return JUNE;
            case "July":
                return JULY;
            case "August":
                return AUGUST;
            case "September":
                return SEPTEMBER;
            case "October":
                return OCTOBER;
            case "November":
                return NOVEMBER;
            case "December":
                return DECEMBER;
            default:
                return JANUARY;
        }
    }

    Months(int monthNumber) {
        this.monthNumber = monthNumber;
    }


}
