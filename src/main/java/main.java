
import SQL.*;
import Table.*;
import Users.*;

public class main {

    public static ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"
    public TeacherInterface ti = new TeacherInterface() {
        @Override
        public Teacher create(Teacher teacher) {
            return TeacherInterface.super.create(teacher);
        }

        @Override
        public Teacher update(Teacher teacher) {
            return TeacherInterface.super.update(teacher);
        }

        @Override
        public Teacher delete(Teacher teacher) {
            return TeacherInterface.super.delete(teacher);
        }

        @Override
        public Teacher getById(long id) {
            return TeacherInterface.super.getById(id);
        }
    };
    public PupilInterface pi = new PupilInterface() {
        @Override
        public Pupil create(Pupil pupil) {
            return PupilInterface.super.create(pupil);
        }

        @Override
        public Pupil update(Pupil pupil) {
            return PupilInterface.super.update(pupil);
        }

        @Override
        public Pupil delete(Pupil pupil) {
            return PupilInterface.super.delete(pupil);
        }

        @Override
        public Pupil getById(long id) {
            return PupilInterface.super.getById(id);
        }
    };

    public void main(String[] args) {

        ti.createDb(); //создание таблицы учителей
        pi.createDb(); //создание таблицы учеников

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