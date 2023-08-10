package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Role;

import java.util.ArrayList;
import java.util.List;

public class AccountHelper {
    private Context context;
    private final String TABLE_ACCOUNT = "Account";
    private final String ACCOUNT_ID = "account_id";
    SQLiteHelper sqLiteHelper;
    private SQL dbHelper;
    SQLiteDatabase Data;

    public AccountHelper(Context context){
        sqLiteHelper = new SQLiteHelper(context);
        dbHelper =  new SQL(context);
        Data = sqLiteHelper.getWritableDatabase();
        onCreate(Data);
    }

    public void onCreate(SQLiteDatabase database){
        //kiểm tra nếu không có dữ liệu trong bản hoặc chưa tạo bản thì chạy


        if (!dbHelper.isTableInitialized(database, TABLE_ACCOUNT))
        {
            insertAccount(new Account("admin","12345","vanchien","email11","01424234","adress",1));
            insertAccount(new Account("use1","12345","vanchien","email11","01424234","adress",1));
            insertAccount(new Account("useName2","12345","HaiMai","email12","01424234","adress",2));
            insertAccount(new Account("useName3","12345","KimThien","email13","01424234","adress",3));

        }
    }

    public int insertAccount(Account account){
        ContentValues values = new ContentValues();
        values.put("username",account.getUserName());
        values.put("password",account.getPassWord());
        values.put("full_name",account.getFullName());
        values.put("email", account.getEmail());
        values.put("phone",account.getPhone());
        values.put("address", account.getAddress());
        values.put("room_id",account.getRoomID());

        // insert khong thanh cong thi return -1 nguoc lai return 1
        long result = Data.insert(TABLE_ACCOUNT,null,values);


        if(result < 0)
            return -1;
        return 1;
    }

    public Account getAccount(int id){
        String SQL_SELECT = String.format("SELECT * FROM %s WHERE account_id = %d",TABLE_ACCOUNT,id);
        Cursor cursor = Data.rawQuery(SQL_SELECT,null);

        if(cursor.moveToFirst()){
            Account account = new Account(
                cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)


            );
            return  account;
        }
        return null;
    }

    public Account getAccount(String userName){
        String SQL_SELECT = String.format("SELECT * FROM %s WHERE username = '%s'",TABLE_ACCOUNT,userName);
        Cursor cursor = Data.rawQuery(SQL_SELECT,null);

        if(cursor.moveToFirst()){
            Account account = new Account(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)


            );
            return  account;
        }
        return null;
    }


    public List<Account> getAllAccounts(){
        String SQL_SELECT = String.format("SELECT * FROM %s",TABLE_ACCOUNT);
        List<Account> accountList = new ArrayList<>();

        Cursor cursor = Data.rawQuery(SQL_SELECT,null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false){
            Account account = new Account(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7)
            );
            cursor.moveToNext();
            accountList.add(account);
        }

        return accountList;
    }
    public List<Account> getAllAccountsChien(){
            String SQL_SELECT = String.format("SELECT * FROM %s", TABLE_ACCOUNT);
            List<Account> accountList = new ArrayList<>();

            Cursor cursor = Data.rawQuery(SQL_SELECT, null);

            if (cursor.moveToFirst()) {
                do {
                    Account account = new Account(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getInt(7)
                    );
                    accountList.add(account);
                } while (cursor.moveToNext());
            }

            cursor.close();

            return accountList;
        }




    public int removeAccount(int id){
        int result = Data.delete(TABLE_ACCOUNT,"account_id + ?",new String[]{String.valueOf(id)});
        Data.close();
        if(result == 1)
            return 1;
        return 0;
    }

    public int updateAccount(Account account){
        ContentValues values = new ContentValues();
        values.put("username",account.getUserName());
        values.put("password",account.getPassWord());
        values.put("full_name",account.getFullName());
        values.put("email", account.getEmail());
        values.put("phone",account.getPhone());
        values.put("address", account.getAddress());
        values.put("room_id",account.getRoomID());

        String whereClause = "account_id = ?";
        String[] whereArgs = {String.valueOf(account.getAccountID())};



        int result =  Data.update(TABLE_ACCOUNT,values,whereClause, whereArgs);


        if(result < 0)
            return -1;
        return 1;
    }


    // return the number of account
    public int getCountAccount(){
        String sqlCount = String.format("SELECT COUNT(%s) FROM %s",ACCOUNT_ID,TABLE_ACCOUNT);

        // truy vấn dữ liệu từ data vao cursor
        Cursor cursor = Data.rawQuery(sqlCount,null);
        cursor.moveToFirst();
        int countAccount = cursor.getInt(0);

        return countAccount;
    }

}
