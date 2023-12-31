package com.example.quanlynhansu.sqlitehelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class SQL extends SQLiteHelper{

    public SQL(@Nullable Context context) {
        super(context);
    }

    public void query (String SQL){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(SQL);
    }

    public  Cursor getData(String SQL){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(SQL,null);
    }
    public boolean isTableInitialized(SQLiteDatabase db, String tableName) {
        // Kiểm tra xem bảng đã được khởi tạo hay chưa
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        boolean isInitialized = cursor.getCount() > 0;
        return isInitialized;
    }
}
