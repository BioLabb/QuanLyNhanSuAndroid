package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomHelper {
    private Context context;
    public final static String TABLE_ROOM = "Room";
    private SQLiteHelper sqLiteHelper;
    private final SQLiteDatabase Database;

    public RoomHelper(Context context){
        sqLiteHelper = new SQLiteHelper(context);
        Database = sqLiteHelper.getWritableDatabase();
    }

    public int insertRoom(Room room){
        ContentValues values = new ContentValues();
        values.put("room_id",room.getRoomID()) ;
        values.put("name",room.getName());
        values.put("number",room.getNumber());
        long statusInsert = Database.insert(TABLE_ROOM,null,values);
        Database.close();
        if(statusInsert < 0)
            return 0; // insert that bai
        return 1; // insert thanh cong
    }

    public Room getRoom(int RoomID) {
        String SQL = String.format("SELECT * FROM %s WHERE room_id == %d ", TABLE_ROOM,RoomID);
        Cursor cursor = sqLiteHelper.getData(SQL);
        if(cursor.moveToFirst()){
            Room room = new Room(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            return room;
        }
        return null;
    }

    public List<Room> getAllRooms(){
        List<Room> roomList = new ArrayList<>();
        String SQL = "SELECT * FROM ROOM";

        Cursor cursor = Database.rawQuery(SQL,null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            Room room = new Room(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));

            roomList.add(room);

            cursor.moveToNext();
        }
        return roomList;
    }


    public void removeRoom(int id){
        String SQL = String.format("DELETE FROM %s WHERE ROOM_ID = %d",TABLE_ROOM,id);
        sqLiteHelper.query(SQL);
    }

}
