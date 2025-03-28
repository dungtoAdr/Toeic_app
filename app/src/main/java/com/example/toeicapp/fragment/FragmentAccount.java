package com.example.toeicapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.toeicapp.R;
import com.example.toeicapp.activty.FavoriteActivity;
import com.example.toeicapp.activty.HistoryActivity;
import com.example.toeicapp.activty.LoginActivity;
import com.example.toeicapp.activty.PolicyActivity;
import com.example.toeicapp.activty.ProfileActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FragmentAccount extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment, container, false);
    }

    private TextView bt_logout, tv_username, tv_email;
    private FirebaseAuth firebaseAuth;
    private LinearLayout line_info, line_history, line_support, line_policy, line_favorite;
    private ImageView avatar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        onclick();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user.getPhotoUrl() != null) {
            Glide.with(this).load(user.getPhotoUrl()).circleCrop().into(avatar);
        } else {
            Glide.with(this).load(R.drawable.account).circleCrop().into(avatar);
        }
        tv_username.setText(user.getDisplayName());
        tv_email.setText(user.getEmail());
    }

    private void onclick() {
        bt_logout.setOnClickListener(v -> {
            firebaseAuth.signOut();
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        line_info.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
        });
        line_support.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:totrungdung19032003@gmail.com?subject=Góp ý về sản phẩm DUNGTOEIC"));
            startActivity(Intent.createChooser(intent, "Chọn ứng dụng Email"));
        });
        line_history.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), HistoryActivity.class);
            startActivity(intent);
            Toast.makeText(getContext(),"History",Toast.LENGTH_SHORT).show();
        });
        line_policy.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), PolicyActivity.class);
            intent.putExtra("url", "https://policies.google.com/");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        line_favorite.setOnClickListener(v -> {
            Intent intent =new Intent(getContext(), FavoriteActivity.class);
            startActivity(intent);
        });
    }

    private void initView(View view) {
        bt_logout = view.findViewById(R.id.btn_logout);
        avatar = view.findViewById(R.id.avatar);
        tv_username = view.findViewById(R.id.tv_username);
        tv_email = view.findViewById(R.id.tv_email);
        line_info = view.findViewById(R.id.line_info);
        line_support = view.findViewById(R.id.line_support);
        line_history = view.findViewById(R.id.line_history);
        line_policy = view.findViewById(R.id.line_policy);
        line_favorite = view.findViewById(R.id.line_favorite);
        firebaseAuth = FirebaseAuth.getInstance();
    }
}
