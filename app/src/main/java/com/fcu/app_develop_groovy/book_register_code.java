package com.fcu.app_develop_groovy;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
public class book_register_code extends AppCompatActivity{

    EditText book_name,author,isbn,publishing,money,category,image,bookid;
    Button back,login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.book_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.book_register_layout),(v,insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
            return insets;
        });
        book_name = findViewById(R.id.editTextText2);
        author = findViewById(R.id.editTextText3);
        isbn = findViewById(R.id.editTextText4);
        publishing = findViewById(R.id.editTextText5);
        money = findViewById(R.id.editTextText6);
        category = findViewById(R.id.editTextText7);
        image = findViewById(R.id.editTextText8);
        back = findViewById(R.id.button3);
        login = findViewById(R.id.button2);
        SQLiteDatabase book_data = openOrCreateDatabase("bookdata.db",MODE_PRIVATE,null);
        String table = "CREATE TABLE book(_id PRIMARY KEY,bookname TEXT,author TEXT,isbn INTEGER,publishing TEXT,money INTEGER,category TEXT)";
        book_data.execSQL(table);
        View.OnClickListener button_booklogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = book_name.getText().toString(),
                        authorname = author.getText().toString(),
                        pulishingname = publishing.getText().toString(),
                        category_str = category.getText().toString();
                int isbncode = Integer.valueOf(isbn.getText().toString()),
                        bookmoney = Integer.valueOf(money.getText().toString());
                String add = "INSERT INTO book(bookname,author,isbn,publishing,money,category) values ('" + bookname + "','" + authorname + "'," + isbn.toString() + ",'" +
                        pulishingname + "'," + money.toString() + ",'" + category_str + "')";
                book_data.execSQL(add);
            }
        };
        View.OnClickListener backtofirst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(book_register_code.this, MainActivity.class);
                book_register_code.this.startActivity(change);
            }
        };
        back.setOnClickListener(backtofirst);
        login.setOnClickListener(button_booklogin);
    }
}
