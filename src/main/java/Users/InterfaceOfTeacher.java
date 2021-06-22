package Users;

import SQL.ConnectData;
import SQL.DeleteRecord;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;

public class InterfaceOfTeacher implements TeacherInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForTeachers = table.new TableEditor();
    ConnectData connectData = new ConnectData("School");


    public void createTeacherTable() {
        tableEditorForTeachers.CreateTeacherTableInDb(connectData);
    }

    public InterfaceOfTeacher() {
        createTeacherTable();
    }

    @Override
    public Teacher create(Teacher teacher) {
        tableEditorForTeachers.CreateTeacherInDb(connectData, teacher);
        return teacher;
    }

    @Override
    public Teacher update(Teacher teacher) {
        tableEditorForTeachers.updateTheLine(connectData, UpdateRecord.updateTeachers, teacher);
        return teacher;
    }

    @Override
    public Teacher delete(Teacher teacher) {
        tableEditorForTeachers.deleteTheLine(connectData, DeleteRecord.deleteTeacherQuery, teacher);
        return teacher;
    }

    @Override
    public Teacher getById(long id) {
        Teacher teacher = new Teacher(4);
        Teacher teacherFromDb = (Teacher) tableEditorForTeachers.showTheLine(connectData, FindRecord.fingTeacherById, teacher);
        System.out.println("You are looking for Mr./Ms. " + teacherFromDb.name +
                " who works in " + teacherFromDb.schoolClass + " grade " +
                "This teacher is " + teacherFromDb.specialization);
        return teacherFromDb;
    }
}
