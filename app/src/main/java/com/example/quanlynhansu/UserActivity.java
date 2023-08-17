package com.example.quanlynhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.quanlynhansu.Adapter.FragmentAdminAdapter;
import com.example.quanlynhansu.Adapter.UserFragmentAdapter;
import com.example.quanlynhansu.sqlitehelper.RoleAccountHelper;
import com.example.quanlynhansu.store.AccountStore;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class UserActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager2;
    RoleAccountHelper roleAccountHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // init
        this.init();

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.tab_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.tab_account).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.tab_home){
            viewPager2.setCurrentItem(0);
        }else if(id == R.id.tab_account){
            Intent intent = new Intent(getApplicationContext(),PersonalInformation.class);
            intent.putExtra("accountID", AccountStore.getUser().getAccountID());
            String roleDelete = roleAccountHelper.searchRoleUser(AccountStore.getUser().getAccountID());
            intent.putExtra("roleDelete", roleDelete);
            Log.d("roleDelete", roleDelete);
            startActivity(intent);
            System.out.println(roleDelete);
//            viewPager2.setCurrentItem(1);
        }
        return true;
    }

    protected void init(){
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.view_pager);
        roleAccountHelper = new RoleAccountHelper(UserActivity.this);
        UserFragmentAdapter userFragmentAdapter = new UserFragmentAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager2.setAdapter(userFragmentAdapter);
    }
}