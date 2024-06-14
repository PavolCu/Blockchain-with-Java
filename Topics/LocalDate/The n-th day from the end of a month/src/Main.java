import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int month = scanner.nextInt();
        int remainingDays = scanner.nextInt();
        scanner.close();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.of(year, month, 1).plusMonths(1).minusDays(remainingDays);

        System.out.println(date.format(formatter));
    }
}