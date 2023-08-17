package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.quanlynhansu.Adapter.LeaveAdapter;
import com.example.quanlynhansu.Adapter.RoomAdapter;
import com.example.quanlynhansu.object.Leave;
import com.example.quanlynhansu.object.Room;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;
import com.example.quanlynhansu.store.AccountStore;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LeaveActivity extends AppCompatActivity {
    LeaveHelper leaveHelper;

    ListView Lview;
    private LeaveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);

        int iduser = AccountStore.getUser().getAccountID();
        leaveHelper  = new LeaveHelper(LeaveActivity.this);
        EditText editStartDate = findViewById(R.id.editStartDate);
        EditText editEndDate = findViewById(R.id.editEndDate);
        EditText editReason = findViewById(R.id.editReason);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        Lview = findViewById(R.id.listLeave);


        // Lấy dữ liệu từ Room
        List<Leave> leaveList = leaveHelper.getAllLeavesReverse();

        // Tạo adapter và gắn dữ liệu vào ListView
        adapter = new LeaveAdapter(LeaveActivity.this, leaveList);
        Lview.setAdapter(adapter);


// Lắng nghe sự kiện chạm vào EditText
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày tháng hiện tại
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Hiển thị DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(LeaveActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Định dạng ngày tháng thành chuỗi "dd/MM/yyyy"
                                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                                        dayOfMonth, monthOfYear + 1, year);

                                // Hiển thị giá trị ngày tháng trong EditText
                                editStartDate.setText(formattedDate);
                            }
                        }, year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        // Lắng nghe sự kiện chạm vào EditText
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ngày tháng hiện tại
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Hiển thị DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(LeaveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Định dạng ngày tháng thành chuỗi "dd/MM/yyyy"
                        String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                                dayOfMonth, monthOfYear + 1, year);

                        // Hiển thị giá trị ngày tháng trong EditText
                        editEndDate.setText(formattedDate);
                    }
                }, year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });


        editStartDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần xử lý trong quá trình văn bản thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Định dạng văn bản thành "dd/MM/yyyy" sau khi văn bản thay đổi
                String inputText = s.toString();

                if (inputText.length() == 2 || inputText.length() == 5) {
                    inputText += "/";
                    editStartDate.setText(inputText);
                    editStartDate.setSelection(inputText.length());
                }
            }
        });
        editEndDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý trước khi văn bản thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần xử lý trong quá trình văn bản thay đổi
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Định dạng văn bản thành "dd/MM/yyyy" sau khi văn bản thay đổi
                String inputText = s.toString();

                if (inputText.length() == 2 || inputText.length() == 5) {
                    inputText += "/";
                    editEndDate.setText(inputText);
                    editEndDate.setSelection(inputText.length());
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Leave leave = new Leave(editStartDate.getText().toString(),editEndDate.getText().toString(),editReason.getText().toString(),"Chưa duyệt",iduser);
                leaveHelper.insertLeave(leave);
                System.out.println(leave);
                System.out.println(editReason.getText().toString());

                System.out.println(editEndDate.getText());
                System.out.println(editStartDate.getText());

                adapter.updateData(leaveHelper.getAllLeavesReverse());

            }
        });
    }
}