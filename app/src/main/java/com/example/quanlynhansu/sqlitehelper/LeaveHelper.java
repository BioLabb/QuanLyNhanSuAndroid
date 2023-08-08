package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.object.Leave;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveHelper {
    private Context context;
    public final static String TABLE_LEAVE = "Leave";

    private SQL dbHelper;
    private final SQLiteDatabase database;

    public LeaveHelper(Context context) {
        this.context = context;
        dbHelper = new SQL(context);
        database = dbHelper.getWritableDatabase();
    }

    public int insertLeave(Leave leave) {
        ContentValues values = new ContentValues();
        values.put("start_date", leave.getStartDate());
        values.put("end_date", leave.getEndDate());
        values.put("reason", leave.getReason());
        values.put("status", leave.getStatus());
        values.put("account_id", leave.getAccountId());

        long statusInsert = database.insert(TABLE_LEAVE, null, values);
        if (statusInsert < 0) {
            return 0; // insert thất bại
        }
        return 1; // insert thành công
    }

    public Leave getLeave(int leaveId) {
        String query = String.format("SELECT * FROM %s WHERE leave_id = %d", TABLE_LEAVE, leaveId);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            Leave leave = new Leave(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            return leave;
        }

        return null;
    }

    public Leave getLeaveByIdUser(int accountId) {
        String query = "SELECT * FROM " + TABLE_LEAVE + " WHERE account_id = " + accountId;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Leave leave = new Leave(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );
            return leave;
        }
        return null;
    }

    public List<Leave> getAllLeaveByIdUser(int accountId) {
        List<Leave> leaveList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_LEAVE + " WHERE account_id = " + accountId;

        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Leave leave = new Leave(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                leaveList.add(leave);
            } while (cursor.moveToNext());
        }

        return leaveList;
    }

    public List<Leave> getAllLeaveByStatus(String status){
        List<Leave> leaveList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE status = '%s'",TABLE_LEAVE, status);
        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                Leave leave = new Leave(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                leaveList.add(leave);
            } while (cursor.moveToNext());
        }

        return leaveList;
    }

    public List<Leave> getAllLeaves() {
        List<Leave> leaveList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_LEAVE);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Leave leave = new Leave(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                leaveList.add(leave);
            } while (cursor.moveToNext());
        }

        return leaveList;
    }
    public List<Leave> getAllLeavesReverse() {
        List<Leave> leaveList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_LEAVE);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToLast()) {
            do {
                Leave leave = new Leave(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)
                );
                leaveList.add(leave);
            } while (cursor.moveToPrevious());
        }

        cursor.close();
        return leaveList;
    }


    public int updateLeave(Leave leave) {
        ContentValues values = new ContentValues();
        values.put("start_date", leave.getStartDate());
        values.put("end_date", leave.getEndDate());
        values.put("reason", leave.getReason());
        values.put("status", leave.getStatus());
        values.put("account_id", leave.getAccountId());

        String whereClause = "leave_id = ?";
        String[] whereArgs = {String.valueOf(leave.getLeaveId())};

        int rowsAffected = database.update(TABLE_LEAVE, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int deleteLeave(int leaveId) {
        String whereClause = "leave_id = ?";
        String[] whereArgs = {String.valueOf(leaveId)};

        int rowsAffected = database.delete(TABLE_LEAVE, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Xóa thất bại
        }
        return 1; // Xóa thành công
    }

    //tinh so ngay giua hai khoang time
    public long totalLeaveDate(String start,String end){
        long result = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date startDate = simpleDateFormat.parse(start);
            Date endDate = simpleDateFormat.parse(end);

            long startValue = startDate.getTime();
            long endValue= endDate.getTime();

            long tmp = Math.abs(endValue - startValue);
            result = tmp/(24*60*60*1000);

        }catch (ParseException e){
            e.printStackTrace();
        }
        return result;
    }

    public long totalAllLeaveDate(int accountId) {
        String query = "SELECT * " +
                "FROM " + TABLE_LEAVE +
                " WHERE account_id = " + accountId;

        Cursor cursor = database.rawQuery(query, null);
        long result = 0;

        if (cursor.moveToFirst()) {
            do {
                //duyet qua cac item va cong tong so ngay da duoc xet duyet
                if(cursor.getString(4).equals("da duyet"))
                    result += totalLeaveDate(cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public long totalUnapprovedDay(int accountId){
        String query = "SELECT * " +
                "FROM " + TABLE_LEAVE +
                " WHERE account_id = " + accountId;

        Cursor cursor = database.rawQuery(query, null);
        long result = 0;

        if (cursor.moveToFirst()) {
            do {
                //duyet qua cac item va cong tong so ngay da duoc xet duyet
                if(!cursor.getString(4).equals("da duyet"))
                    result += totalLeaveDate(cursor.getString(1), cursor.getString(2));
            } while (cursor.moveToNext());
        }
        return result;
    }
}
