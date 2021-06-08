import java.sql.*;
import java.util.Scanner;

public class main {

    public static String url = "jdbc:mysql://localhost:3306/school";
    public static String user = "root";
    public static String password = "1234";
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        ShowMenu();
        int menuChoice = sc.nextInt();
        sc.nextLine(); //кушаем линию
        MenuHandler(menuChoice);
    }

    public static void TestConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to" + url);
            String query = "Insert into test(test_column) values (1)";
            Statement statement = connect.createStatement();
            statement.execute(query);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CreateTeachersTable() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String createTeachersTableQuery = "create table Teachers\n" +
                    "(\n" +
                    "\tid int auto_increment,\n" +
                    "\tName char(30) not null,\n" +
                    "\tSpecialization char(30) not null,\n" +
                    "\tconstraint Teachers_pk\n" +
                    "\t\tprimary key (id)\n" +
                    ");";
            Statement statement = connect.createStatement();
            statement.execute(createTeachersTableQuery);
            System.out.println("Teachers table has been created");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void CreatePupilsTable() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String createTeachersTableQuery = "create table Pupils\n" +
                    "(\n" +
                    "\tId int auto_increment,\n" +
                    "\tName char(30) null,\n" +
                    "\tClass char(4) null,\n" +
                    "\tconstraint Pupils_pk\n" +
                    "\t\tprimary key (Id)\n" +
                    ");\n";
            Statement statement = connect.createStatement();
            statement.execute(createTeachersTableQuery);
            System.out.println("Pupils table has been created");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void CreatePupilInDb(Pupil pupil) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String insertNewPupil = "INSERT INTO school.pupils (Name, Class) \n" +
                    "VALUES (?,?);\n";
            Statement statement = connect.createStatement();
            PreparedStatement preparedStatement = connect.prepareStatement(insertNewPupil, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, pupil.name);
            preparedStatement.setString(2, pupil.pupilClass);
            preparedStatement.execute();
            System.out.printf("Pupil %s from %s has been created", pupil.name, pupil.pupilClass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void CreateTeacherInDb(Teacher teacher) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String insertNewTeacher = "INSERT INTO school.teachers (Name, Specialization, Class) \n" +
                    "VALUES (?,?,?);\n";
            Statement statement = connect.createStatement();
            PreparedStatement preparedStatement = connect.prepareStatement(insertNewTeacher, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teacher.name);
            preparedStatement.setString(2, teacher.specialization);
            preparedStatement.setString(3, teacher.teacherClass);
            preparedStatement.execute();
            System.out.printf("Teacher %s of %s for %s has been created", teacher.name, teacher.specialization, teacher.teacherClass);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void ShowMenu() {
        System.out.println(" Welcome to Simple School Database! \n" +
                "What you want to do? \n" +
                "1. Create new teacher\n" +
                "2. Create new pupil\n" +
                "3. Show all teachers\n" +
                "4. Show all pupils\n" +
                "5. Create teachers table\n" +
                "6. Create pupils table\n");
    }

    public static void MenuHandler(int choice) {

        switch (choice) {
            case 1 -> {
                System.out.println("Enter teacher's name, specialization and class");
                Teacher teacher = new Teacher(sc.nextLine(), sc.nextLine(), sc.nextLine());
                CreateTeacherInDb(teacher);
            }
            case 2 -> {
                System.out.println("Enter pupil's name and class");
                Pupil pupil = new Pupil(sc.nextLine(), sc.nextLine());
                CreatePupilInDb(pupil);
            }
            case 3 -> ShowTeachers();
            case 4 -> ShowPupils();
            case 5 -> CreateTeachersTable();
            case 6 -> CreatePupilsTable();
        }

    }

    public static void ShowTeachers() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String showTeachers = "SELECT Name,Specialization From teachers";
            Statement statement = connect.createStatement();
            statement.executeQuery(showTeachers);
            ResultSet resultset = statement.executeQuery(showTeachers);
            while (resultset.next()) {
                String Name = resultset.getString(1);
                String Specialization = resultset.getString(2);
                System.out.println(Name + " " + Specialization);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void ShowPupils() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            String showPupils = "SELECT Name,Class From pupils";
            Statement statement = connect.createStatement();
            statement.executeQuery(showPupils);
            ResultSet resultset = statement.executeQuery(showPupils);
            while (resultset.next()) {
                String Name = resultset.getString(1);
                String PupilClass = resultset.getString(2);
                System.out.println(Name + " " + PupilClass);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

class Pupil {

    String name;
    String pupilClass;

    Pupil(String _name, String _pupilClass) {
        name = _name;
        pupilClass = _pupilClass;
    }
}

class Teacher {

    String name;
    String specialization;
    String teacherClass;

    Teacher(String _name, String _specialization, String _teacherClass) {
        name = _name;
        specialization = _specialization;
        teacherClass = _teacherClass;
    }
}