import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int year = scanner.nextInt();
        int[] days = new int[3];
        for (int i = 0; i < 3; i++) {
            days[i] = scanner.nextInt();
        }
        scanner.close();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int day : days) {
            LocalDate date = LocalDate.ofYearDay(year, day);
            System.out.println(date.format(formatter));
        }
    }
}