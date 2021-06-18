package Table;

import SQL.ConnectData;
import Users.Pupil;
import Users.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface showTable {

    default <T> List<T> showTheTable(ConnectData connectData, String sqlQuery, Class<T> returnedType) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            Statement statement = connect.createStatement();
            statement.executeQuery(sqlQuery);
            ResultSet resultset = statement.executeQuery(sqlQuery);
            int columns = resultset.getMetaData().getColumnCount();


            switch (returnedType.getName()) {
                case "Users.Teacher": {
                    List<Teacher> list = new ArrayList<>();

                    while (resultset.next()) {
                        for (int i = 1; i < columns + 1; i += 3) {
                            list.add(new Teacher(resultset.getString(i), resultset.getString(i + 1), resultset.getString(i + 2)));
                        }
                    }
                    return (List<T>) list;
                }
                case "Users.Pupil": {
                    List<Pupil> list = new ArrayList<>();

                    while (resultset.next()) {
                        for (int i = 1; i < columns + 1; i += 2) {
                            list.add(new Pupil(resultset.getString(i), resultset.getString(i + 1)));
                        }
                    }
                    return (List<T>) list;
                }
                default:
                    break;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        List smth = new ArrayList<>();
        return smth;
    }
}
