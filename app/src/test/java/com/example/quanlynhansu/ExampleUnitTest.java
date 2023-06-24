package com.example.quanlynhansu;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.app.Application;
import android.app.Instrumentation;

import com.example.quanlynhansu.sqlitehelper.RoomHelper;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(org.junit.runner.Runner.class)
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // TEST ROOMHELPER
    @Test
    public void textAddRoom(){
//        RoomHelper roomHelper = new RoomHelper(ApplicationC);
//
//        int id = roomHelper.getRoom(1).getRoomID();
//
//        assertEquals(id,1);
    }
}