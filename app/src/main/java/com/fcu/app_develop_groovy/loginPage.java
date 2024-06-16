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

public class loginPage extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Button btnToSignUp;
    private Button btnBackToHome;
    private Button btnLogin;

    private EditText etMail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnToSignUp = findViewById(R.id.btnToSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        etMail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etMail.getText().toString();
                String password = etPassword.getText().toString();
                if(v.getId() == R.id.btnLogin && email != "" && password != ""){
                    login(email, password);
                } else if (v.getId() == R.id.btnToSignUp) {
                    Intent intent = new Intent();
                    intent.setClass(loginPage.this, signupPage.class);
                    loginPage.this.startActivity(intent);
                } else if (v.getId() == R.id.btnBackToHome) {
                    Intent intent = new Intent();
                    intent.setClass(loginPage.this, MainActivity.class);
                    loginPage.this.startActivity(intent);
                }
            }
        };

        btnToSignUp.setOnClickListener(listener);
        btnLogin.setOnClickListener(listener);
        btnBackToHome.setOnClickListener(listener);

    }

    private void goToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(loginPage.this, MainActivity.class);
        startActivity(intent);
    }

    private void login(String email, String password){
        etMail.setError(null);
        etPassword.setError(null);

        String userEmail = etMail.getText().toString().trim();
        String userPassword = etPassword.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userPassword)) {
            focusView = etPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(userEmail)) {
            focusView = etMail;
            cancel = true;
        }

        if (cancel) {
            // 如果有錯誤，聚焦到有錯誤的輸入框
            focusView.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(loginPage.this, "Authentication succese.",
                                Toast.LENGTH_SHORT).show();
                        goToMainActivity();
                    }
                    else {
                        Toast.makeText(loginPage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    private boolean isSignin() {
        FirebaseUser user = mAuth.getCurrentUser();
        return user != null;
    }

    private void signOut() {
        mAuth.signOut();
    }
}