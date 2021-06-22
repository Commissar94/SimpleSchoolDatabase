package Table;

import SQL.ConnectData;
import SQL.FindRecord;
import SQL.InsertRecord;
import SQL.ShowTable;
import Users.Human;
import Users.Pupil;
import Users.Teacher;

import java.util.List;

public class Table {

    public class TableEditor implements createTable, createLineInDB, showTable, showLastLineInDB, showLineInDB, updateLineInDB, deleteLineInDB {

        public List<Teacher> ShowTeachers(ConnectData connectData) {

            Table.TableEditor te = new Table.TableEditor();
            return te.showTheTable(connectData, ShowTable.showTeachers, Teacher.class);
        }
        public List<Pupil> ShowPupils(ConnectData connectData) {

            Table.TableEditor te = new Table.TableEditor();
            return te.showTheTable(connectData, ShowTable.showPupils, Pupil.class);
        }

        public Human CreatePupilInDb(ConnectData connectData, Human human) {

            Table.TableEditor te = new Table.TableEditor();
            System.out.println("New record of " + te.newLine(connectData, InsertRecord.insertNewPupilQuery, human).getClass() + " has been created");
            Human createdHuman = te.showTheLastLine(connectData, FindRecord.showLastPupilRecord, human);
            // System.out.println(createdHuman.id + " " + createdHuman.name)
            return createdHuman;
        }

        public Human CreateTeacherInDb(ConnectData connectData, Human human) {

            Table.TableEditor te = new Table.TableEditor();
            System.out.println("New record of " + te.newLine(connectData, InsertRecord.insertNewTeacherQuery, human).getClass() + " has been created");
            Human createdHuman = te.showTheLastLine(connectData, FindRecord.showLastTeacherRecord, human);
            //System.out.println(createdHuman.id + " " + createdHuman.name);
            return human;
        }

        @Override
        public Human deleteTheLine(ConnectData connectData, String sqlQuery, Human human) {
            return deleteLineInDB.super.deleteTheLine(connectData, sqlQuery, human);
        }

        @Override
        public void newTable(ConnectData connectData, String sqlQuery) {
            createTable.super.newTable(connectData, sqlQuery);
        }

        @Override
        public Human newLine(ConnectData connectData, String sqlQuery, Human human) {
            return createLineInDB.super.newLine(connectData, sqlQuery, human);
        }

        @Override
        public <T> List<T> showTheTable(ConnectData connectData, String sqlQuery, Class<T> returnedType) {
            return showTable.super.showTheTable(connectData, sqlQuery, returnedType);
        }

        @Override
        public Human showTheLastLine(ConnectData connectData, String sqlQuery, Human human) {
            return showLastLineInDB.super.showTheLastLine(connectData, sqlQuery, human);
        }

        @Override
        public Human showTheLine(ConnectData connectData, String sqlQuery, Human human) {
            return showLineInDB.super.showTheLine(connectData, sqlQuery, human);
        }

        @Override
        public Human updateTheLine(ConnectData connectData, String sqlQuery, Human human) {
            return updateLineInDB.super.updateTheLine(connectData, sqlQuery, human);
        }
    }
}
