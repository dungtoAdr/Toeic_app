package com.example.toeicapp.activty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toeicapp.R;
import com.example.toeicapp.adapter.ViewPagerAdapter;
import com.example.toeicapp.model.User;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private BottomNavigationView navigationView;
    private TextView toolbar_title;
    private FirebaseAuth firebaseAuth;
    private ApiToeic apiToeic;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initControl();
        initData();
    }

    private void initData() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String name = user.getDisplayName();
        String uid = user.getUid();
        User userr = new User(name, uid);
        compositeDisposable.add(apiToeic.signUp(userr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                Log.d("TAG_signup_user", userModel.getMessage());
                            } else {
                                Log.d("TAG_signup_user", userModel.getMessage());
                            }
                        }, throwable -> {
                            Log.d("TAG_signup_user", throwable.getMessage());
                        }
                )
        );
    }

    private void initView() {
        apiToeic = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiToeic.class);
        firebaseAuth = FirebaseAuth.getInstance();
        viewPager2 = findViewById(R.id.view_pager);
        navigationView = findViewById(R.id.bottom_nav);
        toolbar_title = findViewById(R.id.toolbar_title);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
    }

    private void initControl() {
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                    toolbar_title.setText("DUNGTOEIC");
                } else if (position == 1) {
                    navigationView.getMenu().findItem(R.id.nav_test).setChecked(true);
                    toolbar_title.setText("Test");
                } else if (position == 2) {
                    navigationView.getMenu().findItem(R.id.nav_translate).setChecked(true);
                    toolbar_title.setText("Translate");
                } else if (position == 3) {
                    navigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                    toolbar_title.setText("Account");
                }
            }
        });
        navigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                viewPager2.setCurrentItem(0, false);
            } else if (itemId == R.id.nav_test) {
                viewPager2.setCurrentItem(1, false);
            } else if (itemId == R.id.nav_translate) {
                viewPager2.setCurrentItem(2, false);
            } else if (itemId == R.id.nav_account) {
                viewPager2.setCurrentItem(3, false);
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}