package com.example.quanlynhansu.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlynhansu.UserHomeFragment1;
import com.example.quanlynhansu.fragment.AccountFragment;
import com.example.quanlynhansu.fragment.HomeFragment;
import com.example.quanlynhansu.fragment.HomeUseFragment;

public class UserFragmentAdapter extends FragmentStateAdapter {
    private final int countFragment = 2;
    public UserFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new AccountFragment();
            default:
                return new HomeUseFragment();
        }
    }

    @Override
    public int getItemCount() {
        return this.countFragment;
    }
}
