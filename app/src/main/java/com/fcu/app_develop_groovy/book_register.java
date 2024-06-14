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

import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class book_register extends AppCompatActivity {
    EditText book_name,author,isbn,publishing,money,category,image;
    Button back,login;
    public Book_data data;
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
        book_name.setText("計算機演算法");
        author.setText("陳錫民");
        isbn.setText("123456789123456");
        publishing.setText("逢甲大學");
        money.setText("1000");
        category.setText("教育");
        image.setText("12.jpg");
        SQLiteDatabase book_data = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        book_data.execSQL(CREATE_TABLE);
        data = new Book_data();
        data.setDatabase(book_data);
        View.OnClickListener button_booklogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookname = book_name.getText().toString(),
                        authorname = author.getText().toString(),
                        pulishingname = publishing.getText().toString(),
                        category_str = category.getText().toString(),
                        isbncode = isbn.getText().toString();
                int bookmoney = Integer.valueOf(money.getText().toString());
                addBook(bookname,authorname);
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
    private void addBook(String bookname,String author){
        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(new Review("Steven","Great book!",R.drawable.person1,3));

        Book book = new Book(R.drawable.book1,bookname,0,0,author,reviews1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        ref.push().setValue(book);
        Toast.makeText(this,"Book add",Toast.LENGTH_SHORT).show();
    }
}