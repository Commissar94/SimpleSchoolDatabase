
import SQL.*;
import Users.*;

public class main {

    public static ConnectData connectData = new ConnectData("School");//получаем данные для подключения к базе "Школа"

    public static void main(String[] args) {

        TeacherInterface interfaceOfTeacher = new InterfaceOfTeacher();
        PupilInterface interfaceOfPupil = new InterfaceOfPupil();

        Teacher teacher = new Teacher("Vasya", "3 a", "povar");
        Teacher teacherForUpdate = new Teacher(5, "Vasya", "3 a", "povar");
        Teacher teacherForDelete = new Teacher(25);

        Pupil pupil = new Pupil("Alexander", "2 a");
        Pupil pupilForUpdate = new Pupil(5, "Vladimir", "3 a");
        Pupil pupilForDelete = new Pupil(3);


        Teacher createdTeacher = interfaceOfTeacher.create(teacher);
        Teacher updatedTeacher = interfaceOfTeacher.update(teacherForUpdate);
        Teacher teacherFromDelete = interfaceOfTeacher.delete(teacherForDelete);
        Teacher teacherFromId = interfaceOfTeacher.getById(5);

        Pupil createdPupil = interfaceOfPupil.create(pupil);
        Pupil updatedPupil = interfaceOfPupil.update(pupilForUpdate);
        Pupil pupilFromDelete = interfaceOfPupil.delete(pupilForDelete);
        Pupil pupilFromId = interfaceOfPupil.getById(4);
    }
}

