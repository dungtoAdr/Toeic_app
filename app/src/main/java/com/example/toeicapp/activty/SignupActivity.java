package com.example.toeicapp.activty;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.toeicapp.R;
import com.example.toeicapp.retrofit.ApiToeic;
import com.example.toeicapp.retrofit.RetrofitClient;
import com.example.toeicapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SignupActivity extends AppCompatActivity {

    private TextView tv_login;
    private FirebaseAuth firebaseAuth;
    private EditText edt_password, edt_confirm_password, edt_last_name, edt_first_name, edt_email;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        onclick();
    }

    private void signUp() {
        firebaseAuth = FirebaseAuth.getInstance();
        String first_name = edt_first_name.getText().toString();
        String last_name = edt_last_name.getText().toString();
        String email = edt_email.getText().toString();
        String pass = edt_password.getText().toString();
        String re_pass = edt_confirm_password.getText().toString();
        if (!TextUtils.isEmpty(first_name) && !TextUtils.isEmpty(last_name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(re_pass)) {
            if (pass.equals(re_pass)) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(verificationTask -> {
                                            if (verificationTask.isSuccessful()) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Lỗi khi gửi email xác nhận!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(first_name + " " + last_name)
                                        .build();

                                user.updateProfile(profileChangeRequest);
                            }
//                            Toast.makeText(getApplicationContext(),"Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void onclick() {
        tv_login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        btnSignIn.setOnClickListener(v -> {
            signUp();
        });
    }

    private void initView() {
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        tv_login = findViewById(R.id.tv_login);
        edt_password = findViewById(R.id.edtPassword);
        edt_confirm_password = findViewById(R.id.edtConfirmPassword);
        edt_first_name = findViewById(R.id.edt_first_name);
        edt_last_name = findViewById(R.id.edt_last_name);
        edt_email = findViewById(R.id.edtEmail);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

}