package com.example.toeicapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.toeicapp.fragment.FragmentAccount;
import com.example.toeicapp.fragment.FragmentHome;
import com.example.toeicapp.fragment.FragmentTest;
import com.example.toeicapp.fragment.FragmentTranslate;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:return new FragmentTest();
            case 2:return new FragmentTranslate();
            case 3:return new FragmentAccount();
            default:return new FragmentHome();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
