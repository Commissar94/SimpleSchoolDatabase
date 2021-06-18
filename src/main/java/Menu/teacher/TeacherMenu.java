package Menu.teacher;

import SQL.ConnectData;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;
import Users.Teacher;

import java.util.Scanner;

public class TeacherMenu {
    static Scanner sc = new Scanner(System.in);

    public static void ShowTeacherMenu() {
        System.out.println("""
                What do you want to do?\s
                1. Create new teacher
                2. Get all teachers
                3. Update teacher
                4. Find teacher by ID
                """);
        TeacherMenuChoice(sc.nextInt());
    }

    public static void TeacherMenuChoice(int choice) {

        Table.TableEditor te = new Table.TableEditor();
        ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"

        switch (choice) {
            case 1:
                sc.nextLine(); //кушаем линию
                System.out.println("Enter teacher's name, class and specialization");
                Teacher teacher = new Teacher(sc.nextLine(), sc.nextLine(), sc.nextLine());
                te.CreateTeacherInDb(connectData, teacher);
                break;
            case 2:
                te.ShowTeachers(connectData);
                break;
            case 3:
                System.out.println("Enter teacher's id for updating his record");
                Teacher teacher3 = new Teacher(sc.nextLong());
                Teacher teacherFromDb = (Teacher) te.showTheLine(connectData, FindRecord.fingTeacherById, teacher3);
                System.out.println("Enter new name, specialization and class of the teacher");
                sc.nextLine(); //кушаем линию
                Teacher teacherForUpdate = new Teacher(teacherFromDb.id, sc.nextLine(), sc.nextLine(), sc.nextLine());
                te.updateTheLine(connectData, UpdateRecord.updateTeachers, teacherForUpdate);
                System.out.println("Record with id #" + "" + teacherForUpdate.id + " is " + teacherForUpdate.name + " " + teacherForUpdate.specialization + " " + teacherForUpdate.schoolClass);
                break;
            case 4:
                System.out.println("Enter id of a teacher you want to find");
                Teacher teacher4 = new Teacher(sc.nextLong());
                Teacher teacherFromDb4 = (Teacher) te.showTheLine(connectData, FindRecord.fingTeacherById, teacher4);
                System.out.println("You are looking for Mr./Ms. " + teacherFromDb4.name +
                        " who works in " + teacherFromDb4.schoolClass + " grade " +
                        "This teacher is " + teacherFromDb4.specialization);

                break;
            default:
                break;
        }
    }


}
