package com.example.quanlynhansu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Locale;

public class LeaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        EditText editStartDate = findViewById(R.id.editStartDate);
        EditText editEndDate = findViewById(R.id.editEndDate);

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
    }
}