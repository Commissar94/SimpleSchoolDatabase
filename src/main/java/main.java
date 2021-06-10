import java.sql.*;
import java.util.Scanner;

public class main {

    public static String url = "jdbc:mysql://localhost:3306/school";
    public static String user = "root";
    public static String password = "1234";
    public static Scanner sc = new Scanner(System.in);
    public static String createTeachersTableQuery = "create table Teachers\n" +
            "(\n" +
            "\tid int auto_increment,\n" +
            "\tName char(30) not null,\n" +
            "\tSpecialization char(30) not null,\n" +
            "\tClass char(5) null, \n" +
            "\tconstraint Teachers_pk\n" +
            "\t\tprimary key (id)\n" +
            ");";
    public static String createPupilsTableQuery = "create table Pupils\n" +
            "(\n" +
            "\tId int auto_increment,\n" +
            "\tName char(30) null,\n" +
            "\tClass char(4) null,\n" +
            "\tconstraint Pupils_pk\n" +
            "\t\tprimary key (Id)\n" +
            ");\n";
    public static String insertNewPupilQuery = "INSERT INTO school.pupils (Name, Class) \n" +
            "VALUES (?,?);\n";
    public static String insertNewTeacherQuery = "INSERT INTO school.teachers (Name, Specialization, Class) \n" +
            "VALUES (?,?,?);\n";
    public static String showPupils = "SELECT Name,Class From pupils";
    public static String showTeachers = "SELECT Name,Specialization,Class From teachers";

    public static void main(String[] args) {

        ShowMenu();
        int menuChoice = sc.nextInt();
        sc.nextLine(); //кушаем линию
        MenuHandler(menuChoice);


    }

    public static void CreateTeachersTable() {

        TableEditor forTeachers = new TableEditor();
        System.out.println(forTeachers.newTable(url, user, password, createTeachersTableQuery));
    }

    public static void CreatePupilsTable() {

        TableEditor forPupils = new TableEditor();
        System.out.println(forPupils.newTable(url, user, password, createPupilsTableQuery));
    }

    public static void CreatePupilInDb(Pupil pupil) {

        TableEditor te = new TableEditor();
        System.out.println(te.newLine(url, user, password, insertNewPupilQuery, pupil));
    }

    public static void CreateTeacherInDb(Teacher teacher) {

        TableEditor te = new TableEditor();
        System.out.println(te.newLine(url, user, password, insertNewTeacherQuery, teacher));
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
                System.out.println("Enter teacher's name, class and specialization");
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

        TableEditor te = new TableEditor();
        te.showTheTable(url, user, password, showTeachers);
    }

    public static void ShowPupils() {

        TableEditor te = new TableEditor();
        te.showTheTable(url, user, password, showPupils);
    }
}

abstract class Human {
    String name;
    String schoolClass;
}

class Teacher extends Human {

    String specialization;

    Teacher(String name, String teacherClass, String specialization) {
        this.name = name;
        this.schoolClass = teacherClass;
        this.specialization = specialization;
    }
}

class Pupil extends Human {

    Pupil(String name, String pupilClass) {
        this.name = name;
        this.schoolClass = pupilClass;
    }
}

interface createTable {

    default String newTable(String url, String user, String password, String sqlQuery) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            Statement statement = connect.createStatement();
            statement.execute(sqlQuery);
            return "table has been created";
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}

interface createLineInDB {
    default String newLine(String url, String user, String password, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            Statement statement = connect.createStatement();
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, human.name);
            preparedStatement.setString(2, human.schoolClass);
            if (human instanceof Teacher) {
                preparedStatement.setString(3, ((Teacher) human).specialization);
            }

            preparedStatement.execute();
            return "New line in DB has been created";
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}

interface showTable {

    default void showTheTable(String url, String user, String password, String sqlQuery) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            Statement statement = connect.createStatement();
            statement.executeQuery(sqlQuery);
            ResultSet resultset = statement.executeQuery(sqlQuery);
            int columns = resultset.getMetaData().getColumnCount();
            while (resultset.next()) {
                for (int i = 1; i < columns + 1; i++) {
                    System.out.print(resultset.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

class TableEditor implements createTable, createLineInDB, showTable {
    @Override
    public String newTable(String url, String user, String password, String sqlQuery) {
        return createTable.super.newTable(url, user, password, sqlQuery);
    }

    @Override
    public String newLine(String url, String user, String password, String sqlQuery, Human human) {
        return createLineInDB.super.newLine(url, user, password, sqlQuery, human);
    }

    @Override
    public void showTheTable(String url, String user, String password, String sqlQuery) {
        showTable.super.showTheTable(url, user, password, sqlQuery);
    }
}
