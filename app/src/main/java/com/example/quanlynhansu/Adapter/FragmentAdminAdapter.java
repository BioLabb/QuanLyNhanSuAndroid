package com.example.quanlynhansu.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlynhansu.fragment.SearchFragment;
import com.example.quanlynhansu.fragment.AccountFragment;
import com.example.quanlynhansu.fragment.HomeFragment;

public class FragmentAdminAdapter extends FragmentStateAdapter{
    private final int CountFragment = 3;

    public FragmentAdminAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new SearchFragment();
            case 2:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return CountFragment;
    }
}
