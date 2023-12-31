package com.example.quanlynhansu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.object.GetSalary;
import com.example.quanlynhansu.sqlitehelper.AccountHelper;
import com.example.quanlynhansu.sqlitehelper.GetSalaryHelper;

import java.util.List;

public class GetSalaryAdapter extends ArrayAdapter<GetSalary> {
    private List<GetSalary> getSalaryList;
    double sumSalary;
    public GetSalaryAdapter(@NonNull Context context, int activity_get_salary, List<GetSalary> getSalaryList) {
        super(context, 0,getSalaryList);
        this.getSalaryList = getSalaryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.get_salary_view, parent, false);
        }

        GetSalary getSalary = getSalaryList.get(position);
        TextView tvUserIdSalary = convertView.findViewById(R.id.tvUserIdSalary);
        TextView tvDateSalary = convertView.findViewById(R.id.tvDateSalary);
        TextView tvSalary = convertView.findViewById(R.id.tvSalary);
        TextView tvBonus = convertView.findViewById(R.id.tvBonus);
        TextView tvSumSalary = convertView.findViewById((R.id.tvSumSalary));

        AccountHelper accountHelper = new AccountHelper(this.getContext());
        //set gia tri cho tong luong cua tung user
        GetSalaryHelper getSalaryHelper = new GetSalaryHelper(this.getContext());
        sumSalary = getSalaryHelper.sumGetSalaryById(getSalary.getAccountID(),this.getContext());
        getSalary.setSum(sumSalary);

        tvUserIdSalary.setText(String.valueOf(accountHelper.getAccount(getSalary.getAccountID()).getUserName()));
        tvDateSalary.setText(String.valueOf(getSalary.getStrDate()));
        tvSalary.setText(String.valueOf(getSalary.getSalary()));
        tvBonus.setText(String.valueOf(getSalary.getBonus()));
        tvSumSalary.setText(String.valueOf(getSalary.getSum()));

        return convertView;
    }
}
