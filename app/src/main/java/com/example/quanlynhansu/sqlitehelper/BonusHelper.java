
package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Bonus;

import java.util.ArrayList;
import java.util.List;

public class BonusHelper {
    private Context context;
    public static final String TABLE_BONUS = "Bonus";

    private SQL q;
    private final SQLiteDatabase database;

    public BonusHelper(Context context) {
        // Tạo cơ sở dữ liệu
        q = new SQL(context);
        // Tương tác với dữ liệu
        // Cho ghi và đọc
        database = q.getWritableDatabase();
    }

    public int insertBonus(double condition, double bonus) {
        ContentValues values = new ContentValues();
        values.put("condition", condition);
        values.put("bonus", bonus);
        long statusInsert = database.insert(TABLE_BONUS, null, values);
        if (statusInsert < 0)
            return 0; // Thêm thất bại
        return 1; // Thêm thành công
    }
    public int insertBonus(Bonus bonus) {
        ContentValues values = new ContentValues();
        values.put("condition", bonus.getCondition());
        values.put("bonus", bonus.getBonus());
        long statusInsert = database.insert(TABLE_BONUS, null, values);
        if (statusInsert < 0)
            return 0; // Thêm thất bại
        return 1; // Thêm thành công
    }

    public List<Bonus> getAllBonuses() {
        List<Bonus> bonusList = new ArrayList<>();
        String SQL = "SELECT * FROM " + TABLE_BONUS;

        Cursor cursor = database.rawQuery(SQL, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Bonus bonus = new Bonus(cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2));
            bonusList.add(bonus);
            cursor.moveToNext();
        }
        return bonusList;
    }

    public Bonus getBonus(int bonusId) {
        String SQL = "SELECT * FROM " + TABLE_BONUS + " WHERE bonus_id = " + bonusId;

        Cursor cursor = q.getData(SQL);
        if (cursor.moveToFirst()) {
            Bonus bonus = new Bonus(cursor.getInt(0), cursor.getDouble(1), cursor.getDouble(2));
            return bonus;
        }
        return null;
    }

    public int removeBonus(int bonusId) {
        String SQL = "DELETE FROM " + TABLE_BONUS + " WHERE bonus_id = " + bonusId;
        q.query(SQL);

        if (getBonus(bonusId) == null)
            return 1;
        else
            return 0;
    }

    public int updateBonus(Bonus bonus) {
        ContentValues values = new ContentValues();
        values.put("condition", bonus.getCondition());
        values.put("bonus", bonus.getBonus());

        String whereClause = "bonus_id = ?";
        String[] whereArgs = {String.valueOf(bonus.getBonusId())};

        int rowsAffected = database.update(TABLE_BONUS, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }
}
