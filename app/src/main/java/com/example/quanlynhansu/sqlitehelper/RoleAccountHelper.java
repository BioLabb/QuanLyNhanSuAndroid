package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.RoleAccount;

import java.util.ArrayList;
import java.util.List;


public class RoleAccountHelper {
    private Context context;
    public final static String TABLE_ROLE_ACCOUNT = "Role_Account";

    private SQL dbHelper;
    private final SQLiteDatabase database;
    RoleHelper roleHelper;

    public RoleAccountHelper(Context context) {
        this.context = context;
        dbHelper = new SQL(context);
        database = dbHelper.getWritableDatabase();
        roleHelper = new RoleHelper(context);
        onCreate(database);
    }
    public void onCreate(SQLiteDatabase database){
       if(!dbHelper.isTableInitialized(database,TABLE_ROLE_ACCOUNT)){
           insertRoleAccount(1,1);
           insertRoleAccount(2,2);
           insertRoleAccount(3,3);
       }

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
    public List<RoleAccount> getAllRoleAccounts() {
        List<RoleAccount> roleAccountList = new ArrayList<>();

        String query = String.format("SELECT * FROM %s", TABLE_ROLE_ACCOUNT);
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            int roleIdIndex = cursor.getColumnIndex("role_id");
            int accountIdIndex = cursor.getColumnIndex("account_id");

            do {
                int roleId = cursor.getInt(roleIdIndex);
                int accountId = cursor.getInt(accountIdIndex);

                RoleAccount roleAccount = new RoleAccount(roleId, accountId);
                roleAccountList.add(roleAccount);
            } while (cursor.moveToNext());
        }



        return roleAccountList;
    }
    public String searchRoleUser(int idUser){
        List<RoleAccount> roleAccountList = getAllRoleAccounts();
        List<Role> roles =  roleHelper.getAllRoles();

        for(RoleAccount roleAccount: roleAccountList){
            if(roleAccount.getAccountId() == idUser) {
                Log.d("getAllRoleAccounts" , String.valueOf(roleAccount));
                for(Role role: roles){
                    if(role.getRoleId() == roleAccount.getRoleId()) {
                        return role.getRoleName();
                    }
                }
            }
        }
        return "Chưa xét";
    }

}
