package com.fcu.app_develop_groovy;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class book_register_code extends AppCompatActivity{

    EditText book_name,author,isbn,publishing,money,category,image;
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
    }
}
