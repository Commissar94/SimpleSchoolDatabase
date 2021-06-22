package Users;

public class Pupil extends Human {

    public Pupil(String name, String pupilClass) {
        this.name = name;
        this.schoolClass = pupilClass;
    }

    public Pupil(long id) {
        this.id = id;
    }

    public Pupil(long id, String name, String pupilClass) {
        this.id = id;
        this.name = name;
        this.schoolClass = pupilClass;
    }
}