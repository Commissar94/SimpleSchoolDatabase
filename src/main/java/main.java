
import SQL.ConnectData;
import SQL.CreateTable;
import Table.Table;
import Users.Pupil;
import Users.Teacher;

import java.util.Scanner;

public class main {

    public static ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"
    public static Scanner sc = new Scanner(System.in);
    public static Teacher.TeacherInterface ti = new Teacher.TeacherInterface() {
        @Override
        public Teacher create(Teacher teacher) {
            return Teacher.TeacherInterface.super.create(teacher);
        }

        @Override
        public Teacher update(Teacher teacher) {
            return Teacher.TeacherInterface.super.update(teacher);
        }

        @Override
        public Teacher delete(Teacher teacher) {
            return Teacher.TeacherInterface.super.delete(teacher);
        }

        @Override
        public Teacher getById(long id) {
            return Teacher.TeacherInterface.super.getById(id);
        }
    };
    public static Pupil.PupilInterface pi = new Pupil.PupilInterface() {
        @Override
        public Pupil create(Pupil pupil) {
            return Pupil.PupilInterface.super.create(pupil);
        }

        @Override
        public Pupil update(Pupil pupil) {
            return Pupil.PupilInterface.super.update(pupil);
        }

        @Override
        public Pupil delete(Pupil pupil) {
            return Pupil.PupilInterface.super.delete(pupil);
        }

        @Override
        public Pupil getById(long id) {
            return Pupil.PupilInterface.super.getById(id);
        }
    };

    public static void main(String[] args) {

        Table.TableEditor te = new Table.TableEditor();
        te.newTable(connectData, CreateTable.createTeachersTableQuery); //создаем таблицу учителей (если ее нет)
        te.newTable(connectData, CreateTable.createPupilsTableQuery); //создаем таблицу учеников (если ее нет)


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