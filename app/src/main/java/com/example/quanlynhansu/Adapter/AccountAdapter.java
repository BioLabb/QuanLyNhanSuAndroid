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
import com.example.quanlynhansu.object.Role;
import com.example.quanlynhansu.object.RoleAccount;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.sqlitehelper.RoleHelper;


import java.util.List;

public class AccountAdapter extends ArrayAdapter<Account> {

    private List<Account> accountList;
    private RoleHelper roleHelper;
    private RoleAccountHelper roleAccountHelper;
    public AccountAdapter(@NonNull Context context, List<Account> accountList) {
        super(context, 0, accountList);
        this.accountList = accountList;
        roleHelper = new RoleHelper(context);
        roleAccountHelper = new RoleAccountHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.account_view, parent, false);
        }

        Account account = accountList.get(position);

        String RoleUser = searchRoleUser(account.getAccountID());
        TextView accountViewFullName = convertView.findViewById(R.id.accountViewFullName);
        TextView accountViewEmail = convertView.findViewById(R.id.accountViewEmail);
        TextView accountViewRole = convertView.findViewById(R.id.accountViewRole);
        TextView accountViewPhone = convertView.findViewById(R.id.accountViewPhone);

        accountViewFullName.setText(account.getFullName());
        accountViewEmail.setText(account.getEmail());
        accountViewRole.setText(RoleUser);
        accountViewPhone.setText(account.getPhone());



        return convertView;
    }
    public String searchRoleUser(int idUser){
        List<RoleAccount> roleAccountList = roleAccountHelper.getAllRoleAccounts();
        List<Role> roles =  roleHelper.getAllRoles();
        for(RoleAccount roleAccount: roleAccountList){
            if(roleAccount.getAccountId() == idUser)
                for(Role role: roles){
                    if(role.getRoleId() == roleAccount.getRoleId())
                    {

                        return role.getRoleName();
                    }
                }
        }
        return "Chưa xét";

    }
}

