package SQL;

public class FindRecord {

    public static String showLastPupilRecord = "SELECT Id,Name,Class FROM pupils ORDER BY ID DESC LIMIT 1";
    public static String showLastTeacherRecord = "SELECT * FROM teachers ORDER BY ID DESC LIMIT 1";
    public static String fingPupilById = """
            Select Name,Class FROM pupils where pupils.Id =?;
            """;
    public static String fingTeacherById = """
            Select Name,Specialization,Class FROM teachers where teachers.Id =?;
            """;


}
