import java.sql.*;;

public class query {
    /* students table quary */ 
    static String insert_students = "INSERT INTO Students VALUES (?, ? , ? , ? , ?)";
    static String select_students = "SELECT ID FROM STUDENTS WHERE ID = ? ";
    static String DELETE_STUDENT = "DELETE FROM students WHERE ID = ?";
    static String select_students_report = "SELECT * FROM STUDENTS ";
    /* ATTENDANCE TABLE QUARY */
    static String insert_attendance = "INSERT INTO attendance VALUES (? , ? , ?)";
    static String update_attendance = "UPDATE attendance SET LECTURE = (LECTURE + 1 ) WHERE ID = ? AND DATE = ? ";
    static String update_attendance_DELETE = "UPDATE attendance SET LECTURE = (LECTURE-1) WHERE ID = ? AND DATE = ? ";
    static String update_Attendance_LECTURE = "UPDATE attendance SET id = ? WHERE id = ?";
    static String DELETE_Attendance_byDate = "DELETE FROM attendance WHERE id = ? AND DATE = ? ";
    static String DELETE_Attendance = "DELETE FROM attendance WHERE id = ?";
    static String update_Attendance_id = "UPDATE attendance SET id = ? WHERE id = ?";
    /* attendance_percentage TABLE QUARY */
    static String insert_attendance_percentage = "INSERT INTO attendance_percentage VALUES (?, ?)";
    static String update_attendance_percentage = "UPDATE attendance_percentage SET percentage = ? WHERE ID = ?";
    static String delete_attendance_percentage = "DELETE FROM attendance_percentage WHERE ID = ? ";
    /* JOIN QUARY */
    static String SELECT_all = "SELECT students.id , name , SUM(ATTENDANCE.LECTURE)  , percentage from students left JOIN attendance  ON students.id = attendance.id LEFT JOIN attendance_percentage ON STUDENTS.ID = attendance_percentage.ID  WHERE STUDENTS.ID = ? ";
    static String select_date = "SELECT students.id , name ,ATTENDANCE.LECTURE , percentage from students left JOIN attendance  ON students.id = attendance.id LEFT JOIN attendance_percentage ON STUDENTS.ID = attendance_percentage.ID WHERE attendance.DATE = ? ";
    static Connection con;

    query() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceDB", "root", "");
    }
}
