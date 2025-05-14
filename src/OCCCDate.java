import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OCCCDate implements Serializable {
    private int dayOfMonth;
    private int monthOfYear;
    private int year;
    private GregorianCalendar gc;

    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;
    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;
    public static final boolean SHOW_DAY_NAME = true;
    public static final boolean HIDE_DAY_NAME = false;

    private boolean dateFormat = FORMAT_US;
    private boolean dateStyle = STYLE_NUMBERS;
    private boolean dateDayName = SHOW_DAY_NAME;

    // Default constructor (current date)
    public OCCCDate() {
        gc = new GregorianCalendar();
        this.year = gc.get(Calendar.YEAR);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1; // Months are 0-based
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    }

    // Constructor with day, month, year (adjusts invalid dates)
    public OCCCDate(int day, int month, int year) {
        this.gc = new GregorianCalendar(year, month - 1, day);
        this.year = gc.get(Calendar.YEAR);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1;
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    }

    // Constructor with dayOfYear (1-365) and year
    public OCCCDate(int dayOfYear, int year) {
        this.gc = new GregorianCalendar(year, Calendar.JANUARY, 1);
        this.gc.add(Calendar.DAY_OF_YEAR, dayOfYear - 1);
        this.year = gc.get(Calendar.YEAR);
        this.monthOfYear = gc.get(Calendar.MONTH) + 1;
        this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
    }

    // Copy constructor
    public OCCCDate(OCCCDate d) {
        this(d.dayOfMonth, d.monthOfYear, d.year);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public String getDayName() {
        String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        return days[gc.get(Calendar.DAY_OF_WEEK) - 1];
    }

    public int getMonthNumber() {
        return monthOfYear;
    }

    public String getMonthName() {
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return months[monthOfYear - 1];
    }

    public int getYear() {
        return year;
    }

    // Setters for formatting options
    public void setDateFormat(boolean df) {
        this.dateFormat = df;
    }

    public void setStyleFormat(boolean sf) {
        this.dateStyle = sf;
    }

    public void setDayName(boolean nf) {
        this.dateDayName = nf;
    }

    // Calculate age difference from today
    public int getDifferenceInYears() {
        GregorianCalendar now = new GregorianCalendar();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1;
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        int age = currentYear - this.year;
        if (currentMonth < this.monthOfYear || (currentMonth == this.monthOfYear && currentDay < this.dayOfMonth)) {
            age--; // Not yet reached birthday this year
        }
        return age;
    }

    public int getDifferenceInYears(OCCCDate d) {
        int diff = Math.abs(this.year - d.year);
        if (this.monthOfYear < d.monthOfYear || (this.monthOfYear == d.monthOfYear && this.dayOfMonth < d.dayOfMonth)) {
            diff--;
        }
        return diff;
    }

    // Equals method
    public boolean equals(OCCCDate dob) {
        return this.dayOfMonth == dob.dayOfMonth &&
                this.monthOfYear == dob.monthOfYear &&
                this.year == dob.year;
    }

    // String representation
    @Override
    public String toString() {
        String formattedDate;
        if (dateFormat == FORMAT_US) {
            formattedDate = dateStyle == STYLE_NUMBERS
                    ? String.format("%02d/%02d/%04d", monthOfYear, dayOfMonth, year)
                    : String.format("%s %02d, %04d", getMonthName(), dayOfMonth, year);
        } else {
            formattedDate = dateStyle == STYLE_NUMBERS
                    ? String.format("%02d/%02d/%04d", dayOfMonth, monthOfYear, year)
                    : String.format("%02d %s %04d", dayOfMonth, getMonthName(), year);
        }
        return dateDayName ? getDayName() + ", " + formattedDate : formattedDate;
    }
}
