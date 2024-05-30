package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Book_detail_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookAuthor;
    private ImageView ratingStars;
    private ListView reviewList;
    private Button btnOpenMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookImage = findViewById(R.id.review_imge);
        bookTitle = findViewById(R.id.book_title);
        ratingStars = findViewById(R.id.rating_stars);
        reviewList = findViewById(R.id.review_list);
        bookAuthor = findViewById(R.id.tv_author);
        btnOpenMenu = findViewById(R.id.btn_borrow);

        //側拉選單初始化
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        // 接收書籍資料
        Intent intent = getIntent();
        if (intent != null) {
            int imageResId = intent.getIntExtra("bookImage", -1);
            String title = intent.getStringExtra("bookTitle");
            int ratingResId = intent.getIntExtra("ratingStars", -1);
            String authorName = intent.getStringExtra("Author");
            List<Review> bookReviews = (List<Review>) getIntent().getSerializableExtra("bookReviews");

            if (imageResId != -1) {
                bookImage.setImageResource(imageResId);
            }
            if (title != null) {
                bookTitle.setText(title);
            }
            if (ratingResId != -1) {
                switch (ratingResId){
                    case 1:
                        ratingStars.setImageResource(R.drawable.star1);
                        break;
                    case 2:
                        ratingStars.setImageResource(R.drawable.star2);
                        break;
                    case 3:
                        ratingStars.setImageResource(R.drawable.star3);
                        break;
                    case 4:
                        ratingStars.setImageResource(R.drawable.star4);
                        break;
                    case 5:
                        ratingStars.setImageResource(R.drawable.star5);
                        break;
                }

            }
            if(authorName != null){
                bookAuthor.setText(authorName);
            }

            ReviewListAdapter adapter = new ReviewListAdapter(this, bookReviews);
            reviewList.setAdapter(adapter);

            // 這裡可以設置評價列表的資料
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home){
            Toast.makeText(Book_detail_Activity.this, "首頁選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_books) {
            Toast.makeText(Book_detail_Activity.this, "書籍選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_settings) {
            Toast.makeText(Book_detail_Activity.this, "設置選項被選中", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
