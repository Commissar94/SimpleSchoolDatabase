package SQL;

public class UpdateRecord {

    public static String updatePupils = """
            UPDATE pupils
            set Name  = ?,
                Class = ?
            where id = ?;
            """;
    public  static String updateTeachers = """
            UPDATE teachers
            set Name          = ?,
                Specialization= ?,
                Class         = ?
            where id = ?;
            """;


}
