import java.io.*;
import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
public class Attendance {
    Connection con = query.con;
    PreparedStatement pst;
    int r;
    Scanner sc = new Scanner(System.in);

    public void addStudent(Student s) throws Exception {
        pst = query.con.prepareStatement(query.insert_students);
        pst.setInt(1, s.getId());
        pst.setString(2, s.getName());
        pst.setDate(3, s.getBirth_date());
        pst.setString(4, s.getPhone_number());
        pst.setString(5, s.getMail_id());
        r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("ADDED SUCSSESFULL\n");
        } else {
            System.out.println("ADDED FAILED\n");
        }
    }
    
    public void persantage(int id) throws SQLException {
        int count_date = 0;
        PreparedStatement set_persantage;
        PreparedStatement Group_by = con.prepareStatement("SELECT Date FROM ATTENDANCE GROUP BY DATE");
        ResultSet iidd = Group_by.executeQuery();
        while (iidd.next()) {
            count_date++;
        }
        if (count_date == 0) {
            set_persantage = con.prepareStatement(query.update_attendance_percentage);
            set_persantage.setInt(2, id);
            set_persantage.setDouble(1, 0);
            set_persantage.executeUpdate();
        } else if (count_date > 0) {
            double total_LECTURE = (count_date * 4);
            PreparedStatement total_attendance = con
                    .prepareStatement("SELECT SUM(LECTURE) FROM ATTENDANCE WHERE ID = ? ");
            total_attendance.setInt(1, id);
            ResultSet id1 = total_attendance.executeQuery();
            id1.next();
            double total_attend = id1.getInt(1);
            double persantage = ((total_attend * 100) / total_LECTURE);
            PreparedStatement percantage_id = con
                    .prepareStatement("SELECT * FROM attendance_percentage WHERE ID = ? ");
            percantage_id.setInt(1, id);
            ResultSet rs = percantage_id.executeQuery();
            if (rs.next()) {
                set_persantage = con.prepareStatement(query.update_attendance_percentage);
                set_persantage.setInt(2, id);
                set_persantage.setDouble(1, persantage);
                set_persantage.executeUpdate();
            } else if (persantage != 0) {
                set_persantage = con.prepareStatement(query.insert_attendance_percentage);
                set_persantage.setInt(1, id);
                set_persantage.setDouble(2, persantage);
                set_persantage.executeUpdate();
            }
        }
    }

    public void markAttendance(int Id, Date date) throws Exception {
        PreparedStatement pst = con.prepareStatement(query.select_students);
        pst.setInt(1, Id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pst = con.prepareStatement(query.update_attendance);
            pst.setInt(1, Id);
            pst.setDate(2, date);
            r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("MARKED ATTENDANCE SUCSSESFULL\n");
            } else {
                pst = con.prepareStatement(query.insert_attendance);
                pst.setInt(1, Id);
                pst.setInt(2, 1);
                pst.setDate(3, date);
                r = pst.executeUpdate();
                if (r > 0) {
                    System.out.println("MARKED ATTENDANCE SUCSSESFULL\n");
                }
            }
        } else {
            System.out.println("NOT FOUND THIS ID ");
            return;
        }
        persantage_id();
    }

    public void persantage_id() throws Exception {
        pst = con.prepareStatement("SELECT ID FROM STUDENTS");
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            persantage(rs.getInt(1));
        }
    }

    public void DELETEattendance(int id, Date date) throws Exception {
        pst = con.prepareStatement("SELECT LECTURE FROM ATTENDANCE WHERE ID = ? ");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (!rs.next()) {
            return;
        }
        if (rs.getInt(1) == 1) {
            PreparedStatement delete = con.prepareStatement(query.DELETE_Attendance_byDate);
            delete.setInt(1, id);
            delete.setDate(2, date);
            r = delete.executeUpdate();
            if (r > 0) {
                System.out.println("DELETE ATTENDANCE SUCSSESFULL\n");
            } else {
                System.out.println("NOT FOUND");
            }
        } else {
            pst = query.con.prepareStatement(query.update_attendance_DELETE);
            pst.setInt(1, id);
            pst.setDate(2, date);
            r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("DELETE ATTENDANCE SUCSSESFULL\n");
            } else {
                System.out.println("NOT FOUND");
            }
        }
        persantage(id);
    }

    private void DELETEattendance(int id) throws Exception {
        pst = query.con.prepareStatement(query.DELETE_Attendance);
        pst.setInt(1, id);
        r = pst.executeUpdate();
        pst = con.prepareStatement(query.delete_attendance_percentage);
        pst.setInt(1, id);
        r = pst.executeUpdate();
    }

    public void DELETESTUDENT(int id) throws Exception {
        DELETEattendance(id);
        PreparedStatement pp = con.prepareStatement("DELETE FROM  attendance_percentage WHERE id = ?");
        pp.setInt(1, id);
        pp.executeUpdate();
        pst = con.prepareStatement(query.DELETE_STUDENT);
        pst.setInt(1, id);
        r = pst.executeUpdate();
        if (r > 0) {
            System.out.println("UPDATE SUCSSESFULL\n");
        } else {
            System.out.println("NOT FOUND ID\n");
        }
    }

    public void updateAttendance(int id1, int id2) throws Exception {
        pst = con.prepareStatement(query.update_Attendance_id);
        pst.setInt(1, id2);
        pst.setInt(2, id1);
        r = pst.executeUpdate();
        if (r > 0) {
            pst = con.prepareStatement("UPDATE attendance_percentage SET id = ? WHERE id = ? and DATE = ? ");
            pst.setInt(1, id2);
            pst.setInt(2, id1);
            pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            r = pst.executeUpdate();
            System.out.println("UPDATE SUCSSESFULL\n");
        } else {
            System.out.println("NOT FOUND ID\n");
        }
    }
    public void updateAttendance_today(int id1 , int id2 , int lecture) throws Exception{
        pst = con.prepareStatement("UPDATE attendance SET LECTURE = ? WHERE ID = ? and Date = ? ");
        pst.setInt(1, ++lecture);
        pst.setInt(2, id2);
        pst.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
        pst.executeUpdate();
        DELETEattendance(id1, java.sql.Date.valueOf(LocalDate.now()));
        persantage(id2);
    }

    public void students_details() throws Exception {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query.select_students_report);
        while (rs.next()) {
            System.out.println("*****************************************");
            System.out.println("ID : " + rs.getInt(1));
            System.out.println("NAME : " + rs.getString(2));
            System.out.println("BIRTH DATE : " + rs.getDate(3));
            System.out.println("PHONE NUMBER : " + rs.getString(4));
            System.out.println("MAIL ID : " + rs.getString(5));
        }
        System.out.println("*****************************************");
    }

    public void generateReportByDate(Date date) throws Exception {
        PreparedStatement pst = con.prepareStatement(query.select_date);
        pst.setDate(1, (date));
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("==========================================");
            System.out.println("ID :- " + rs.getInt(1));
            System.out.println("Name :- " + rs.getString(2));
            System.out.println("TOTAL ATTEND LECTURE :- " + rs.getInt(3));
            System.out.println("PERCANTAGE OF ATTENDANCE :- " + rs.getDouble(4));
            System.out.println("==========================================");
            r = 1;
        }
        if (r != 1) {
            System.out.println("NOT FOUND ANY RECORD ");
        }
    }

    public void generateReport() throws Exception {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query.select_students_report);
        while (rs.next()) {
            generateReport(rs.getInt(1));
        }
    }

    public void generateReport(int id) throws Exception {
        PreparedStatement pst = con.prepareStatement(query.SELECT_all);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            System.out.println("==========================================");
            System.out.println("ID :- " + rs.getInt(1));
            System.out.println("Name :- " + rs.getString(2));
            System.out.println("TOTAL ATTEND LECTURE :- " + rs.getInt(3));
            System.out.println("PERCANTAGE OF ATTENDANCE :- " + rs.getDouble(4));
            System.out.println("==========================================");
            r = 1;
        }
        if (r != 1) {
            System.out.println("NOT FOUND ANY RECORD ");
        }
    }

    public void DOWNLOAD_report() throws Exception {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query.select_students_report);
        while (rs.next()) {
            DOWNLOAD_report(rs.getInt(1));
        }
    }

    public void DOWNLOAD_report(int id) throws Exception {
        int r = 0;

        System.out.print("ENTER FILE NAME : ");
        String File_name = sc.nextLine() + ".txt";
        File report = new File(File_name);
        while (!report.createNewFile()) {
            System.out.print(
                    "THERE IS ALREADY A FILE WITH SAME NAME IN THIS LOCATION \n ENTER NEW NAME : ");
            File_name = sc.nextLine() + ".txt";
            report = new File(File_name);
        }
        FileWriter writereport = new FileWriter(report);
        PreparedStatement pst = con.prepareStatement(query.SELECT_all);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            writereport.write("==========================================\n");
            writereport.write("ID :- " + rs.getInt(1));
            writereport.write("\nName :- " + rs.getString(2));
            writereport.write("\nTOTAL ATTEND LECTURE :- " + rs.getInt(3));
            writereport.write("\nPERCANTAGE OF ATTENDANCE :- " + rs.getDouble(4));
            writereport.write("\n==========================================\n \n");
            writereport.flush();
            r = 1;
        }
        writereport.close();
        if (r != 1) {
            System.out.println("NOT FOUND ANY RECORD ");
        } else {
            System.out.println("DOWNLOAD FILE AS " + File_name);
        }
    }
}