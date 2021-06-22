
import SQL.*;
import Users.*;

public class main {

    public static ConnectData connectData = new ConnectData("School");//получаем данные для подключения к базе "Школа"

    public static void main(String[] args) {

        TeacherInterface ti = new InterfaceOfTeacher();
        PupilInterface pi = new InterfaceOfPupil();

        Teacher teacher = new Teacher("Vasya", "3 a", "povar");
        Teacher teacherForUpdate = new Teacher(5, "Vasya", "3 a", "povar");
        Teacher teacherForDelete = new Teacher(25);

        Pupil pupil = new Pupil("Alexander", "2 a");
        Pupil pupilForUpdate = new Pupil(5, "Vladimir", "3 a");
        Pupil pupilForDelete = new Pupil(3);


        Teacher createdTeacher = ti.create(teacher);
        Teacher updatedTeacher = ti.update(teacherForUpdate);
        Teacher teacherFromDelete = ti.delete(teacherForDelete);
        Teacher teacherFromId = ti.getById(1);

        Pupil createdPupil = pi.create(pupil);
        Pupil updatedPupil = pi.update(pupilForUpdate);
        Pupil pupilFromDelete = pi.delete(pupilForDelete);
        Pupil pupilFromId = pi.getById(3);
    }
}

class InterfaceOfTeacher implements TeacherInterface {

    public void createTeacherTable() {
        TeacherInterface.tableEditorForTeachers.CreateTeacherTableInDb(main.connectData);
    }

    public TeacherInterface ti = new TeacherInterface() {
    };

    InterfaceOfTeacher() {
        createTeacherTable();
    }
}

class InterfaceOfPupil implements PupilInterface {

    public void createPupilTable() {
        PupilInterface.tableEditorForPupils.CreatePupilTableInDb(connectData);
    }

    public PupilInterface pi = new PupilInterface() {
    };

    InterfaceOfPupil() {
        createPupilTable();
    }
}