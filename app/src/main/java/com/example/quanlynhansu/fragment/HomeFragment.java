package com.example.quanlynhansu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quanlynhansu.R;
import com.example.quanlynhansu.store.AccountStore;

public class HomeFragment extends Fragment {
    private View view;
    private TextView txtViewName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        this.init();

        return view;
    }

    private void init(){

        txtViewName = (TextView) view.findViewById(R.id.txt_name);
        String userName = String.valueOf(AccountStore.getUser().getUserName());
        txtViewName.setText(String.format("Xin chào %s",userName));


    }

}