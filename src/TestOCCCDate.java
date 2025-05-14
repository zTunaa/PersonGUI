/*
import java.util.Scanner;

public class TestOCCCDate {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Enter today's date and create an OCCCDate
        System.out.print("Enter today's date (MM DD YYYY): ");
        int month = scanner.nextInt();
        int day = scanner.nextInt();
        int year = scanner.nextInt();
        OCCCDate today = new OCCCDate(day, month, year);
        today.setDateFormat(OCCCDate.FORMAT_US);
        today.setStyleFormat(OCCCDate.STYLE_NUMBERS);
        System.out.println("Today's Date (US Format, Numeric): " + today);
        System.out.println("Day of the Week: " + today.getDayName());

        // Create and display an OCCCDate from February 29, 2022
        System.out.print("Enter leap year date: (MM DD YYYY): ");
        month = scanner.nextInt();
        day = scanner.nextInt();
        year = scanner.nextInt();

        OCCCDate leapDate = new OCCCDate(day, month, year);
        leapDate.setDateFormat(OCCCDate.FORMAT_EURO);
        leapDate.setStyleFormat(OCCCDate.STYLE_NUMBERS);
        System.out.println("\nLeap Year Test (EURO Format, Numeric): " + leapDate);
        System.out.println("Day of the Week: " + leapDate.getDayName());

        // Create and display an OCCCDate from January 365, 2022
        System.out.print("Enter 365 day date: (MM DD YYYY): ");
        month = scanner.nextInt();
        day = scanner.nextInt();
        year = scanner.nextInt();

        OCCCDate endOfYear = new OCCCDate(day, month, year);
        endOfYear.setDateFormat(OCCCDate.FORMAT_US);
        endOfYear.setStyleFormat(OCCCDate.STYLE_NAMES);
        System.out.println("\nEnd of Year Test (US Format, Names): " + endOfYear);
        System.out.println("Day of the Week: " + endOfYear.getDayName());

        // Create and display James T. Kirk's birthdate (March 22, 2233)
        System.out.print("Enter James T. Kirk's birthdate date: (MM DD YYYY): ");
        month = scanner.nextInt();
        day = scanner.nextInt();
        year = scanner.nextInt();

        OCCCDate kirkBirthDate = new OCCCDate(day, month, year);
        kirkBirthDate.setDateFormat(OCCCDate.FORMAT_EURO);
        kirkBirthDate.setStyleFormat(OCCCDate.STYLE_NAMES);
        System.out.println("\nJames T. Kirk's Birthdate (EURO Format, Names): " + kirkBirthDate);
        System.out.println("Day of the Week: " + kirkBirthDate.getDayName());

        scanner.close();
    }
}
*/
