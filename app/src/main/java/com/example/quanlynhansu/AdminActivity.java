package com.example.quanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.quanlynhansu.Adapter.FragmentAdminAdapter;
import com.example.quanlynhansu.store.AccountStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.init();

        // khi viewPage chuyển trang thì trạng thái tab của bottom cũng thay đổi theo
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.tab_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.tab_search).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.tab_account).setChecked(true);
                        break;
                }
            }
        });
        bottomNavigationView.setOnItemSelectedListener(this);
    }

    // tab bottom nav thay đổi
    // thì fragment trong viewPager cũng thay dổi theo tab
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int idSelect = item.getItemId();
        if(idSelect == R.id.tab_home){
            viewPager.setCurrentItem(0);
        }else if(idSelect == R.id.tab_search){
            Intent intent = new Intent(context,UserManagement.class);
            startActivity(intent);
//            viewPager.setCurrentItem(1);
        }
        else if(idSelect == R.id.tab_account){
            Intent intent = new Intent(context, PersonalInformation.class);
            intent.putExtra("idUser", AccountStore.getUser().getAccountID());
            intent.putExtra("roleDelete","User");
            startActivity(intent);
//            viewPager.setCurrentItem(2);
        }
        return true;
    }


    private void init(){
        context = this;
        viewPager = (ViewPager2) findViewById(R.id.view_pager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);

        FragmentAdminAdapter fragmentAdminAdapter = new FragmentAdminAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(fragmentAdminAdapter);

    }
}