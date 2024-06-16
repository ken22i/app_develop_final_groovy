package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private ListView lvFilteredBooks;
    private Button btn_mainpage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        lvFilteredBooks = findViewById(R.id.lvFilteredBooks);
        btn_mainpage = findViewById(R.id.back_to_btn);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };
        btn_mainpage.setOnClickListener(listener);
        // 獲取從 MainActivity 傳遞過來的過濾後的書籍列表
        List<Book> filteredBooks = getIntent().getParcelableArrayListExtra("filteredBooks");

        if (filteredBooks != null) {
            Book_listAdapter adapter = new Book_listAdapter(this, filteredBooks);
            lvFilteredBooks.setAdapter(adapter);
        }

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(SearchResultActivity.this, "item被選中" + id, Toast.LENGTH_SHORT).show();
                Book selectedBook = filteredBooks.get(position);

                Intent intent = new Intent(SearchResultActivity.this, Book_detail_Activity.class);
                intent.putExtra("bookImage", selectedBook.getImageUrl());
                intent.putExtra("bookTitle", selectedBook.getName());
                intent.putExtra("ratingStars", selectedBook.getScore());
                intent.putExtra("Author", selectedBook.getAuthor());
                intent.putExtra("bookReviews", new ArrayList<>(selectedBook.getReviews()));

                startActivity(intent);
            }
        };
        lvFilteredBooks.setOnItemClickListener(itemClickListener);
    }
}
