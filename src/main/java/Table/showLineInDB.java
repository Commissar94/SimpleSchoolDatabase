package Table;

import SQL.ConnectData;
import Users.Human;
import Users.Pupil;
import Users.Teacher;

import java.sql.*;

public interface showLineInDB {

    default Human showTheLine(ConnectData connectData, String sqlQuery, Human human) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery);
            preparedStatement.setLong(1, human.id);
            ResultSet rs = preparedStatement.executeQuery();

            switch (human.getClass().getName()) {

                case "Users.Teacher":
                    while (rs.next()) {
                        return new Teacher(human.id, rs.getString(1), rs.getString(2), rs.getString(3));
                    }

                case "Users.Pupil":
                    while (rs.next()) {
                        return new Pupil(human.id, rs.getString(1), rs.getString(2));
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