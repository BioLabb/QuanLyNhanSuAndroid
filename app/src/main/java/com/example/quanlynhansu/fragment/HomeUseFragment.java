package com.example.quanlynhansu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.quanlynhansu.AttendanceActivity;
import com.example.quanlynhansu.AttendanceDetailsActivity;
import com.example.quanlynhansu.GetSalaryActivity;
import com.example.quanlynhansu.LeaveActivity;
import com.example.quanlynhansu.MainActivity;
import com.example.quanlynhansu.R;
import com.example.quanlynhansu.object.Account;
import com.example.quanlynhansu.object.GetSalary;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.AttendanceHelper;
import com.example.quanlynhansu.sqlitehelper.GetSalaryHelper;
import com.example.quanlynhansu.store.AccountStore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeUseFragment extends Fragment {
    private View view;
    private ImageView avatar;
    private TextView nameUser;
    private TextView tongCong;
    private TextView tongLuong;
    private Button dkNghiPhep;
    private Button dangXuat;
    private Button chamCong;
    private Button btnHistoryAttendance;
    int isChamCong;
    ArrayList<GetSalary> arrGetSalary;
    List<Account> arrAccount;
    int idAccount;
    double sumSalary;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_user,container,false);

        this.init();
        this.setText();

        nameUser.setText(AccountStore.getUser().getUserName());

        GetSalaryHelper getSalaryHelper =new GetSalaryHelper(getContext());

        int id = AccountStore.getUser().getAccountID();


        dkNghiPhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LeaveActivity.class);
                startActivity(intent);
            }
        });

        AttendanceHelper attendanceHelper = new AttendanceHelper(getContext());
        arrGetSalary = (ArrayList<GetSalary>) getSalaryHelper.getSalaryList();
        arrGetSalary.clear();
        //set luong cho tung nhan vien
        AccountHelper accountHelper = new AccountHelper(getContext());
        arrAccount =  accountHelper.getAllAccounts();
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        // Định dạng ngày theo định dạng dd/MM/yyyy
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(currentDate);

        chamCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(attendanceHelper.isHaveAttendance(formattedDate, id))
                    Toast.makeText(getContext() , "Bạn đã chấm công rồi ! vui đợi qua ngày mai.", Toast.LENGTH_SHORT).show();
                else{
                    GetSalary getSalary;
                    isChamCong = attendanceHelper.insertAttendance(formattedDate,id);
                    //duyet qua danh sach acc de thong ke luong
                    for (int i = 0; i < arrAccount.size() ; i++){
                        idAccount = arrAccount.get(i).getAccountID();
                        getSalary = getSalaryHelper.getSalaryByIdUser(idAccount);
                        //if account chua co he so luong thi khoi tao
                        if(getSalaryHelper.isUserHaveSalary(idAccount)){
                            //cap nhat vao database
                            sumSalary = getSalaryHelper.sumGetSalaryById(idAccount,getContext());
                            getSalaryHelper.updateSum(getSalary,sumSalary);
                        }else {
                            getSalaryHelper.insert(new GetSalary(formattedDate, 5000, 300,0, idAccount));
                        }
                    }
                    setText();
                    Toast.makeText(getContext() , "Chấm công thành công !!!", Toast.LENGTH_SHORT).show();
                }
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

        dangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
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
        System.out.println(sumSalary);
        tongLuong.setText(sumSalary);
    }

    private void init(){
        avatar = (ImageView) view.findViewById(R.id.img_avatar);
        nameUser = (TextView) view.findViewById(R.id.txt_name);
        tongCong = (TextView) view.findViewById(R.id.txt_sum_cong);
        tongLuong = (TextView) view.findViewById(R.id.txt_sum_luong);

        dkNghiPhep = (Button) view.findViewById(R.id.btn_dk_phep);
        chamCong = (Button) view.findViewById(R.id.btn_cham_cong);
        dangXuat = (Button) view.findViewById(R.id.btn_dang_xuat);

        btnHistoryAttendance = (Button) view.findViewById(R.id.btn_history_attendance);

        this.setText();
    }
}
