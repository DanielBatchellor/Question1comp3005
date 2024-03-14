import java.sql.*;
import java.util.Scanner;

public class main {
    //the required variables for connection, might have to change it to work on other machines
    static String url = "jdbc:postgresql://localhost:5432/studentData";
    static String user = "postgres";
    static String password = ""; //left blank put in own password
    static Connection conn;

    public static void main(String[] args){
        //keep going until the user wants to quit
        while(true) {
            try {
                Class.forName("org.postgresql.Driver");

                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Write either: READ, CREATE, UPDATE, or DELETE to perform the operations. Anything else to exit");
                String choice;
                Scanner scan = new Scanner(System.in);
                choice = scan.nextLine();

                switch (choice) {
                    case "READ" -> getAllStudents();
                    case "CREATE" -> {
                        addStudent("name", "last", "namelast@email.com", "2023-09-01");
                        break;
                    }
                    case "UPDATE" -> {
                        int student_id = scan.nextInt();
                        updateStudentEmail(student_id, "namefirst@email.com");
                        break;
                    }
                    case "DELETE" -> {
                        int student_id = scan.nextInt();
                        deleteStudent(student_id);
                        break;
                    }
                    default -> System.exit(0);
                }
            }catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
        }
    }

    private static void getAllStudents() throws SQLException {
        Statement statement = conn.createStatement();
        statement.executeQuery("SELECT * FROM students");
        ResultSet resultSet = statement.getResultSet();
        while(resultSet.next()){
            System.out.println(resultSet.getInt("student_id")+"\t"
                    +resultSet.getString("first_name")+"\t"+resultSet.getString("last_name")+"\t"
                    +resultSet.getString("email")+"\t"+resultSet.getDate("enrollment_date"));
        }
    }

    private static void addStudent(String first_name, String last_name, String email, String enrollment_date) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeQuery("INSERT INTO students(first_name, last_name, email, enrollment_date) VALUES" +
                "('"+first_name+"', '"+last_name+"', '"+email+"', '"+enrollment_date+"')");

    }

    private static void updateStudentEmail(int student_id, String email) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeQuery("UPDATE students SET email = '"+email+"' WHERE student_id = "+student_id);

    }

    private static void deleteStudent(int student_id) throws SQLException {

        Statement statement = conn.createStatement();
        statement.executeQuery("DELETE FROM students WHERE student_id = "+student_id);

    }
}