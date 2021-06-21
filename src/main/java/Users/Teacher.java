package Users;

import SQL.ConnectData;
import SQL.DeleteRecord;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;

public class Teacher extends Human {

    public String specialization;
    static Table.TableEditor te = new Table.TableEditor();
    static ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"


    public Teacher(String name, String teacherClass, String specialization) {
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

    public interface TeacherInterface {
        default Teacher create(Teacher teacher) {
            te.CreateTeacherInDb(connectData, teacher);
            return teacher;
        }

        default Teacher update(Teacher teacher) {
            te.updateTheLine(connectData, UpdateRecord.updateTeachers, teacher);
            return teacher;
        }

        default Teacher delete(Teacher teacher) {

            te.deleteTheLine(connectData, DeleteRecord.deleteTeacherQuery, teacher);
            return teacher;
        }

        default Teacher getById(long id) {
            Teacher teacher = new Teacher(4);
            Teacher teacherFromDb = (Teacher) te.showTheLine(connectData, FindRecord.fingTeacherById, teacher);
            System.out.println("You are looking for Mr./Ms. " + teacherFromDb.name +
                    " who works in " + teacherFromDb.schoolClass + " grade " +
                    "This teacher is " + teacherFromDb.specialization);
            return teacherFromDb;
        }
    }
}
