import Menu.Menu;
import SQL.ConnectData;
import SQL.CreateTable;
import Table.Table;

import java.util.Scanner;

public class main {

    public static ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Table.TableEditor te = new Table.TableEditor();
        te.newTable(connectData,CreateTable.createTeachersTableQuery); //создаем таблицу учителей (если ее нет)
        te.newTable(connectData,CreateTable.createPupilsTableQuery); //создаем таблицу учеников (если ее нет)
        Menu menu = new Menu(); //создаем объект меню
        menu.ShowMenu();
        menu.MenuChoice(sc.nextInt()); //даем выбор учителя или ученика
    }
}