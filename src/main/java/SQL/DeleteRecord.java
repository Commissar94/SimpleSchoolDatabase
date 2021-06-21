package SQL;

public class DeleteRecord {

    public static String deletePupilQuery = """
            DELETE from pupils where Id =?;
            """;

    public static String deleteTeacherQuery = """
            DELETE from teachers where Id =?;
            """;

}
