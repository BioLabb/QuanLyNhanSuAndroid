package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RoleAccountHelper {
    private Context context;
    public final static String TABLE_ROLE_ACCOUNT = "Role_Account";

    private SQL dbHelper;
    private final SQLiteDatabase database;

    public RoleAccountHelper(Context context) {
        this.context = context;
        dbHelper = new SQL(context);
        database = dbHelper.getWritableDatabase();
    }

    public int insertRoleAccount(int roleId, int accountId) {
        ContentValues values = new ContentValues();
        values.put("role_id", roleId);
        values.put("account_id", accountId);

        long statusInsert = database.insert(TABLE_ROLE_ACCOUNT, null, values);
        if (statusInsert < 0) {
            return 0; // insert thất bại
        }
        return 1; // insert thành công
    }


    public int deleteRoleAccount(int roleId, int accountId) {
        String whereClause = "role_id = ? AND account_id = ?";
        String[] whereArgs = {String.valueOf(roleId), String.valueOf(accountId)};

        int rowsAffected = database.delete(TABLE_ROLE_ACCOUNT, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Xóa thất bại
        }
        return 1; // Xóa thành công
    }
}
