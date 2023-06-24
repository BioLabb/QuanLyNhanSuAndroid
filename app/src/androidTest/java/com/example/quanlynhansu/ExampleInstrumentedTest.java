package com.example.quanlynhansu;

import android.app.Instrumentation;
import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;
import com.example.quanlynhansu.sqlitehelper.SQLiteHelper;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    final RoomHelper roomHelper = new RoomHelper(appContext);
    @Test
    public void useAppContext() {
        // Context of the app under test.
        assertEquals("com.example.quanlynhansu", appContext.getPackageName());
    }


    @Test
    public  void addRoom(){
        // them room chua co trong data
        Room r = new Room(1,"test",123);
        assertEquals(roomHelper.insertRoom(r),1);
    }

    @Test
    public void addRoom1(){
        // them room da co trong data

        Room r = new Room(1,"test",123);
        assertEquals(roomHelper.insertRoom(r),0);
    }

    @Test
    public void getRoom(){
        // tim room da co trong data
        assertEquals(roomHelper.getRoom(1).getRoomID(),1);
    }

    @Test
    public void getRoom2(){
        // tìm room chưa có trong data
        assertEquals(roomHelper.getRoom(99),null);
    }



}