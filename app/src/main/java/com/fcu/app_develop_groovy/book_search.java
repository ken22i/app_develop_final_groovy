package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class book_search extends AppCompatActivity {
    EditText search_bookname,search_author,search_isbn,search_category;
    Button btn_search,btn_search_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.book_search_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        search_bookname = findViewById(R.id.search_book_name);
        search_author = findViewById(R.id.search_author);
        search_isbn = findViewById(R.id.search_isbn);
        search_category = findViewById(R.id.search_category);
        btn_search = findViewById(R.id.btn_search);
        btn_search_back = findViewById(R.id.btn_search_back);
        Book_data data = new Book_data();
        View.OnClickListener search_data = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        View.OnClickListener back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(book_search.this, MainActivity.class);
                book_search.this.startActivity(change);
                Toast.makeText(book_search.this, "回首頁", Toast.LENGTH_SHORT).show();
            }
        };
        btn_search.setOnClickListener(search_data);
        btn_search_back.setOnClickListener(back);
    }
}