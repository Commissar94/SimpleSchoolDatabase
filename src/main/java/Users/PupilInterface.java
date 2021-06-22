package Users;

import SQL.*;
import Table.*;

public interface PupilInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForPupils = table.new TableEditor();
    ConnectData connectData = new ConnectData("School"); //получаем данные для подключения к базе "Школа"


    default Pupil create(Pupil pupil) {
        tableEditorForPupils.CreatePupilInDb(connectData, pupil);
        return pupil;
    }

    default Pupil update(Pupil pupil) {
        tableEditorForPupils.updateTheLine(connectData, UpdateRecord.updatePupils, pupil);
        return pupil;
    }

    default Pupil delete(Pupil pupil) {
        tableEditorForPupils.deleteTheLine(connectData, DeleteRecord.deletePupilQuery, pupil);
        return pupil;
    }

    default Pupil getById(long id) {
        Pupil pupil = new Pupil(4);
        Pupil pupilFromDb = (Pupil) tableEditorForPupils.showTheLine(connectData, FindRecord.fingPupilById, pupil);
        System.out.println("You are looking for Mr./Ms. " + pupilFromDb.name +
                " who works in " + pupilFromDb.schoolClass + " grade " +
                "This teacher is ");
        return pupilFromDb;
    }
}


