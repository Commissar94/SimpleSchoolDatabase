package Users;

import SQL.*;
import Table.*;

public interface TeacherInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForTeachers = table.new TableEditor();
    ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"

    default Teacher create(Teacher teacher) {
        tableEditorForTeachers.CreateTeacherInDb(connectData, teacher);
        return teacher;
    }

    default Teacher update(Teacher teacher) {
        tableEditorForTeachers.updateTheLine(connectData, UpdateRecord.updateTeachers, teacher);
        return teacher;
    }

    default Teacher delete(Teacher teacher) {
        tableEditorForTeachers.deleteTheLine(connectData, DeleteRecord.deleteTeacherQuery, teacher);
        return teacher;
    }

    default Teacher getById(long id) {
        Teacher teacher = new Teacher(4);
        Teacher teacherFromDb = (Teacher) tableEditorForTeachers.showTheLine(connectData, FindRecord.fingTeacherById, teacher);
        System.out.println("You are looking for Mr./Ms. " + teacherFromDb.name +
                " who works in " + teacherFromDb.schoolClass + " grade " +
                "This teacher is " + teacherFromDb.specialization);
        return teacherFromDb;
    }
}
