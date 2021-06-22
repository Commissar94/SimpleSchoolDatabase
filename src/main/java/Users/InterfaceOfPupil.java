package Users;

import SQL.*;
import Table.*;

public class InterfaceOfPupil implements PupilInterface {

    Table table = new Table();
    Table.TableEditor tableEditorForPupils = table.new TableEditor();
    ConnectData connectData = new ConnectData("School");

    public void createPupilTable() {
        tableEditorForPupils.CreatePupilTableInDb(connectData);
    }

    public InterfaceOfPupil() {
        createPupilTable();
    }

    @Override
    public Pupil create(Pupil pupil) {
        tableEditorForPupils.CreatePupilInDb(connectData, pupil);
        return pupil;
    }

    @Override
    public Pupil update(Pupil pupil) {
        tableEditorForPupils.updateTheLine(connectData, UpdateRecord.updatePupils, pupil);
        return pupil;
    }

    @Override
    public Pupil delete(Pupil pupil) {
        tableEditorForPupils.deleteTheLine(connectData, DeleteRecord.deletePupilQuery, pupil);
        return pupil;
    }

    @Override
    public Pupil getById(long id) {
        Pupil pupil = new Pupil(4);
        Pupil pupilFromDb = (Pupil) tableEditorForPupils.showTheLine(connectData, FindRecord.fingPupilById, pupil);
        System.out.println("You are looking for Mr./Ms. " + pupilFromDb.name +
                " who works in " + pupilFromDb.schoolClass + " grade " +
                "This teacher is ");
        return pupilFromDb;
    }
}
