package Table;

import SQL.ConnectData;
import Users.Human;
import Users.Pupil;
import Users.Teacher;

import java.sql.*;

public interface createLineInDB {
    default Human newLine(ConnectData connectData, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);

            switch (human.getClass().getName()) {

                case "Users.Teacher":

                    Teacher teacher = (Teacher) human;
                    preparedStatement.setString(1, teacher.name);
                    preparedStatement.setString(2, teacher.specialization);
                    preparedStatement.setString(3, teacher.schoolClass);
                    preparedStatement.execute();
                    return teacher;
                case "Users.Pupil":
                    Pupil pupil = (Pupil) human;
                    preparedStatement.setString(1, pupil.name);
                    preparedStatement.setString(2, pupil.schoolClass);
                    preparedStatement.execute();
                    return pupil;
                default:
                    break;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return human;
    }
}
