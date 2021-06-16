import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static String url = "jdbc:mysql://localhost:3306/school";
    public static String user = "root";
    public static String password = "1234";
    public static Scanner sc = new Scanner(System.in);
    public static String createTeachersTableQuery = """
            create table Teachers
            (
            \tid int auto_increment,
            \tName char(30) not null,
            \tSpecialization char(30) not null,
            \tClass char(5) null,\s
            \tconstraint Teachers_pk
            \t\tprimary key (id)
            );""";
    public static String createPupilsTableQuery = """
            create table Pupils
            (
            \tId int auto_increment,
            \tName char(30) null,
            \tClass char(4) null,
            \tconstraint Pupils_pk
            \t\tprimary key (Id)
            );
            """;
    public static String insertNewPupilQuery = """
            INSERT INTO school.pupils (Name, Class)\s
            VALUES (?,?);
            """;
    public static String insertNewTeacherQuery = """
            INSERT INTO school.teachers (Name, Specialization, Class)\s
            VALUES (?,?,?);
            """;
    public static String showPupils = "SELECT Name,Class From pupils";
    public static String showTeachers = "SELECT Name,Specialization,Class From teachers";

    public static void main(String[] args) {

        ShowMenu();
        int menuChoice = sc.nextInt();
        sc.nextLine(); //кушаем линию
        MenuHandler(menuChoice);
    }

    public static void CreateTeachersTable() {

        Table.TableEditor forTeachers = new Table.TableEditor();
        System.out.println(forTeachers.newTable(url, user, password, createTeachersTableQuery));
    }

    public static void CreatePupilsTable() {

        Table.TableEditor forPupils = new Table.TableEditor();
        System.out.println(forPupils.newTable(url, user, password, createPupilsTableQuery));
    }

    public static void CreatePupilInDb(Human human) {

        Table.TableEditor te = new Table.TableEditor();
        System.out.println("New record of " + te.newLine(url, user, password, insertNewPupilQuery, human).getClass() + " has been created");
    }

    public static void CreateTeacherInDb(Human human) {

        Table.TableEditor te = new Table.TableEditor();
        System.out.println("New record of " + te.newLine(url, user, password, insertNewTeacherQuery, human).getClass() + " has been created");
    }

    public static void ShowMenu() {
        System.out.println("""
                 Welcome to Simple School Database!\s
                What you want to do?\s
                1. Create new teacher
                2. Create new pupil
                3. Show all teachers
                4. Show all pupils
                5. Create teachers table
                6. Create pupils table
                """);
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

    public static List<Teacher> ShowTeachers() {

        Table.TableEditor te = new Table.TableEditor();
        return te.showTheTable(url, user, password, showTeachers, Teacher.class);
    }

    public static List<Pupil> ShowPupils() {

        Table.TableEditor te = new Table.TableEditor();
        return te.showTheTable(url, user, password, showPupils, Pupil.class);
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
    default Human newLine(String url, String user, String password, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

          switch (human.getClass().getName()) {

                case "Teacher":
                    Teacher teacher = (Teacher) human;
                    preparedStatement.setString(1, teacher.name);
                    preparedStatement.setString(2, teacher.schoolClass);
                    preparedStatement.setString(3, teacher.specialization);
                    preparedStatement.execute();
                    return teacher;
                case "Pupil":
                    Pupil pupil = (Pupil) human;
                    preparedStatement.setString(1, pupil.name);
                    preparedStatement.setString(2, pupil.schoolClass);
                    preparedStatement.execute();
                    return pupil;
                default:
                    break;
            }

//            if (human instanceof Pupil) {
//                Pupil pup = new Pupil(human.name, human.schoolClass);
//                preparedStatement.setString(1, pup.name);
//                preparedStatement.setString(2, pup.schoolClass);
//                preparedStatement.execute();
//                return pup;
//            } else if (human instanceof Teacher) {
//                Teacher tea = new Teacher(human.name, human.schoolClass, ((Teacher) human).specialization);
//                preparedStatement.setString(1, tea.name);
//                preparedStatement.setString(2, tea.schoolClass);
//                preparedStatement.setString(3, tea.specialization);
//                preparedStatement.execute();
//                return tea;
//            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return human;
    }
}

interface showTable {

    default <T> List<T> showTheTable(String url, String user, String password, String sqlQuery, Class<T> returnedType) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            Statement statement = connect.createStatement();
            statement.executeQuery(sqlQuery);
            ResultSet resultset = statement.executeQuery(sqlQuery);
            int columns = resultset.getMetaData().getColumnCount();


            switch (returnedType.getName()) {
                case "Teacher": {
                    List<Teacher> list = new ArrayList<>();

                    while (resultset.next()) {
                        for (int i = 1; i < columns + 1; i += 3) {
                            list.add(new Teacher(resultset.getString(i), resultset.getString(i + 1), resultset.getString(i + 2)));
                        }
                    }
                    return (List<T>) list;
                }
                case "Pupil": {
                    List<Pupil> list = new ArrayList<>();

                    while (resultset.next()) {
                        for (int i = 1; i < columns + 1; i += 2) {
                            list.add(new Pupil(resultset.getString(i), resultset.getString(i + 1)));
                        }
                    }
                    return (List<T>) list;
                }
                default:
                    break;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        List smth = new ArrayList<>();
        return smth;
    }
}

class Table {

    static class TableEditor implements createTable, createLineInDB, showTable {
        @Override
        public String newTable(String url, String user, String password, String sqlQuery) {
            return createTable.super.newTable(url, user, password, sqlQuery);
        }


        @Override
        public Human newLine(String url, String user, String password, String sqlQuery, Human human) {
            return createLineInDB.super.newLine(url, user, password, sqlQuery, human);
        }

        @Override
        public <T> List<T> showTheTable(String url, String user, String password, String sqlQuery, Class<T> returnedType) {
            return showTable.super.showTheTable(url, user, password, sqlQuery, returnedType);
        }
    }

}