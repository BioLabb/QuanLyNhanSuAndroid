package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.GetSalary;
import com.example.quanlynhansu.object.Leave;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class GetSalaryHelper {

    private static final String TABLE_GET_SALARY = "Get_salary";
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase DB;

    private SQL dbHelper;

    public GetSalaryHelper(Context context){
        sqLiteHelper = new SQLiteHelper(context);
        dbHelper =  new SQL(context);
        DB = sqLiteHelper.getWritableDatabase();
        onCreate(DB);
    }

    public void onCreate(SQLiteDatabase database){
        //kiểm tra nếu không có dữ liệu trong bản hoặc chưa tạo bản thì chạy


        if (!dbHelper.isTableInitialized(database, TABLE_GET_SALARY))
        {
            insert(new GetSalary("1/1/1970", 5000, 100,0,1 ));
            insert(new GetSalary("1/1/1970", 1000, 700,0,2 ));
            insert(new GetSalary("1/1/1970", 4000, 200,0,3 ));
            insert(new GetSalary("1/2/1970", 2000, 300,0,4 ));

        }
    }
     public int insert(GetSalary getSalary){
         ContentValues values = new ContentValues();

//         values.put("id",getSalary.getId());
         values.put("date", getSalary.getStrDate());
         values.put("salary", getSalary.getSalary());
         values.put("bonus",getSalary.getBonus());
         values.put("sum",getSalary.getSum());
         values.put("account_id",getSalary.getAccountID());

         long result =  DB.insert(TABLE_GET_SALARY,null,values);
         if(result < 0)
             return -1;
         return 1;
     }

     public GetSalary getSalary(int id) throws ParseException {
        String SQL_SELECT = String.format("SELECT * FROM %s WHERE ID = %d",TABLE_GET_SALARY,id);

        Cursor cursor = DB.rawQuery(SQL_SELECT,null );

        if(cursor.isFirst()){
            // Chuyển chuỗi thành calender

            GetSalary getSalary = new GetSalary(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
            );
            return getSalary;
        }
        return null;
     }

     public List<GetSalary> getSalaryList() {
        //
        String SQL_SELECT = String.format("SELECT * FROM %s ",TABLE_GET_SALARY);
        List<GetSalary> getSalaryList = new ArrayList<>();

        Cursor cursor = DB.rawQuery(SQL_SELECT,null);
        cursor.moveToFirst();

         //calendar.setTime(format.parse("01/01/2023"));


         //
        while (cursor.isAfterLast() == false){
            GetSalary getSalary = new GetSalary(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
            );

            getSalaryList.add(getSalary);
            cursor.moveToNext();
        }

        return getSalaryList;
     }

     public double totalGetSalary(){
        double totalSum = 0;
        String query = "SELECT * FROM " + TABLE_GET_SALARY;
        Cursor cursor = DB.rawQuery(query,null);

        cursor.moveToFirst();
         while (cursor.isAfterLast() == false){
             totalSum += cursor.getDouble(4);
             cursor.moveToNext();
             cursor.moveToNext();
         }

         return totalSum;

     }

    public GetSalary getGetSalaryByIdUser(int accountId) throws ParseException {
        String query = "SELECT * FROM " + TABLE_GET_SALARY + " WHERE account_id = " + accountId;

        Cursor cursor = DB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            calendar.setTime(format.parse(cursor.getString(1)));

            GetSalary getSalary = new GetSalary(
                    cursor.getInt(0),
                    calendar,
                    cursor.getDouble(2),
                    cursor.getDouble(3),
                    cursor.getDouble(4),
                    cursor.getInt(5)
            );
            return getSalary;
        }
        return null;
    }

     public int remove(int id){
        String where = "id = ?";
        String whereArgs = String.valueOf(id);

        int result = DB.delete(TABLE_GET_SALARY,where, new String[]{whereArgs});
        if(result < 1)
            return -1;
        return 1;
     }

     public int update(GetSalary getSalary){
        ContentValues values = new ContentValues();

         values.put("id",getSalary.getId());
         values.put("date", getSalary.getStrDate());
         values.put("bonus",getSalary.getBonus());
         values.put("sum",getSalary.getSum());
         values.put("account_id",getSalary.getAccountID());
         values.put("salary", getSalary.getSalary());
        int result =  DB.update(TABLE_GET_SALARY,values,null,null);
        if(result < 1)
            return -1;
        return 1;
     }
}
