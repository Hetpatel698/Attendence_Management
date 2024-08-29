import java.time.LocalDate;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
         System.out.print("Enter Birth Year : ");
        Scanner sc = new Scanner(System.in);
        int year = sc.nextInt();
        System.out.print("Enter Birth Month : ");
        int month = sc.nextInt();
        System.out.print("Enter Date : ");
        int date = sc.nextInt();
        sc.nextLine();
        if((LocalDate.of(year, month, date)).isBefore(LocalDate.now().withYear(2010))) {
            System.out.println("HELLO");
        }
    }
}
