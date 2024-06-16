package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signupPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText username;
    private EditText etMail2;
    private EditText etPassword2;

    private Button btnCancel;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnCancel = findViewById(R.id.btnCancel);
        btnSignUp = findViewById(R.id.btnSignUp);
        etMail2 = findViewById(R.id.etEmail2);
        etPassword2 = findViewById(R.id.etPassword2);
        username = findViewById(R.id.etName);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String email = etMail2.getText().toString();
                String password = etPassword2.getText().toString();
                if(v.getId() == R.id.btnSignUp && userName != "" && email != "" && password != ""){
                    signUp(email, password);
                } else if (v.getId() == R.id.btnCancel) {
                    Intent intent = new Intent();
                    intent.setClass(signupPage.this, loginPage.class);
                    signupPage.this.startActivity(intent);
                }
            }
        };

        btnCancel.setOnClickListener(listener);
        btnSignUp.setOnClickListener(listener);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(signupPage.this, loginPage.class);
        startActivity(intent);
    }

    private void signUp(String email, String password){

        username.setError(null);
        etMail2.setError(null);
        etPassword2.setError(null);

        String userName = username.getText().toString().trim();
        String userEmail = etMail2.getText().toString().trim();
        String userPassword = etPassword2.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;


        if (TextUtils.isEmpty(userPassword)) {
            focusView = etPassword2;
            cancel = true;
        }

        if (TextUtils.isEmpty(userEmail)) {
            focusView = etMail2;
            cancel = true;
        }

        if (TextUtils.isEmpty(userName)) {
            focusView = username;
            cancel = true;
        }

        if (cancel) {
            // 如果有錯誤，聚焦到有錯誤的輸入框
            focusView.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(signupPage.this, "Authentication succese.",
                                Toast.LENGTH_SHORT).show();
                        goToLoginActivity();
                    } else {
                        Toast.makeText(signupPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}