package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class book_register extends AppCompatActivity {
    EditText book_name,author,isbn,publishing,money,category,image;
    Button back,login;
    private static final String DATABASE_NAME = "bookdata";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS book(_id INTEGER PRIMARY KEY,bookname TEXT,author TEXT,isbn INTEGER,publishing TEXT,money INTEGER,category TEXT)";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.book_register_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        book_name = findViewById(R.id.edit_bookname);
        author = findViewById(R.id.edit_author);
        isbn = findViewById(R.id.edit_isbn);
        publishing = findViewById(R.id.edit_publishing);
        money = findViewById(R.id.edit_money);
        category = findViewById(R.id.edit_category);
        image = findViewById(R.id.edit_image);
        back = findViewById(R.id.btn_back_register);
        login = findViewById(R.id.btn_login);

        SQLiteDatabase book_data = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        book_data.execSQL(CREATE_TABLE);
        Book_data data = new Book_data(book_data);
        View.OnClickListener button_booklogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = book_name.getText().toString(),
                        authorname = author.getText().toString(),
                        pulishingname = publishing.getText().toString(),
                        category_str = category.getText().toString(),
                        isbncode = isbn.getText().toString();
                int bookmoney = Integer.valueOf(money.getText().toString());
                data.addDatabase(bookname,authorname,isbncode,pulishingname,bookmoney,category_str);
                String add = "INSERT INTO book(bookname,author,isbn,publishing,money,category) values ('" + bookname + "','" + authorname + "'," + isbn.toString() + ",'" +
                        pulishingname + "'," + money.toString() + ",'" + category_str + "')";
                book_data.execSQL(add);
                Toast.makeText(book_register.this, "書籍登入成功1", Toast.LENGTH_SHORT).show();
            }
        };
        View.OnClickListener backtofirst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(book_register.this, MainActivity.class);
                book_register.this.startActivity(change);
                Toast.makeText(book_register.this, "回首頁", Toast.LENGTH_SHORT).show();
            }
        };
        login.setOnClickListener(button_booklogin);
        back.setOnClickListener(backtofirst);
    }
}