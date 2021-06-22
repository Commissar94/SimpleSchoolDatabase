
import SQL.*;
import Table.Table;
import Users.*;

public class main {

    public static ConnectData connectData = new ConnectData("School");//получаем данные для подключения к базе "Школа"

    public static void main(String[] args) {

        TeacherInterface interfaceOfTeacher = new InterfaceOfTeacher();
        PupilInterface pi = new InterfaceOfPupil();

        Teacher teacher = new Teacher("Vasya", "3 a", "povar");
        Teacher teacherForUpdate = new Teacher(5, "Vasya", "3 a", "povar");
        Teacher teacherForDelete = new Teacher(25);

        Pupil pupil = new Pupil("Alexander", "2 a");
        Pupil pupilForUpdate = new Pupil(5, "Vladimir", "3 a");
        Pupil pupilForDelete = new Pupil(3);


        Teacher createdTeacher = interfaceOfTeacher.create(teacher);
        Teacher updatedTeacher = interfaceOfTeacher.update(teacherForUpdate);
        Teacher teacherFromDelete = interfaceOfTeacher.delete(teacherForDelete);
        Teacher teacherFromId = interfaceOfTeacher.getById(1);

        Pupil createdPupil = pi.create(pupil);
        Pupil updatedPupil = pi.update(pupilForUpdate);
        Pupil pupilFromDelete = pi.delete(pupilForDelete);
        Pupil pupilFromId = pi.getById(3);
    }
}

class InterfaceOfTeacher implements TeacherInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForTeachers = table.new TableEditor();
    ConnectData connectData = new ConnectData("School");


    public void createTeacherTable() {
        tableEditorForTeachers.CreateTeacherTableInDb(main.connectData);
    }

    InterfaceOfTeacher() {
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

class InterfaceOfPupil implements PupilInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForPupils = table.new TableEditor();
    ConnectData connectData = new ConnectData("School");

    public void createPupilTable() {
        tableEditorForPupils.CreatePupilTableInDb(connectData);
    }

    InterfaceOfPupil() {
        createPupilTable();
    }

    @Override
    public Pupil create(Pupil pupil) {
        tableEditorForPupils.CreatePupilInDb(connectData, pupil);
        return pupil;
    }

    @Override
    public Pupil update(Pupil pupil) {
        tableEditorForPupils.updateTheLine(connectData, UpdateRecord.updatePupils, pupil);
        return pupil;
    }

    @Override
    public Pupil delete(Pupil pupil) {
        tableEditorForPupils.deleteTheLine(connectData, DeleteRecord.deletePupilQuery, pupil);
        return pupil;
    }

    @Override
    public Pupil getById(long id) {
        Pupil pupil = new Pupil(4);
        Pupil pupilFromDb = (Pupil) tableEditorForPupils.showTheLine(connectData, FindRecord.fingPupilById, pupil);
        System.out.println("You are looking for Mr./Ms. " + pupilFromDb.name +
                " who works in " + pupilFromDb.schoolClass + " grade " +
                "This teacher is ");
        return pupilFromDb;
    }
}