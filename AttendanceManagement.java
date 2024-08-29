import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class AttendanceManagement {
    public static void main(String[] args) throws Exception {
        System.out.println(
                "=============================== WELCOME TO ATTENDANCE MANAGEMANTE SYSTEM ===============================");
        Scanner sc = new Scanner(System.in);
        new query();
        linkList l = new linkList();
        Attendance attendance = new Attendance();
        Date date;
        int id = 0;
        int choice = 0;
        ResultSet rs;
        int year;
        int month;
        int DATE;
        while (choice != 10) {
            System.out.println("1. ADD STUDENT ");
            System.out.println("2. MARK ATTENDANCE " );
            System.out.println("3. GENERATE REPORT " );
            System.out.println("4. DATE WISE GANERATE REPORT ");
            System.out.println("5. DETAILS FOR STUDENTS ");
            System.out.println("6. UPDATE ATTENDANCE" );
            System.out.println("7. DELETE ATTENDANCE " );
            System.out.println("8. DELETE STUDENT" );
            System.out.println("9. DOWNLOAD REPORT " );
            System.out.println("10. EXIT" );
            System.out.print("ENTER YOUR CHOICE :- ");
            try {
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        Student s = new Student();
                        s.add_student();
                        attendance.addStudent(s);
                        l.Add(s);
                        break;
                    case 2:
                        System.out.print("Enter student ID: ");
                        int studentId = sc.nextInt();
                        sc.nextLine();
                        date = java.sql.Date.valueOf(LocalDate.now());
                        PreparedStatement pst = query.con
                                .prepareStatement("SELECT LECTURE FROM  attendance WHERE ID = ? AND DATE = ? ");
                        pst.setInt(1, studentId);
                        pst.setDate(2, date);
                        rs = pst.executeQuery();
                        if (rs.next()) {
                            if (rs.getInt(1) < 4) {
                                attendance.markAttendance(studentId, date);
                            } else {
                                System.out.println("ONE DAY ADD ONLY FOUR LECTURE");
                            }
                            break;
                        }
                        attendance.markAttendance(studentId, date);
                        break;
                    case 3:
                        attendance.generateReport();
                        break;
                    case 4:
                        System.out.print("ENTER YEAR WHICH YEAR :- ");
                        year = sc.nextInt();
                        System.out.print("Enter Month :- ");
                        month = sc.nextInt();
                        System.out.print("Enter Date :-");
                        DATE = sc.nextInt();
                        date = java.sql.Date.valueOf(LocalDate.of(year, month, DATE));
                        attendance.generateReportByDate(date);
                        break;
                    case 5:
                        attendance.students_details();
                        break;
                    case 6:
                        System.out.print("\nEnter Old Id :- ");
                        int id1 = sc.nextInt();
                        System.out.print("\nEnter new Id :- ");
                        int id2 = sc.nextInt();
                        pst = query.con.prepareStatement("SELECT * FROM attendance WHERE ID = ? AND DATE = ? ");
                        pst.setInt(1, id2);
                        pst.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                        rs = pst.executeQuery();

                        if (rs.next()) {
                            System.out.println(id2+"IS ALLREADY EXIST FOR TODAY");
                            attendance.updateAttendance_today(id1, id2 , rs.getInt("lecture"));
                        } else {
                            attendance.updateAttendance(id1, id2);
                        }
                        break;
                    case 7:
                        System.out.print("Enter Id :- ");
                        id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("ENTER YEAR WHICH YEAR :- ");
                        year = sc.nextInt();
                        System.out.print("Enter Month :- ");
                        month = sc.nextInt();
                        System.out.print("Enter Date :-");
                        DATE = sc.nextInt();
                        date = java.sql.Date.valueOf(LocalDate.of(year, month, DATE));
                        attendance.DELETEattendance(id, date);
                        break;
                    case 8:
                        System.out.print("Enter Id :- ");
                        id = sc.nextInt();
                        l.DELETE_STUDENT(id);
                        attendance.DELETESTUDENT(id);
                        break;
                    case 9:
                        attendance.DOWNLOAD_report();
                        break;
                    case 10:
                        System.out.println("\nExiting...");
                        System.out.println("=============================== THANKS FOR USING OUR SYSTEM =============================== \n"
                                );
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                System.out.println("\n");
                if (e.toString().equalsIgnoreCase(
                        "java.sql.SQLIntegrityConstraintViolationException: Duplicate entry '1' for key 'PRIMARY'")) {
                    System.out.println("NOT ADD , BEACUSE ID IS ALL READY EXITS ");
                } else if (e.toString().equalsIgnoreCase("java.util.InputMismatchException")) {
                    System.out.println(e.toString());
                    sc.nextLine();
                } else {
                    System.out.println(e.toString());
                }
                System.out.println("\n");
                continue;
            }
        }
        sc.close();
    }
}