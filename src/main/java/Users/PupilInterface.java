package Users;

public interface PupilInterface {

    Pupil create(Pupil pupil);

    Pupil update(Pupil pupil);

    Pupil delete(Pupil pupil);

    Pupil getById(long id);
}


