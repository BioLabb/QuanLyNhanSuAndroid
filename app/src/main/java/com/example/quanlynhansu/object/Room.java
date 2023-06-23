package com.example.quanlynhansu.object;

public class Room {
    private int roomID;
    private String name;
    private String number;

    public Room(int roomID, String name){
        this.setRoomID(roomID);
        this.setName(name);
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
