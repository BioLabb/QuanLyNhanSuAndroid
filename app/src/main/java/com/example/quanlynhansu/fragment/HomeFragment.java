package com.example.quanlynhansu.fragment;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.LeaveHelper;
import com.example.quanlynhansu.store.AccountStore;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private View view;
    private TextView txtViewName;
    private TextView txtSumNhanVien;
    private TextView txtSumNghiPhep;
    private TextView txtSumCong;

    private Button btnPhongBan;
    private Button btnChamCong;
    private Button btnNghiPhep;
    private Button btnLuongThuong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


        this.init();

        // set event Onclick
        btnPhongBan.setOnClickListener(this);
        btnChamCong.setOnClickListener(this);
        btnNghiPhep.setOnClickListener(this);
        btnLuongThuong.setOnClickListener(this);

        return view;
    }

    private void init(){
        context = getContext();

        // init view
        btnPhongBan = (Button) view.findViewById(R.id.btn_quan_ly_phong_ban);
        btnChamCong = (Button) view.findViewById(R.id.quan_ly_cham_cong);
        btnLuongThuong = (Button) view.findViewById(R.id.quan_ly_luong_thuong);
        btnNghiPhep = (Button) view.findViewById(R.id.quan_ly_nghi_phep);
        txtSumNhanVien = (TextView) view.findViewById(R.id.sum_nhan_vien);
        txtSumNghiPhep = (TextView) view.findViewById(R.id.txt_sum_nghi_phep);
        txtSumCong = (TextView) view.findViewById(R.id.txt_sum_cong);

        // setName user at Textview
        txtViewName = (TextView) view.findViewById(R.id.txt_name);
        String userName = String.valueOf(AccountStore.getUser().getUserName());
        txtViewName.setText(String.format("Xin chào %s",userName));

        // set Count in box view

        // set count nhan vien
        AccountHelper accountHelper = new AccountHelper(context);
        String sumNhanVien = String.valueOf(accountHelper.getCountAccount());
        txtSumNhanVien.setText(sumNhanVien);

        // set count nghi phep
        LeaveHelper leaveHelper = new LeaveHelper(context);
        String sumNgiPhep = String.valueOf(leaveHelper.getCountLeave());
        txtSumNghiPhep.setText(sumNgiPhep);

        // set count cong
        AttendanceHelper attendanceHelper = new AttendanceHelper(context);
        String sumCong = String.valueOf(attendanceHelper.getCountAttendance());
        txtSumCong.setText(sumCong);

    }

    private void changeActivity(Context context, Class<?> activity){
        Intent intent = new Intent(context, activity);
        startActivity(intent);

    }
    // handler event onclick
    @Override
    public void onClick(View v) {
        int id = view.getId();

        if(id == btnPhongBan.getId()){

        }else if(id == btnChamCong.getId()){

        }else if(id == btnChamCong.getId()){

        }else  if(id == btnLuongThuong.getId()){

        }
    }
}