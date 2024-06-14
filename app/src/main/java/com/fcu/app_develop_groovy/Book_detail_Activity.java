package com.fcu.app_develop_groovy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Book_detail_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookAuthor;
    private ImageView ratingStars;
    private ListView reviewList;
    private Button btnOpenMenu;
    private Button btnSubmitReview;
    private List<Review> bookReviews;
    private ReviewListAdapter adapter;
    private DatabaseReference databaseReference;
    private String title; // 書籍標題
    private String authorName; // 書籍作者
    private int imageResId; // 書籍圖片資源ID
    private int ratingResId; // 評價星數資源ID
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
        btnSubmitReview = findViewById(R.id.btn_review_submit);
        // 初始化 Firebase Database 引用
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        // 側拉選單初始化
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
            imageResId = intent.getIntExtra("bookImage", -1);
            title = intent.getStringExtra("bookTitle");
            ratingResId = intent.getIntExtra("ratingStars", -1);
            authorName = intent.getStringExtra("Author");
            bookReviews = (List<Review>) getIntent().getSerializableExtra("bookReviews");

            if (imageResId != -1) {
                bookImage.setImageResource(imageResId);
            }
            if (title != null) {
                bookTitle.setText(title);
            }
            if (ratingResId != -1) {
                switch (ratingResId) {
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
            if (authorName != null) {
                bookAuthor.setText(authorName);
            }

            adapter = new ReviewListAdapter(this, bookReviews);
            reviewList.setAdapter(adapter);
        }

        // 設置提交評論按鈕的點擊事件
        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReviewDialog();
            }
        });
    }

    private void showReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_review, null);
        builder.setView(dialogView);


        final EditText etReviewContent = dialogView.findViewById(R.id.etReviewContent);
        final RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);

        builder.setTitle("提交評論")
                .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String reviewContent = etReviewContent.getText().toString();
                        int reviewScore = (int) ratingBar.getRating();

                        if (!reviewContent.isEmpty() && reviewScore > 0) {
                            Review newReview = new Review("testUser", reviewContent, R.drawable.person3, reviewScore);
                            bookReviews.add(newReview);
                            adapter.notifyDataSetChanged();
                            // 上傳評論到 Firebase
                            // 刪除舊書籍記錄並上傳新的書籍記錄
                            updateBookInFirebase();

                        } else {
                            Toast.makeText(Book_detail_Activity.this, "請填寫完整的評論信息和星級評分", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void updateBookInFirebase() {
        // 刪除舊書籍記錄
        databaseReference.orderByChild("name").equalTo(title).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
                // 上傳新的書籍記錄
                Book updatedBook = new Book(imageResId, title, 0, ratingResId, authorName, bookReviews);
                databaseReference.push().setValue(updatedBook)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(Book_detail_Activity.this, "評論已成功提交並更新", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Book_detail_Activity.this, "評論提交失敗：" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Book_detail_Activity.this, "操作失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            Toast.makeText(Book_detail_Activity.this, "首頁選項被選中", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Book_detail_Activity.this, MainActivity.class);
            startActivity(intent);
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
