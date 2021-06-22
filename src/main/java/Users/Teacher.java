package Users;

public class Teacher extends Human {

    public String specialization;

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
}
