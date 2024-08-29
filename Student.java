import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;

public class Student {
    String name;
    int id;
    Date Birth_date;
    String Phone_number;
    String Mail_id;
    Scanner sc = new Scanner(System.in);

    void add_student() {
        System.out.print("Enter Id : ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name : ");
        name = sc.nextLine();
        Birth_date = Birth_date();
        sc.nextLine();
        Phone_number = phone_number();
        Mail_id = Mail_id();
    }

    private Date Birth_date() {
        System.out.print("Enter Birth Year : ");
        int year = sc.nextInt();
        System.out.print("Enter Birth Month : ");
        int month = sc.nextInt();
        System.out.print("Enter Date : ");
        int date = sc.nextInt();
        sc.nextLine();
        LocalDate date1 = LocalDate.of(year, month, date);
        while (!date1.isBefore(LocalDate.now())) {
            System.out.println("ENTER LESS THEN " + LocalDate.now());
            System.out.print("Enter Birth Year : ");
            year = sc.nextInt();
            System.out.print("Enter Birth Month : ");
            month = sc.nextInt();
            System.out.print("Enter Date : ");
            date = sc.nextInt();
            date1 = LocalDate.of(year, month, date);
        }
        return java.sql.Date.valueOf(LocalDate.of(year, month, date));
    }

    private String phone_number() {
        System.out.print("Enter Mobile Number :");
        String phone = sc.nextLine();
        int i = 0;
        int r = 0;
        while (i == 0) {
            if (phone.length() != 10) {
                System.out.print("ENTER VALID NUMBER : ");
                phone = sc.nextLine();
                continue;
            } else {
                for (int j = 0; j < phone.length(); j++) {
                    if (phone.charAt(j) <= '9') {
                        r++;
                    }
                }
                if (r == 10) {
                    i = 1;
                } else {
                    System.out.print("ENTER VALID NUMBER : ");
                    phone = sc.nextLine();
                    continue;
                }
            }
        }
        return phone;
    }

    private String Mail_id() {
        String name = this.name;
        String[] name_array = name.split(" ");
        name = "";
        for (int i = 0; i < name_array.length; i++) {
            name = name + name_array[i];
        }
        String Mail_id = name.toLowerCase() + (int) (Math.random() * 10000) + "@attendancemanagementsystem.in";
        return Mail_id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Date getBirth_date() {
        return Birth_date;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public String getMail_id() {
        return Mail_id;
    }

}