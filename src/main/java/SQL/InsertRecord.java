package SQL;

public class InsertRecord {

    public static String insertNewPupilQuery = """
            INSERT INTO school.pupils (Name, Class)\s
            VALUES (?,?);
            """;

    public static String insertNewTeacherQuery = """
            INSERT INTO school.teachers (Name, Specialization, Class)\s
            VALUES (?,?,?);
            """;

}
