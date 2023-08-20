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
import com.example.quanlynhansu.object.Account;

import java.util.List;

public class RoomDetailsAdapter extends ArrayAdapter<Account> {
    private List<Account> accountList;

    public RoomDetailsAdapter(@NonNull Context context, int account_room_view, List<Account> accountList) {
        super(context, 0, accountList);
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_room_view, parent, false);
        }

        Account account = accountList.get(position);

        TextView tvNameUserRoom = convertView.findViewById(R.id.tvNameUserRoom);
        TextView tvRoleUserRoom = convertView.findViewById(R.id.tvRoleUserRoom);

        tvNameUserRoom.setText(String.valueOf(account.getFullName()));
        tvRoleUserRoom.setText(String.valueOf(account.getEmail()));

        return convertView;
    }
}
