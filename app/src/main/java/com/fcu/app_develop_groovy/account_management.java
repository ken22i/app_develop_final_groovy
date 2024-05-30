package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class account_management extends AppCompatActivity {
    EditText studient_id_input;
    Button back,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_management_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studient_id_input = findViewById(R.id.edit_account);
        back = findViewById(R.id.btn_management_back);
        search = findViewById(R.id.btn_search);
        View.OnClickListener backtofirst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(account_management.this, MainActivity.class);
                account_management.this.startActivity(change);
            }
        };
        back.setOnClickListener(backtofirst);
    }
}