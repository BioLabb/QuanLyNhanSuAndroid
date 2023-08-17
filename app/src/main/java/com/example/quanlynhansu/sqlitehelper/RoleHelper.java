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
    private final String ADMIN = "Admin";
    private final String USER = "User";

    public final static String TABLE_ROLE = "Role";

    private SQL dbHelper;
    private final SQLiteDatabase database;

    public RoleHelper(Context context) {
        this.context = context;
        dbHelper = new SQL(context);
        database = dbHelper.getWritableDatabase();
        onCreate(database);
    }
    public void onCreate(SQLiteDatabase database){
        //kiểm tra nếu không có dữ liệu trong bản hoặc chưa tạo bản thì chạy
        if (!dbHelper.isTableInitialized(database, TABLE_ROLE))
        {
            insertRole(new Role("Admin"));
            insertRole(new Role("User"));
            insertRole(new Role("Management"));
        }
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

    //
    public Boolean isAdmin(int AccountID){
        String sql = String.format("SELECT ROLE.role_name\n" +
                "FROM ACCOUNT, ROLE, ROLE_ACCOUNT\n" +
                "WHERE ACCOUNT.ACCOUNT_ID = ROLE_ACCOUNT.account_id AND ACCOUNT.ACCOUNT_ID = %d AND ROLE.role_id = ROLE_ACCOUNT.role_id",AccountID);

        Cursor cursor = database.rawQuery(sql,null);
        if(cursor.moveToFirst() == false)
            return false;

        String role = cursor.getString(0);
        System.out.println("isaaa" + role);
        return role.equalsIgnoreCase(USER) ? false:true ;

    }
}

