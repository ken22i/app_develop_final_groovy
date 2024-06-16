package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private Button btnOpenMenu;
    private ViewPager2 viewPager;
    private List<Integer> imageList = new ArrayList<>();
    private List<String> urlList = new ArrayList<>();
    private Handler handler = new Handler();
    private Runnable runnable;
    private ListView lvBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        EditText etSearch = findViewById(R.id.etSearch_del);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = etSearch.getText().toString().trim();
                    performSearch(searchText); // 執行搜尋
                    return true;
                }
                return false;
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);
        btnOpenMenu = findViewById(R.id.btn_open_menu);
        viewPager = findViewById(R.id.vp_news);
        lvBooks = findViewById(R.id.lvBooks);
        storeSigninInfo("ken22i22i22i");
        NavigationView navigationView = findViewById(R.id.nav_view);
        //側拉選單
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

        //輪播news
        imageList.add(R.drawable.activity_1);
        imageList.add(R.drawable.activity_2);
        imageList.add(R.drawable.activity_3);

        // 對應的網址
        urlList.add("https://www.fcu.edu.tw/event/?id=49F304433F0744C2B40C5584A787F179");
        urlList.add("https://www.fcu.edu.tw/event/?id=62B13E82E6CF416EB1F521A2CE35468D");
        urlList.add("https://www.fcu.edu.tw/event/?id=9BA4849F61064028BEE0B84192764AD2");

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, imageList, urlList);
        viewPager.setAdapter(adapter);
        // 設置自動輪播
        runnable = new Runnable() {
            @Override
            public void run() {
                int currentItem = viewPager.getCurrentItem();
                int totalItemCount = adapter.getItemCount();
                int nextItem = (currentItem + 1) % totalItemCount;
                viewPager.setCurrentItem(nextItem, true);
                handler.postDelayed(this, 3000); // 每3秒切換一次
            }
        };
        handler.postDelayed(runnable, 3000); // 開始輪播


        //創建review列表
        List<Review> reviews1 = new ArrayList<>();
        reviews1.add(new Review("Steven", "Great book!", R.drawable.person1, 3));
        reviews1.add(new Review("jom", "Very informative.", R.drawable.person2, 4));
        List<Review> reviews2 = new ArrayList<>();
        reviews2.add(new Review("Ken", "Excellent resource", R.drawable.person3, 5));
        reviews2.add(new Review("toby", "Highly recommended.", R.drawable.person2, 1));
        List<Review> reviews3 = new ArrayList<>();
        reviews3.add(new Review("jully", "A must-read for developers.", R.drawable.person5, 2));
        reviews3.add(new Review("JJ", "Interesting insights.", R.drawable.person4, 4));
        List<Review> reviews4 = new ArrayList<>();
        reviews4.add(new Review("OWO", "A must-read for developers.", R.drawable.person1, 2));
        reviews4.add(new Review("JJ", "Interesting insights.", R.drawable.person4, 5));
        List<Review> reviews5 = new ArrayList<>();
        reviews5.add(new Review("qq", "Excellent resource", R.drawable.person1, 5));
        reviews5.add(new Review("jonny", "Highly recommended.", R.drawable.person2, 1));

        //書籍列表
        //Book book1 = new Book(R.drawable.book1, "WordPress網站架設實務", 452, 1, "何敏煌", reviews1);
        //book1.calculateScore();
        //Book book2 = new Book(R.drawable.book2, "R資料科學 (第2版)", 387, 1, "Hadley Wickham", reviews2);
       // book2.calculateScore();
        //Book book3 = new Book(R.drawable.book3, "用ChatGPT詠唱來點亮React&前端技能樹", 3879, 1, "一宵三筵 (黃韻儒)", reviews3);
        //book3.calculateScore();
        //Book book4 = new Book(R.drawable.book4, "人工智能的第一性原理: 熵與訊息引擎", 39, 1, "周輝龍", reviews4);
        //book4.calculateScore();
        //Book book5 = new Book(R.drawable.book5, "新一代Keras 3.x重磅回歸: 跨TensorFlow與PyTorch建構Transformer、CNN、RNN、LSTM深度學習模型", 87, 1, "陳會安", reviews5);
        //book5.calculateScore();
        List<Book> books = new ArrayList<>();
        //books.add(book1);
       // books.add(book2);
        //books.add(book3);
        //books.add(book4);
        //books.add(book5);
        /*
        Book_listAdapter lvadapter = new Book_listAdapter(this,books);
        lvBooks.setAdapter(lvadapter);
        */

        loadBooksFromFirebase();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 清除Handler回調，避免內存泄漏
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            Toast.makeText(MainActivity.this, "首頁選項被選中", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        } else if (itemId == R.id.nav_books) {
            Toast.makeText(MainActivity.this, "書籍選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_settings) {
            Toast.makeText(MainActivity.this, "設置選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_book_register) {
            Intent intent = new Intent(MainActivity.this, book_register.class);
            //intent.setClass(MainActivity.this, book_register.class);
            MainActivity.this.startActivity(intent);
        } else if (itemId == R.id.nav_delete) {
            Intent intent = new Intent(MainActivity.this, DeleteBooksActivity.class);
            startActivity(intent);
        } else if(itemId == R.id.nav_log){
            Intent intent = new Intent(MainActivity.this,account_management.class);
            intent.setClass(MainActivity.this, account_management.class);
            MainActivity.this.startActivity(intent);
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

    private void storeSigninInfo(String message) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Signin-Log");
        ref.setValue(new Date().getTime() + ":" + message);
    }

    private void loadBooksFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        List<Book> books = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        GenericTypeIndicator<List<Review>> t = new GenericTypeIndicator<List<Review>>() {};
                        List<Review> reviews = snapshot.child("reviews").getValue(t);
                        book.calculateScore(); // 計算書籍的評分
                        books.add(book);
                    }
                }
                Book_listAdapter lvadapter = new Book_listAdapter(MainActivity.this, books);
                lvBooks.setAdapter(lvadapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "讀取數據失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        //書籍列表被點擊
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "item被選中" + id, Toast.LENGTH_SHORT).show();
                Book selectedBook = books.get(position);

                Intent intent = new Intent(MainActivity.this, Book_detail_Activity.class);
                intent.putExtra("bookImage", selectedBook.getImageUrl());
                intent.putExtra("bookTitle", selectedBook.getName());
                intent.putExtra("ratingStars", selectedBook.getScore());
                intent.putExtra("Author", selectedBook.getAuthor());
                intent.putExtra("bookReviews", new ArrayList<>(selectedBook.getReviews()));

                startActivity(intent);
            }
        };
        lvBooks.setOnItemClickListener(itemClickListener);


    }
    private void performSearch(String searchText) {

        loadBooksFromFirebaseWithSearch(searchText);
    }
    private void loadBooksFromFirebaseWithSearch(String searchText) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");
        List<Book> filteredBooks = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                filteredBooks.clear(); // 清空上一次的搜尋結果

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        // 判斷書名或作者中是否包含使用者輸入的文字
                        if (book.getName().toLowerCase().contains(searchText.toLowerCase()) ||
                                book.getAuthor().toLowerCase().contains(searchText.toLowerCase())) {
                            book.calculateScore(); // 計算書籍的評分
                            filteredBooks.add(book);
                        }
                    }
                }
                // 將過濾後的書籍列表顯示在新的視圖中
                displayFilteredBooks(filteredBooks);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "讀取數據失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void displayFilteredBooks(List<Book> filteredBooks) {
        // 將過濾後的書籍列表顯示在新的視圖中，可以是另一個 Activity 或者 Fragment
        // 這裡展示如何在新的 Activity 中顯示列表

        Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
        intent.putParcelableArrayListExtra("filteredBooks", new ArrayList<>(filteredBooks)); // 使用 putParcelableArrayListExtra
        startActivity(intent);
    }
}

