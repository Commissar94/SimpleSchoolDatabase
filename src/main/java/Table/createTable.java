package Table;

import SQL.ConnectData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public interface createTable {

    default void newTable(ConnectData connectData, String sqlQuery) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection(connectData.url, connectData.user, connectData.password);
            Statement statement = connect.createStatement();
            statement.execute(sqlQuery);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}