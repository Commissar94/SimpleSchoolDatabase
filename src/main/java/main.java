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
    public static String showLastPupilRecord = "SELECT Id,Name,Class FROM pupils ORDER BY ID DESC LIMIT 1";
    public static String showLastTeacherRecord = "SELECT * FROM teachers ORDER BY ID DESC LIMIT 1";
    public static String fingPupilById = """
            Select Name,Class FROM pupils where pupils.Id =?;
            """;
    public static String fingTeacherById = """
            Select Name,Specialization,Class FROM teachers where teachers.Id =?;
            """;
    public static String updatePupils = """
            UPDATE pupils
            set Name  = ?,
                Class = ?
            where id = ?;
            """;
    public static String updateTeachers = """
            UPDATE teachers
            set Name          = ?,
                Specialization= ?,
                Class         = ?
            where id = ?;
            """;

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

    public static Human CreatePupilInDb(Human human) {

        Table.TableEditor te = new Table.TableEditor();
        System.out.println("New record of " + te.newLine(url, user, password, insertNewPupilQuery, human).getClass() + " has been created");
        Human createdHuman = te.showTheLastLine(url, user, password, showLastPupilRecord, human);
        // System.out.println(createdHuman.id + " " + createdHuman.name)
        return createdHuman;
    }

    public static Human CreateTeacherInDb(Human human) {

        Table.TableEditor te = new Table.TableEditor();
        System.out.println("New record of " + te.newLine(url, user, password, insertNewTeacherQuery, human).getClass() + " has been created");
        Human createdHuman = te.showTheLastLine(url, user, password, showLastTeacherRecord, human);
        // System.out.println(createdHuman.id + " " + createdHuman.name);
        return human;
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
                7. Update teacher
                8. Update pupil
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
            case 7 -> {
                System.out.println("Enter teacher's id for updating his record");
                Teacher teacher = new Teacher(sc.nextLong());
                Table.TableEditor te = new Table.TableEditor();
                Teacher teacherFromDb = (Teacher) te.showTheLine(url, user, password, fingTeacherById, teacher);
                System.out.println("Enter new name, specialization and class of the teacher");
                sc.nextLine(); //кушаем линию
                Teacher teacherForUpdate = new Teacher(teacherFromDb.id, sc.nextLine(), sc.nextLine(), sc.nextLine());

                te.updateTheLine(url, user, password, updateTeachers, teacherForUpdate);
                System.out.println("Record with id #" + "" + teacherForUpdate.id + " is " + teacherForUpdate.name + " " + teacherForUpdate.specialization + " " + teacherForUpdate.schoolClass);
            }
            case 8 -> {
                System.out.println("Enter pupil's id for updating his record");
                Pupil pupil = new Pupil(sc.nextLong());
                Table.TableEditor te = new Table.TableEditor();
                Pupil pupilFromDb = (Pupil) te.showTheLine(url, user, password, fingPupilById, pupil);
                System.out.println("Enter new name and class of the pupil");
                sc.nextLine(); //кушаем линию
                Pupil pupilForUpdate = new Pupil(pupilFromDb.id, sc.nextLine(), sc.nextLine());

                te.updateTheLine(url, user, password, updatePupils, pupilForUpdate);
                System.out.println("Record with id #" + "" + pupilForUpdate.id + " is " + pupilForUpdate.name + " " + pupilForUpdate.schoolClass);
            }
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
    long id;
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

    public Teacher(long id) {
        this.id = id;
    }

    public Teacher(long id, String name, String teacherClass, String specialization) {
        this.id = id;
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

    public Pupil(long id) {
        this.id = id;
    }

    public Pupil(long id, String name, String pupilClass) {
        this.id = id;
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
                    preparedStatement.setString(2, teacher.specialization);
                    preparedStatement.setString(3, teacher.schoolClass);
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
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return human;
    }
}

//interface getHuman {
//
//    default Human getHumanById(String url, String user, String password, String sqlQuery, Human human) {
//
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection connect = DriverManager.getConnection(url, user, password);
//            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
//
//            switch (human.getClass().getName()) {
//                case "Teacher":
//                    Teacher teacher = (Teacher) human;
//                    preparedStatement.setString(1, teacher.name);
//                    preparedStatement.setString(2, teacher.specialization);
//                    preparedStatement.setString(3, teacher.schoolClass);
//                    preparedStatement.execute();
//                    return teacher;
//                case "Pupil":
//                    Pupil pupil = (Pupil) human;
//                    preparedStatement.setString(1, pupil.name);
//                    preparedStatement.setString(2, pupil.schoolClass);
//                    preparedStatement.execute();
//                    return pupil;
//                default:
//                    break;
//            }
//
//
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }
//        return human;
//
//    }


interface updateLineInDB {

    default Human updateTheLine(String url, String user, String password, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery);

            switch (human.getClass().getName()) {
                case "Teacher": {
                    Teacher teacher = (Teacher) human;
                    preparedStatement.setString(1, teacher.name);
                    preparedStatement.setString(2, teacher.specialization);
                    preparedStatement.setString(3, teacher.schoolClass);
                    preparedStatement.setLong(4, teacher.id);
                    preparedStatement.execute();
                }
                case "Pupil": {
                    Pupil pupil = (Pupil) human;
                    preparedStatement.setString(1, pupil.name);
                    preparedStatement.setString(2, pupil.schoolClass);
                    preparedStatement.setLong(3, pupil.id);
                    preparedStatement.execute();
                }
                default:
                    break;
            }

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

interface showLastLineInDB {

    default Human showTheLastLine(String url, String user, String password, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            Statement statement = connect.createStatement();
            statement.executeQuery(sqlQuery);
            ResultSet resultset = statement.executeQuery(sqlQuery);


            switch (human.getClass().getName()) {
                case "Teacher": {
                    if (resultset.next()) {
                        Teacher teacher = new Teacher(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getString(4));
                        return teacher;
                    }
                }
                case "Pupil": {

                    if (resultset.next()) {
                        Pupil pupil = new Pupil(resultset.getLong(1), resultset.getString(2), resultset.getString(3));
                        return pupil;
                    }

                }
                default:
                    break;
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return human;
    }


}

interface showLineInDB {

    default Human showTheLine(String url, String user, String password, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, human.id);
            ResultSet rs = preparedStatement.executeQuery();

            switch (human.getClass().getName()) {

                case "Teacher":
                    while (rs.next()) {
                        return new Teacher(human.id, rs.getString(1), rs.getString(2), rs.getString(3));
                    }

                case "Pupil":
                    while (rs.next()) {
                        System.out.println(rs.getString(1));
                        System.out.println(rs.getString(2));
                        return new Pupil(human.id, rs.getString(1), rs.getString(2));
                    }

                default:
                    break;
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return human;
    }

}

class Table {

    static class TableEditor implements createTable, createLineInDB, showTable, showLastLineInDB, showLineInDB, updateLineInDB {
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

        @Override
        public Human showTheLastLine(String url, String user, String password, String sqlQuery, Human human) {
            return showLastLineInDB.super.showTheLastLine(url, user, password, sqlQuery, human);
        }

        @Override
        public Human showTheLine(String url, String user, String password, String sqlQuery, Human human) {
            return showLineInDB.super.showTheLine(url, user, password, sqlQuery, human);
        }

        @Override
        public Human updateTheLine(String url, String user, String password, String sqlQuery, Human human) {
            return updateLineInDB.super.updateTheLine(url, user, password, sqlQuery, human);
        }
    }

}