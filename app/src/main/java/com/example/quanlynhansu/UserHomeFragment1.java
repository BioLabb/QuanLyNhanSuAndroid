package com.example.quanlynhansu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserHomeFragment1 extends Fragment {
    private View view;
    private ImageView avatar;
    private TextView nameUser;
    private TextView tongCong;
    private TextView tongLuong;
    private Button dkNghiPhep;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user,container,false);

        this.init();
        System.out.println(view.getId());
        return view;
    }

    private void init(){
        avatar = (ImageView) view.findViewById(R.id.img_avatar);
        nameUser = (TextView) view.findViewById(R.id.txt_name);
        tongCong = (TextView) view.findViewById(R.id.txt_sum_cong);
        tongLuong = (TextView) view.findViewById(R.id.txt_sum_nghi_phep);
        dkNghiPhep = (Button) view.findViewById(R.id.btn_history_attendance);
    }
}
