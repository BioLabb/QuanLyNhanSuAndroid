package com.example.quanlynhansu.object;

public class Room {
    private int roomID;
    private String name;
    private int number;

    public Room(int roomID, String name, int number) {
        this.roomID = roomID;
        this.name = name;
        this.number = number;
    }
    public Room( String name, int number) {
        this.name = name;
        this.number = number;
    }
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public String toString(){
        return name;
    }

}
