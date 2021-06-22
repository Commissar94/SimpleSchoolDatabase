package Users;

public interface TeacherInterface {

    Teacher create(Teacher teacher);

    Teacher update(Teacher teacher);

    Teacher delete(Teacher teacher);

    Teacher getById(long id);

}
