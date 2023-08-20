package com.example.quanlynhansu.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomHelper{
    private Context context;
    public final static String TABLE_ROOM = "Room";

    private SQL q;
    private final SQLiteDatabase Database;

    public RoomHelper(Context context){
        // tao co so du lieu
        q  = new SQL(context);
        // tuong tac voi du lie
        // cho ghi va do
        Database = q.getWritableDatabase();
        onCreate(Database);
    }
    public void onCreate(SQLiteDatabase database){
        //kiểm tra nếu không có dữ liệu trong bản hoặc chưa tạo bản thì chạy
        if (!q.isTableInitialized(database, TABLE_ROOM))
        {
            insertRoom("Phòng makerting",40);
            insertRoom("Phòng IT", 35);
            insertRoom("Phòng quản lý",10);
        }
    }

    public int insertRoom(String name, int number ){
        ContentValues values = new ContentValues();
//        values.put("room_id",room.getRoomID()) ;
        values.put("name",name);
        values.put("number",number);
        long statusInsert = Database.insert(TABLE_ROOM,null,values);
//        Database.close();
        if(statusInsert < 0)
            return 0; // insert that bai
        return 1; // insert thanh cong
    }
    public int insertRoom(Room room){
        ContentValues values = new ContentValues();
//        values.put("room_id",room.getRoomID()) ;
        values.put("name",room.getName());
        values.put("number",room.getNumber());
        long statusInsert = Database.insert(TABLE_ROOM,null,values);
//        Database.close();
        if(statusInsert < 0)
            return 0; // insert that bai
        return 1; // insert thanh cong
    }

    // tim room theo id
    public Room getRoom(int RoomID) {
        String SQL = String.format("SELECT * FROM %s WHERE room_id == %d ", TABLE_ROOM,RoomID);

        //Cursor cursor = sqLiteHelper.getData(SQL);
        Cursor cursor = q.getData(SQL);
        if(cursor.moveToFirst()){
            Room room = new Room(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            return room;
        }
        return null;
    }

    // lay tat ca room
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



    public int removeRoom(int id){
        String SQL = String.format("DELETE FROM %s WHERE ROOM_ID = %d",TABLE_ROOM,id);
        q.query(SQL);

        if(getRoom(id) == null)
            return 1;
        else
            return 0;
    }

    public int updateRoom(Room room) {
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("number", room.getNumber());

        String whereClause = "room_id = ?";
        String[] whereArgs = {String.valueOf(room.getRoomID())};

        int rowsAffected = Database.update(TABLE_ROOM, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int updateNameRoom(Room room ,int id) {
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("number", room.getNumber());

        String whereClause = "room_id = ?";
        String[] whereArgs = {String.valueOf(id)};

        int rowsAffected = Database.update(TABLE_ROOM, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int updateQuantity(Room room, int idRoom, int quantity) {
        ContentValues values = new ContentValues();
        values.put("name", room.getName());
        values.put("number", quantity);

        String whereClause = "room_id = ?";
        String[] whereArgs = {String.valueOf(idRoom)};

        int rowsAffected = Database.update(TABLE_ROOM, values, whereClause, whereArgs);
        if (rowsAffected < 0) {
            return 0; // Cập nhật thất bại
        }
        return 1; // Cập nhật thành công
    }

    public int numberOfRoom(int idRoom, Context context){
        AccountHelper accountHelper = new AccountHelper(context);
        return accountHelper.getCountByIdRoom(idRoom);
    }

}
