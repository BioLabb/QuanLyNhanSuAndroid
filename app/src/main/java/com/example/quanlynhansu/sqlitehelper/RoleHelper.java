package com.example.quanlynhansu.sqlitehelper;

import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.example.quanlynhansu.object.Role;


import java.util.ArrayList;
import java.util.List;

public class RoleHelper {
    private Context context;
    public final static String TABLE_ROLE = "Role";

    private SQL dbHelper;
    private final SQLiteDatabase database;

    public RoleHelper(Context context) {
        this.context = context;
        dbHelper = new SQL(context);
        database = dbHelper.getWritableDatabase();
    }

    public int insertRole(Role role) {
        ContentValues values = new ContentValues();
        values.put("role_name", role.getRoleName());

        long statusInsert = database.insert(TABLE_ROLE, null, values);
        if (statusInsert < 0) {
            return 0; // insert thất bại
        }
        return 1; // insert thành công
    }

    public Role getRole(int roleId) {
        String query = String.format("SELECT * FROM %s WHERE role_id = %d", TABLE_ROLE, roleId);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            Role role = new Role(
                    cursor.getInt(0),
                    cursor.getString(1)
            );
            return role;
        }

        return null;
    }

    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", TABLE_ROLE);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Role role = new Role(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
                roleList.add(role);
            } while (cursor.moveToNext());
        }

        return roleList;
    }

    public int updateRole(Role role) {
        ContentValues values = new ContentValues();
        values.put("role_name", role.getRoleName());

        String whereClause = "role_id = ?";
        String[] whereArgs = {String.valueOf(role.getRoleId())};

        int rowsAffected = database.update(TABLE_ROLE, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int deleteRole(int roleId) {
        String whereClause = "role_id = ?";
        String[] whereArgs = {String.valueOf(roleId)};

        int rowsAffected = database.delete(TABLE_ROLE, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Xóa thất bại
        }
        return 1; // Xóa thành công
    }
}

