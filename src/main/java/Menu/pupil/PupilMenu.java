package Menu.pupil;

import SQL.ConnectData;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;
import Users.Pupil;

import java.util.Scanner;

public class PupilMenu {
    static Scanner sc = new Scanner(System.in);

    public static void ShowPupilMenu() {
        System.out.println("""
                What do you want to do?\s
                1. Create new pupil
                2. Get all pupils
                3. Update pupil
                4. Find pupil by ID
                """);

        PupilMenuChoice(sc.nextInt());
    }

    public static void PupilMenuChoice(int choice) {


        Table.TableEditor te = new Table.TableEditor();
        ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"


        switch (choice) {
            case 1:
                sc.nextLine(); //кушаем линию
                System.out.println("Enter pupil's name and class");
                Pupil pupil = new Pupil(sc.nextLine(), sc.nextLine());
                te.CreatePupilInDb(connectData, pupil);
                break;
            case 2:
                te.ShowPupils(connectData);
                break;
            case 3:
                System.out.println("Enter pupil's id for updating his record");
                Pupil pupil3 = new Pupil(sc.nextLong());
                Pupil pupilFromDb = (Pupil) te.showTheLine(connectData, FindRecord.fingPupilById, pupil3);
                System.out.println("Enter new name and class of the pupil");
                sc.nextLine(); //кушаем линию
                Pupil pupilForUpdate = new Pupil(pupilFromDb.id, sc.nextLine(), sc.nextLine());
                te.updateTheLine(connectData, UpdateRecord.updatePupils, pupilForUpdate);
                System.out.println("Record with id #" + "" + pupilForUpdate.id + " is " + pupilForUpdate.name + " " + pupilForUpdate.schoolClass);
                break;
            case 4:
                System.out.println("Enter id of the pupil you want to find");
                Pupil pupil4 = new Pupil(sc.nextLong());
                Pupil pupilFromDb4 = (Pupil) te.showTheLine(connectData, FindRecord.fingPupilById, pupil4);
                System.out.println("You are looking for " + pupilFromDb4.name + " from " + pupilFromDb4.schoolClass + "grade");
                break;
            default:
                break;
        }
    }
}

