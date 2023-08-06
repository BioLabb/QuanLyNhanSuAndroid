package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.GetSalary;
import com.example.quanlynhansu.object.Salary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SalaryHelper {
    private Context context;
    private static final String TABLE_SALARY = "Salary";
    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase DB;

    public SalaryHelper(Context context){
        this.context = context;
        sqLiteHelper = new SQLiteHelper(context);
        DB = sqLiteHelper.getWritableDatabase();
    }

    public int insert(Salary Salary){
        ContentValues values = new ContentValues();

        values.put("id",Salary.getId());
        values.put("amount", Salary.getAmount());
        values.put("date",Salary.getDate());
        values.put("account_id",Salary.getAccount_id());

        long result =  DB.insert(TABLE_SALARY,null,values);
        if(result < 0)
            return -1;
        return 1;
    }

    public Salary getSalary(int id) throws ParseException {
        String SQL_SELECT = String.format("SELECT * FROM %s WHERE ID = %d",TABLE_SALARY,id);

        Cursor cursor = DB.rawQuery(SQL_SELECT,null );

        if(cursor.isFirst()){
            // Chuyển chuỗi thành calender
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            calendar.setTime(format.parse(cursor.getString(1)));

            Salary getSalary = new Salary(
                    cursor.getInt(0),
                    cursor.getDouble(1),
                    calendar,
                    cursor.getInt(3)
            );
            return getSalary;
        }
        return null;
    }

    public List<Salary> SalaryList() throws ParseException {
        //
        String SQL_SELECT = String.format("SELECT * FROM %s ",TABLE_SALARY);
        List<Salary> SalaryList = new ArrayList<>();

        Cursor cursor = DB.rawQuery(SQL_SELECT,null);
        cursor.moveToFirst();

        //
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        calendar.setTime(format.parse(cursor.getString(1)));

        //
        while (cursor.isAfterLast() == false){
            Salary Salary = new Salary(
                    cursor.getInt(0),
                    cursor.getDouble(1),
                    calendar,
                    cursor.getInt(3)
            );

            SalaryList.add(Salary);
        }

        return SalaryList;
    }

    public int remove(int id){
        String where = "id = ?";
        String whereArgs = String.valueOf(id);

        int result = DB.delete(TABLE_SALARY,where, new String[]{whereArgs});
        if(result < 1)
            return -1;
        return 1;
    }

    public int update(Salary Salary){
        ContentValues values = new ContentValues();

        values.put("id",Salary.getId());
        values.put("amount", Salary.getAmount());
        values.put("date",Salary.getDate());
        values.put("account_id",Salary.getAccount_id());

        int result =  DB.update(TABLE_SALARY,values,null,null);
        if(result < 1)
            return -1;
        return 1;
    }
}
