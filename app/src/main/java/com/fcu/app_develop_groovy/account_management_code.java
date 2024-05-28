package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class account_management_code extends AppCompatActivity {
    EditText studient_id_input;
    Button back,search;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.account_management_layout),(v,insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        });
        studient_id_input = findViewById(R.id.editTextText);
        back = findViewById(R.id.button4);
        search = findViewById(R.id.button);
        View.OnClickListener backtofirst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(account_management_code.this, MainActivity.class);
                account_management_code.this.startActivity(change);
            }
        };
        back.setOnClickListener(backtofirst);
    }
}
