package Users;

import SQL.ConnectData;
import SQL.DeleteRecord;
import SQL.FindRecord;
import SQL.UpdateRecord;
import Table.Table;


public interface PupilInterface {

    Table table = new Table();
    Table.TableEditor te = table.new TableEditor();
    ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"

    default void createDb(){
        te.CreatePupilTableInDb(connectData);
    }

    default Pupil create(Pupil pupil) {
        createDb();
        te.CreatePupilInDb(connectData, pupil);
        return pupil;
    }

    default Pupil update(Pupil pupil) {
        createDb();
        te.updateTheLine(connectData, UpdateRecord.updatePupils, pupil);
        return pupil;
    }

    default Pupil delete(Pupil pupil) {
        createDb();
        te.deleteTheLine(connectData, DeleteRecord.deletePupilQuery, pupil);
        return pupil;
    }

    default Pupil getById(long id) {
        createDb();
        Pupil pupil = new Pupil(4);
        Pupil pupilFromDb = (Pupil) te.showTheLine(connectData, FindRecord.fingPupilById, pupil);
        System.out.println("You are looking for Mr./Ms. " + pupilFromDb.name +
                " who works in " + pupilFromDb.schoolClass + " grade " +
                "This teacher is ");
        return pupilFromDb;
    }
}


