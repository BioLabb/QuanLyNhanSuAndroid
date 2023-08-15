package com.example.quanlynhansu.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quanlynhansu.AttendanceActivity;
import com.example.quanlynhansu.GetSalaryActivity;
import com.example.quanlynhansu.LeaveActivity;
import com.example.quanlynhansu.R;
import com.example.quanlynhansu.RoomActivity;
import com.example.quanlynhansu.store.AccountStore;

public class AccountFragment extends Fragment {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account,container,false);

//        init();
//
//        btnPhongBan.setOnClickListener(this);
//        btnChamCong.setOnClickListener(this);
//        btnNghiPhep.setOnClickListener(this);
//        btnLuongThuong.setOnClickListener(this);
        return  view;

    }

//    public void init(){
//
//        btnPhongBan = (Button) view.findViewById(R.id.btn_quan_ly_phong_ban);
//        btnChamCong = (Button) view.findViewById(R.id.quan_ly_cham_cong);
//        btnLuongThuong = (Button) view.findViewById(R.id.quan_ly_luong_thuong);
//        btnNghiPhep = (Button) view.findViewById(R.id.quan_ly_nghi_phep);
//        txtSumNhanVien = (TextView) view.findViewById(R.id.sum_nhan_vien);
//        txtSumNghiPhep = (TextView) view.findViewById(R.id.txt_sum_nghi_phep);
//        txtSumCong = (TextView) view.findViewById(R.id.txt_sum_cong);
//    }

    protected void changeActivity(Context context, Class<?> activityClass){
        Intent intent = new Intent(context, activityClass);
        startActivity(intent);
    }
//    public void onClick(View view) {
//        int id = view.getId();
//
//        if(id == btnPhongBan.getId()){
//            changeActivity(context, RoomActivity.class);
//        }else if(id == btnChamCong.getId()){
//            changeActivity(context, AttendanceActivity.class);
//        }else if(id == btnNghiPhep.getId()){
//            changeActivity(context, LeaveActivity.class);
//        }else  if(id == btnLuongThuong.getId()){
//            changeActivity(context, GetSalaryActivity.class);
//        }
//    }
}