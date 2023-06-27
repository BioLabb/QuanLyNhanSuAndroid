package com.example.quanlynhansu.sqlitehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QuanLyNhanSu.db";
    //old version
//    public static final int DB_VERSION = 1;
    // new version
    public static final int DB_VERSION = 3;

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
            "    date          NUMERIC   NOT NULL,\n" +
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
            "    date       NUMERIC    NOT NULL,\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id) \n" +
            ");" ;

    // create get_salary
    public static final String CREATE_GET_SALARY = "CREATE TABLE Get_Salary (\n" +
            "    id         INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    date       NUMERIC    NOT NULL,\n" +
            "    salary     REAL    NOT NULL,\n" +
            "    bonus      REAL    NOT NULL,\n" +
            "    sum        REAL    NOT NULL,\n" +
            "    account_id INTEGER NOT NULL\n" +
            "                       REFERENCES Account (account_id) \n" +
            ");\n";
    // create leave
    public static final String CREATE_LEAVE = "CREATE TABLE Leave (\n" +
            "    leave_id   INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    start_date NUMERIC NOT NULL,\n" +
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
            "    password   TEXT    NOT NULL UNIQUE ,\n" +
            "    full_name  TEXT    NOT NULL,\n" +
            "    email      TEXT    NOT NULL,\n" +
            "    phone      TEXT,\n" +
            "    address    TEXT,\n" +
            "    room_id    INTEGER REFERENCES Room (room_id) \n" +
            ");";


    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void query (String SQL){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL);
    }

    public Cursor getData(String SQL){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(SQL,null);
    }


    // được go nếu chưa có file DATA_NAME
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
        }catch (Exception e){
            Log.e("Error","table exit");
        }
    }


    // ĐƯỢC GỌI KHI CÓ VERSION MỚI
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP = "DROP TABLE IF EXISTS ";

        db.execSQL(SQL_DROP + "BONUS" );
        db.execSQL(SQL_DROP + "ACCOUNT");
        db.execSQL(SQL_DROP + "ROLE_ACCOUNT");
        db.execSQL(SQL_DROP + "ATTENDANCE");
        db.execSQL(SQL_DROP + "LEAVE");
        db.execSQL(SQL_DROP + "SALARY");
        db.execSQL(SQL_DROP + "GET_SALARY");
        db.execSQL(SQL_DROP + "ROOM");
        db.execSQL(SQL_DROP + "ROLE");

        onCreate(db);

    }
}
