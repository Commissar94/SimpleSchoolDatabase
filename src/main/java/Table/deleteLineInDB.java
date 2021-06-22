package Table;

import SQL.ConnectData;
import SQL.FindRecord;
import Users.Human;
import Users.Pupil;
import Users.Teacher;

import java.sql.*;

public interface deleteLineInDB {

    default Human deleteTheLine(ConnectData connectData, String sqlQuery, Human human) {

        try {
            Table table = new Table();
            Table.TableEditor te = table.new TableEditor();
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, human.id);
            if (human.getClass().getName().equals("Users.Teacher")) {
                Teacher teacherFromDb = (Teacher) te.showTheLine(connectData, FindRecord.fingTeacherById, human);
                preparedStatement.executeUpdate();
                // System.out.println(teacherFromDb.id + " " + teacherFromDb.name);
                return teacherFromDb;
            } else if (human.getClass().getName().equals("Users.Pupil")) {
                Pupil pupilFromDb = (Pupil) te.showTheLine(connectData, FindRecord.fingPupilById, human);
                preparedStatement.executeUpdate();
                return pupilFromDb;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Why are we here");
        return human;
    }
}
