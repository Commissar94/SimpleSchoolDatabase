package Users;

import SQL.ConnectData;
import SQL.DeleteRecord;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;

public class Pupil extends Human {

    static Table.TableEditor te = new Table.TableEditor();
    static ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"

    public Pupil(String name, String pupilClass) {
        this.name = name;
        this.schoolClass = pupilClass;
    }

    public Pupil(long id) {
        this.id = id;
    }

    public Pupil(long id, String name, String pupilClass) {
        this.id = id;
        this.name = name;
        this.schoolClass = pupilClass;
    }

    public interface PupilInterface {
        default Pupil create(Pupil pupil) {
            te.CreatePupilInDb(connectData, pupil);
            return pupil;
        }

        default Pupil update(Pupil pupil) {
            te.updateTheLine(connectData, UpdateRecord.updatePupils, pupil);
            return pupil;
        }

        default Pupil delete(Pupil pupil) {
            te.deleteTheLine(connectData, DeleteRecord.deletePupilQuery, pupil);
            return pupil;
        }

        default Pupil getById(long id) {
            Pupil pupil = new Pupil(4);
            Pupil pupilFromDb = (Pupil) te.showTheLine(connectData, FindRecord.fingPupilById, pupil);
            System.out.println("You are looking for Mr./Ms. " + pupilFromDb.name +
                    " who works in " + pupilFromDb.schoolClass + " grade " +
                    "This teacher is ");
            return pupilFromDb;
        }
    }
}