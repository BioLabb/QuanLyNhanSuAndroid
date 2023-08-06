package com.example.quanlynhansu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.GetSalaryHelper;
import com.example.quanlynhansu.store.AccountStore;

public class HomeUseFragment extends Fragment {
    private View view;
    private ImageView avatar;
    private TextView nameUser;
    private TextView tongCong;
    private TextView tongLuong;
    private Button dkNghiPhep;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user,container,false);

        this.init();

        nameUser.setText(AccountStore.getUser().getUserName());

        GetSalaryHelper getSalaryHelper =new GetSalaryHelper(getContext());

        int id = AccountStore.getUser().getAccountID();
        tongLuong.setText("0");


        return view;
    }

    private void init(){
        avatar = (ImageView) view.findViewById(R.id.img_avatar);
        nameUser = (TextView) view.findViewById(R.id.txt_name);
        tongCong = (TextView) view.findViewById(R.id.txt_sum_cong);
        tongLuong = (TextView) view.findViewById(R.id.txt_sum_luong);
//        Toast.makeText(getContext(),tongLuong.getText().toString(),Toast.LENGTH_SHORT).show();
        dkNghiPhep = (Button) view.findViewById(R.id.btn_dk_phep);
    }
}
