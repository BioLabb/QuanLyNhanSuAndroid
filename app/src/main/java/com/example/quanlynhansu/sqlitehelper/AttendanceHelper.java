package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.object.Leave;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceHelper {
    private Context context;
    public static final String TABLE_ATTENDANCE = "Attendance";
    public static final String ATTENDANCE_ID = "attendance_id";

    private SQL q;

    private SQLiteHelper sqLiteHelper;
    private final SQLiteDatabase database;

    public AttendanceHelper(Context context){
        sqLiteHelper = new SQLiteHelper(context);
        q =  new SQL(context);
        database = sqLiteHelper.getWritableDatabase();
        onCreate(database);
    }

    public void onCreate(SQLiteDatabase database){
        //kiểm tra nếu không có dữ liệu trong bản hoặc chưa tạo bản thì chạy
        if (!q.isTableInitialized(database, TABLE_ATTENDANCE))
        {
            insertAttendance(new Attendance("1/1/2024",1));
            insertAttendance(new Attendance("1/1/2024",2));
            insertAttendance(new Attendance("1/1/2024",3));
            insertAttendance(new Attendance("2/1/2024",4));
            insertAttendance(new Attendance("2/1/2024",1));
            insertAttendance(new Attendance("3/1/2024",2));
            insertAttendance(new Attendance("4/1/2024",3));
            insertAttendance(new Attendance("4/1/2024",1));
            insertAttendance(new Attendance("4/1/2024",4));
        }
    }

    public int insertAttendance(String date, int accountId) {
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("account_id", accountId);
        long statusInsert = database.insert(TABLE_ATTENDANCE, null, values);
        if (statusInsert < 0)
            return 0; // Thêm thất bại
        return 1; // Thêm thành công
    }
    public int insertAttendance(Attendance attendance) {
        ContentValues values = new ContentValues();
        values.put("date", attendance.getDate());
        values.put("account_id", attendance.getAccountId());
        long statusInsert = database.insert(TABLE_ATTENDANCE, null, values);
        if (statusInsert < 0)
            return 0; // Thêm thất bại
        return 1; // Thêm thành công
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> attendanceList = new ArrayList<>();
        String SQL = "SELECT * FROM " + TABLE_ATTENDANCE;

        Cursor cursor = database.rawQuery(SQL, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Attendance attendance = new Attendance(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
            attendanceList.add(attendance);
            cursor.moveToNext();
        }
        return attendanceList;
    }

    public List<Attendance> getAllAttendanceByIdUser(int accountId) {
        List<Attendance> attendanceList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ATTENDANCE + " WHERE account_id = " + accountId;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Attendance attendance = new Attendance(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2)
            );
            attendanceList.add(attendance);
            cursor.moveToNext();
        }
        return attendanceList;
    }

    public Attendance getAttendance(int attendanceId) {
        String SQL = "SELECT * FROM " + TABLE_ATTENDANCE + " WHERE attendance_id = " + attendanceId;

        Cursor cursor = q.getData(SQL);
        if (cursor.moveToFirst()) {
            Attendance attendance = new Attendance(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            return attendance;
        }
        return null;
    }

    public Attendance getAttendanceByIdUser(int accountId) {
        String SQL = "SELECT * FROM " + TABLE_ATTENDANCE + " WHERE account_id = " + accountId;

        Cursor cursor = q.getData(SQL);
        if (cursor.moveToFirst()) {
            Attendance attendance = new Attendance(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            return attendance;
        }
        return null;
    }

    public int removeAttendance(int attendanceId) {
        String SQL = "DELETE FROM " + TABLE_ATTENDANCE + " WHERE attendance_id = " + attendanceId;
        q.query(SQL);

        if (getAttendance(attendanceId) == null)
            return 1;
        else
            return 0;
    }

    public int updateAttendance(Attendance attendance) {
        ContentValues values = new ContentValues();
        values.put("date", attendance.getDate());
        values.put("account_id", attendance.getAccountId());

        String whereClause = "attendance_id = ?";
        String[] whereArgs = {String.valueOf(attendance.getAttendanceId())};

        int rowsAffected = database.update(TABLE_ATTENDANCE, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int totalAllAttendance(int accountId) {
        String SQL = "SELECT * " +
                     "FROM " + TABLE_ATTENDANCE +
                     " WHERE account_id = " + accountId;

        Cursor cursor = q.getData(SQL);
        if (cursor.moveToFirst() && cursor.getString(1) != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int getCountAttendance(){
        String sqlCount = String.format("SELECT COUNT(%s) FROM %s",ATTENDANCE_ID,TABLE_ATTENDANCE);

        Cursor cursor =  database.rawQuery(sqlCount,null);
        if(cursor.moveToFirst()){
            int resultCount = cursor.getInt(0);
            return resultCount;
        }
        return 0;
    }
}
