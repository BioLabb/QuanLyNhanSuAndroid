package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Attendance;

import java.util.ArrayList;
import java.util.List;

public class AttendanceHelper {
    private Context context;
    public static final String TABLE_ATTENDANCE = "Attendance";

    private SQL q;
    private final SQLiteDatabase database;

    public AttendanceHelper(Context context) {
        // Tạo cơ sở dữ liệu
        q = new SQL(context);
        // Tương tác với dữ liệu
        // Cho ghi và đọc
        database = q.getWritableDatabase();
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
            Attendance attendance = new Attendance(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
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
        if (cursor.getCount() > 0) {
            return cursor.getCount();
        }
        return 0;
    }

}
