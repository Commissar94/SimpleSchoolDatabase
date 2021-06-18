package SQL;

public class CreateTable {

    public static String createTeachersTableQuery = """
            create table IF NOT EXISTS Teachers
            (
            \tid int auto_increment,
            \tName char(30) not null,
            \tSpecialization char(30) not null,
            \tClass char(5) null,\s
            \tconstraint Teachers_pk
            \t\tprimary key (id)
            );""";

    public static String createPupilsTableQuery = """
            create table IF NOT EXISTS Pupils
            (
            \tId int auto_increment,
            \tName char(30) null,
            \tClass char(4) null,
            \tconstraint Pupils_pk
            \t\tprimary key (Id)
            );
            """;
}
