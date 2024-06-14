import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputDate = scanner.nextLine();
        scanner.close();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(inputDate, formatter);

        LocalDate dateBefore = date.minusYears(30);
        LocalDate dateAfter = date.plusYears(30);

        System.out.println(dateBefore.format(formatter));
        System.out.println(dateAfter.format(formatter));
    }
}