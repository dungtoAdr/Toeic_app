package com.example.toeicapp.activty;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView navigationView;
    private Toolbar toolbar;
    private TextView toolbar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initControl();
    }

    private void initView() {
        viewPager2 = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.bottom_nav);
        toolbar=findViewById(R.id.tool_bar);
        toolbar_title=findViewById(R.id.toolbar_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }

    private void initControl() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                    toolbar_title.setText("TOEIC");
                } else if (position == 1) {
                    navigationView.getMenu().findItem(R.id.nav_test).setChecked(true);
                    toolbar_title.setText("Test");
                } else if (position == 2) {
                    navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                    toolbar_title.setText("Account");
                }
            }
        });
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    viewPager2.setCurrentItem(0, false);
                } else if (itemId == R.id.nav_test) {
                    viewPager2.setCurrentItem(1, false);
                } else if (itemId == R.id.nav_account) {
                    viewPager2.setCurrentItem(2, false);
                }
                return true;
            }
        });
    }
}