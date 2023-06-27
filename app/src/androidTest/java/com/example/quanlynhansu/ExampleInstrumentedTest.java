package com.example.quanlynhansu;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.Attendance;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoomHelper;

import java.nio.channels.AcceptPendingException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    final RoomHelper roomHelper = new RoomHelper(appContext);
    final AttendanceHelper attendanceHelper = new AttendanceHelper(appContext);
    final RoleAccountHelper roleAccountHelper = new RoleAccountHelper(appContext);

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
        assertEquals(roomHelper.insertRoom(r),1);
    }
    @Test
    public void addRoom2(){
        // them room da co trong data
        assertEquals(roomHelper.insertRoom("test",123),1);
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


    @Test
    public  void addAttendance(){

        assertEquals(attendanceHelper.insertAttendance("30-2-2002",3),1);
    }
    //nên chạy addAttendance nhiều lần để tránh bị lỗi khi chạy các hàm khác
    @Test
    public  void remoteAttendance(){

        assertEquals(attendanceHelper.removeAttendance(1),1);
    }
    @Test
    public  void updateAttendance(){

        assertEquals(attendanceHelper.updateAttendance(new Attendance(2,"20-3-2001",3)),1);
    }


    @Test
    public  void addRoleA(){

        assertEquals(roleAccountHelper.insertRoleAccount(1,1),1);
    }
    public  void addRoleA1(){

        assertEquals(roleAccountHelper.insertRoleAccount(2,1),1);
    }

    @Test
    public void testDate(){
        Leave leave = new Leave("12/12/2023","12/12/2024","khu vuc","status",123);
        LeaveHelper helper = new LeaveHelper(appContext);
        assertEquals(helper.insertLeave(leave),1);
    }
    //nên chạy addAttendance nhiều lần để tránh bị lỗi khi chạy các hàm khác
    @Test
    public  void deleteRoleA(){

        assertEquals(roleAccountHelper.deleteRoleAccount(1,1),1);
        //xoa dựa vào mã roleid và accountid không dựa vào thứ tự bảng
    }
  //không cần update vì bảng này mối quan hệ many to many

    @Test
    public void addAccount(){
//        Account account = new Account(1,"useName","12345","sinhTien","email");

        Account account = new Account(2,1,"useName","12345","sinhTien","email","01424234","adress");
        AccountHelper accountHelper = new AccountHelper(appContext);
//        accountHelper.insertAccount(account);

        Account account1 = accountHelper.getAccount(4);
        assertEquals(account1.getAccountID(),null );
    }
}