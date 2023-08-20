package com.example.quanlynhansu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlynhansu.AttendanceActivity;
import com.example.quanlynhansu.AttendanceDetailsActivity;
import com.example.quanlynhansu.LeaveActivity;
import com.example.quanlynhansu.R;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.GetSalaryHelper;
import com.example.quanlynhansu.store.AccountStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeUseFragment extends Fragment {
    private View view;
    private ImageView avatar;
    private TextView nameUser;
    private TextView tongCong;
    private TextView tongLuong;
    private Button dkNghiPhep;
    private Button chamCong;
    private Button btnHistoryAttendance;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user,container,false);

        this.init();

        nameUser.setText(AccountStore.getUser().getUserName());

        GetSalaryHelper getSalaryHelper =new GetSalaryHelper(getContext());

        int id = AccountStore.getUser().getAccountID();
        tongLuong.setText("0");

        dkNghiPhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LeaveActivity.class);
                startActivity(intent);
            }
        });

        AttendanceHelper attendanceHelper = new AttendanceHelper(getContext());
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        // Định dạng ngày theo định dạng dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(currentDate);

        chamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceHelper.insertAttendance(formattedDate,AccountStore.getUser().getAccountID());
                Toast.makeText(getContext() , "Chấm công thành công !!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnHistoryAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AttendanceDetailsActivity.class);
                intent.putExtra("idUserAttendance", id);
                startActivity(intent);
            }
        });

        return view;
    }

    private void setText(){
        AttendanceHelper attendanceHelper = new AttendanceHelper(getContext());
        String sumCong = String.valueOf(attendanceHelper.totalAllAttendance(AccountStore.getUser().getAccountID()));
        tongCong.setText(sumCong);

        // set tong luong
        GetSalaryHelper getSalaryHelper = new GetSalaryHelper(getContext());
        String sumSalary = String.valueOf(getSalaryHelper.sumGetSalaryById(AccountStore.getUser().getAccountID(),getContext()));
        tongLuong.setText(sumSalary);
    }

    private void init(){
        avatar = (ImageView) view.findViewById(R.id.img_avatar);
        nameUser = (TextView) view.findViewById(R.id.txt_name);
        tongCong = (TextView) view.findViewById(R.id.txt_sum_cong);
        tongLuong = (TextView) view.findViewById(R.id.txt_sum_luong);
//        Toast.makeText(getContext(),tongLuong.getText().toString(),Toast.LENGTH_SHORT).show();
        dkNghiPhep = (Button) view.findViewById(R.id.btn_dk_phep);
        chamCong = (Button) view.findViewById(R.id.btn_cham_cong);
        btnHistoryAttendance = (Button) view.findViewById(R.id.btn_history_attendance);

        this.setText();
    }
}
