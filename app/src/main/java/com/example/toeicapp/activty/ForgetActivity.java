package com.example.toeicapp.activty;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.toeicapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText emailInput;
    private Button forgot_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        emailInput =findViewById(R.id.emailInput);
        forgot_button = findViewById(R.id.forgot_button);
        firebaseAuth = FirebaseAuth.getInstance();
        forgot_button.setOnClickListener(v -> {
            firebaseAuth.sendPasswordResetEmail(emailInput.getText().toString()).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Toast.makeText(this, "Email đã được gửi", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
            });
        });
    }
}