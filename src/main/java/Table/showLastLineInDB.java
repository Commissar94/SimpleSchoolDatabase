package Table;

import SQL.ConnectData;
import Users.Human;
import Users.Pupil;
import Users.Teacher;

import java.sql.*;

public interface showLastLineInDB {

    default Human showTheLastLine(ConnectData connectData, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            Statement statement = connect.createStatement();
            statement.executeQuery(sqlQuery);
            ResultSet resultset = statement.executeQuery(sqlQuery);

            switch (human.getClass().getName()) {
                case "Users.Teacher": {
                    if (resultset.next()) {
                        Teacher teacher = new Teacher(resultset.getInt(1), resultset.getString(2), resultset.getString(3), resultset.getString(4));
                        return teacher;
                    }
                }
                case "Users.Pupil": {

                    if (resultset.next()) {
                        Pupil pupil = new Pupil(resultset.getLong(1), resultset.getString(2), resultset.getString(3));
                        return pupil;
                    }

                }
                default:
                    break;
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return human;
    }
}
