package com.example.quanlynhansu.sqlitehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {

     //SQL INIT TABLE

    // create bonus
    public static final String CREATE_BONUS = "CREATE TABLE Bonus (\n" +
             "    bonus_id  INTEGER PRIMARY KEY AUTOINCREMENT\n" +
             "                      NOT NULL,\n" +
             "    condition REAL    NOT NULL,\n" +
             "    bonus     REAL    NOT NULL\n" +
             ");";

    // create attendance
    public static final String CREATE_ATTENDANCE = "CREATE TABLE Attendance (\n" +
            "    attendance_id INTEGER PRIMARY KEY AUTOINCREMENT\n" +
            "                          NOT NULL,\n" +
            "    date          TEXT    NOT NULL,\n" +
            "    account_id    INTEGER NOT NULL\n" +
            "                          REFERENCES Account (account_id) \n" +
            ");";

    // create room
    public static final String CREATE_ROOM = "CREATE TABLE IF NOT EXISTS Room (\n" +
            "    room_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    name TEXT NOT NULL,\n" +
            "    number INTEGER \n" +
            ");";

    // create salary
    public static final String CREATE_SALARY = "CREATE TABLE Salary (\n" +
            "    salary_id  INTEGER PRIMARY KEY AUTOINCREMENT\n" +
            "                       NOT NULL,\n" +
            "    amount     REAL    NOT NULL,\n" +
            "    date       TEXT    NOT NULL,\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id) \n" +
            ");" ;

    // create get_salary
    public static final String CREATE_GET_SALARY = "CREATE TABLE Get_Salary (\n" +
            "    id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    date       TEXT    NOT NULL,\n" +
            "    salary     REAL    NOT NULL,\n" +
            "    bonus      REAL    NOT NULL,\n" +
            "    sum        REAL    NOT NULL,\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id) \n" +
            ");\n";
    // create leave
    public static final String CREATE_LEAVE = "CREATE TABLE Leave (\n" +
            "    leave_id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    start_date TEXT    NOT NULL,\n" +
            "    end_date   TEXT    NOT NULL,\n" +
            "    reason     TEXT,\n" +
            "    status     TEXT    NOT NULL,\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id) \n" +
            ");";

    // create role
    public static final String CREATE_ROLE = "CREATE TABLE IF NOT EXISTS `Role` (\n" +
            "    role_id INTEGER PRIMARY KEY AUTOINCREMENT\n" +
            "                    NOT NULL,\n"+
            "    role_name TEXT NOT NULL\n" +
            ");";

    // create role_account
    public static final String CREATE_ROLE_ACCOUNT = "CREATE TABLE Role_Account (\n" +
            "    role_id    INTEGER NOT NULL\n" +
            "                       REFERENCES Role (role_id),\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id),\n" +
            "    PRIMARY KEY (\n" +
            "        role_id,\n" +
            "        account_id\n" +
            "    ),\n" +
            "    FOREIGN KEY (\n" +
            "        role_id\n" +
            "    )\n" +
            "    REFERENCES Role (role_id),\n" +
            "    FOREIGN KEY (\n" +
            "        account_id\n" +
            "    )\n" +
            "    REFERENCES Account (account_id) \n" +
            ");";

    // create account
    public static final String CREATE_ACCOUNT = "CREATE TABLE Account (\n" +
            "    account_id INTEGER PRIMARY KEY AUTOINCREMENT\n" +
            "                       NOT NULL,\n" +
            "    username   TEXT    NOT NULL,\n" +
            "    password   TEXT    NOT NULL,\n" +
            "    full_name  TEXT    NOT NULL,\n" +
            "    email      TEXT    NOT NULL,\n" +
            "    phone      TEXT,\n" +
            "    address    TEXT,\n" +
            "    room_id    INTEGER REFERENCES Room (room_id) \n" +
            ");";


    // INIT INDEX
//    public static final String CREATE_INDEX_ROLE_ID = "CREATE INDEX IF NOT EXISTS role_account_role_id_foreign ON Role_Account (role_id);";
//    public static final String CREATE_INDEX_ACCOUNT_ID = "CREATE INDEX IF NOT EXISTS role_account_account_id_foreign ON Role_Account (account_id);";

//    FOREIGN KEY
//    public static final String FOREIGN_KEY_ACCOUNT_WITH_ROOM_ID = "CREATE TABLE IF NOT EXISTS account_room_id_foreign (\n" +
//        "    FOREIGN KEY (room_id) REFERENCES Room(room_id)\n" +
//        ");";
//    public static final String FOREIGN_KEY_GET_SALARY_WITH_ACCOUNT_ID = "CREATE TABLE IF NOT EXISTS get_salary_account_id_foreign (\n" +
//            "    FOREIGN KEY (account_id) REFERENCES Account(account_id)\n" +
//            ");";
//    public static final String FOREIGN_KEY_ATTENDANCE_WITH_ACCOUNT_ID = "CREATE TABLE IF NOT EXISTS attendance_account_id_foreign (\n" +
//            "    FOREIGN KEY (account_id) REFERENCES Account(account_id)\n" +
//            ");";
//    public static final String FOREIGN_KEY_LEAVE_WITH_ACCOUNT_ID = "CREATE TABLE IF NOT EXISTS leave_account_id_foreign (\n" +
//            "    FOREIGN KEY (account_id) REFERENCES Account(account_id)\n" +
//            ");";
//    public static final String FOREIGN_KEY_SALARY_WITH_ACCOUNT_ID = "CREATE TABLE IF NOT EXISTS salary_account_id_foreign (\n" +
//            "    FOREIGN KEY (account_id) REFERENCES Account(account_id)\n" +
//            ");";
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void query (String SQL){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL);
    }

    public Cursor getData(String SQL){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(SQL,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            //create table
            db.execSQL(CREATE_BONUS);
            db.execSQL(CREATE_ACCOUNT);
            db.execSQL(CREATE_ROLE_ACCOUNT);
            db.execSQL(CREATE_ATTENDANCE);
            db.execSQL(CREATE_LEAVE);
            db.execSQL(CREATE_SALARY);
            db.execSQL(CREATE_GET_SALARY);
            db.execSQL(CREATE_ROOM);
            db.execSQL(CREATE_ROLE);

            // create index
//            db.execSQL(CREATE_INDEX_ACCOUNT_ID);
//            db.execSQL(CREATE_INDEX_ROLE_ID);
//
//            // foreign key

//            db.execSQL(FOREIGN_KEY_ACCOUNT_WITH_ROOM_ID);
//            db.execSQL(FOREIGN_KEY_ATTENDANCE_WITH_ACCOUNT_ID);
//            db.execSQL(FOREIGN_KEY_SALARY_WITH_ACCOUNT_ID);
//            db.execSQL(FOREIGN_KEY_GET_SALARY_WITH_ACCOUNT_ID);
//            db.execSQL(FOREIGN_KEY_LEAVE_WITH_ACCOUNT_ID);
        }catch (Exception e){
            Log.e("Error","table exit");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
