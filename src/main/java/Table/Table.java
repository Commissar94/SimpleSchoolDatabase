package Table;

import SQL.*;
import Users.*;


public class Table {

    public class TableEditor implements createTable, createLineInDB, showTable, showLastLineInDB, showLineInDB, updateLineInDB, deleteLineInDB {

        public Human CreatePupilInDb(ConnectData connectData, Human human) {
            Table.TableEditor te = new Table.TableEditor();
            System.out.println("New record of " + te.newLine(connectData, InsertRecord.insertNewPupilQuery, human).getClass() + " has been created");
            // System.out.println(createdHuman.id + " " + createdHuman.name)
            return te.showTheLastLine(connectData, FindRecord.showLastPupilRecord, human);
        }

        public Human CreateTeacherInDb(ConnectData connectData, Human human) {
            Table.TableEditor te = new Table.TableEditor();
            System.out.println("New record of " + te.newLine(connectData, InsertRecord.insertNewTeacherQuery, human).getClass() + " has been created");
            //System.out.println(createdHuman.id + " " + createdHuman.name);
            return te.showTheLastLine(connectData, FindRecord.showLastTeacherRecord, human);
        }

        public void CreateTeacherTableInDb(ConnectData connectData) {
            Table.TableEditor te = new Table.TableEditor();
            te.newTable(connectData, CreateTable.createTeachersTableQuery);
        }

        public void CreatePupilTableInDb(ConnectData connectData) {
            Table.TableEditor te = new Table.TableEditor();
            te.newTable(connectData, CreateTable.createPupilsTableQuery);
        }
    }
}
