package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class book_register extends AppCompatActivity {
    EditText book_name,author,isbn,publishing,money,category,image;
    Button back,login;
    public Book_data data;
    private static final String DATABASE_NAME = "bookdata";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS book(_id INTEGER PRIMARY KEY,bookname TEXT,author TEXT,isbn INTEGER,publishing TEXT,money INTEGER,category TEXT)";
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
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
        Button chooseImage = findViewById(R.id.btn_choose_image);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
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
                String bookname = book_name.getText().toString();
                String authorname = author.getText().toString();
                uploadFile(bookname, authorname);
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
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
        }
    }
    private void uploadFile(String bookname, String author) {
        if (imageUri != null) {
            StorageReference fileReference = FirebaseStorage.getInstance().getReference("uploads").child(System.currentTimeMillis() + ".jpg");
            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    addBook(bookname, author, imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(book_register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void addBook(String bookname, String author, String imageUrl) {
        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(new Review("Steven", "Great book!", R.drawable.person1, 3));

        Book book = new Book(imageUrl, bookname, 0, 0, author, reviews1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        ref.push().setValue(book);
        Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
    }
}