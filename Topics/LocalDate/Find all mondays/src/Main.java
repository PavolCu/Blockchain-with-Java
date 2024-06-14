import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int month = scanner.nextInt();

        LocalDate date = LocalDate.of(year, month, 1);
        if (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        while (date.getMonthValue() == month) {
            System.out.println(date);
            date = date.plusWeeks(1);
        }
    }
}