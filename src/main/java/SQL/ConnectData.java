package SQL;

public class ConnectData {

    public String url = "";
    public String user = "";
    public String password = "";

    public ConnectData(String nameOfBD) {

        if (nameOfBD.equals("School")) {
            url = "jdbc:mysql://localhost:3306/school";
            user = "root";
            password = "1234";
        }

    }

}
