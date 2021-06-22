package Users;

import SQL.ConnectData;
import SQL.DeleteRecord;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;

public interface TeacherInterface {

    Table table = new Table();
    Table.TableEditor te = table.new TableEditor();
    ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"

    default void createDb() {
        te.CreateTeacherTableInDb(connectData);
    }

    default Teacher create(Teacher teacher) {
        createDb();
        te.CreateTeacherInDb(connectData, teacher);
        return teacher;
    }

    default Teacher update(Teacher teacher) {
        createDb();
        te.updateTheLine(connectData, UpdateRecord.updateTeachers, teacher);
        return teacher;
    }

    default Teacher delete(Teacher teacher) {
        createDb();
        te.deleteTheLine(connectData, DeleteRecord.deleteTeacherQuery, teacher);
        return teacher;
    }

    default Teacher getById(long id) {
        createDb();
        Teacher teacher = new Teacher(4);
        Teacher teacherFromDb = (Teacher) te.showTheLine(connectData, FindRecord.fingTeacherById, teacher);
        System.out.println("You are looking for Mr./Ms. " + teacherFromDb.name +
                " who works in " + teacherFromDb.schoolClass + " grade " +
                "This teacher is " + teacherFromDb.specialization);
        return teacherFromDb;
    }
}
