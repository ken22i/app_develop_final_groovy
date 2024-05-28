package com.fcu.app_develop_groovy;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
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
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        btnOpenMenu = findViewById(R.id.btn_open_menu);
        viewPager = findViewById(R.id.vp_news);
        lvBooks = findViewById(R.id.lvBooks);
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

        ImageSliderAdapter adapter = new ImageSliderAdapter(this,imageList,urlList);
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

        //書籍列表
        Book book1 = new Book(R.drawable.book1,"WordPress網站架設實務",452,4,"何敏煌");
        Book book2 = new Book(R.drawable.book2,"R資料科學 (第2版)",387,5,"Hadley Wickham");
        Book book3 = new Book(R.drawable.book3,"用ChatGPT詠唱來點亮React&前端技能樹",3879,3,"一宵三筵 (黃韻儒)");
        Book book4 = new Book(R.drawable.book4,"人工智能的第一性原理: 熵與訊息引擎",39,2,"周輝龍");
        Book book5 = new Book(R.drawable.book5,"新一代Keras 3.x重磅回歸: 跨TensorFlow與PyTorch建構Transformer、CNN、RNN、LSTM深度學習模型",87,1,"陳會安");
        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        Book_listAdapter lvadapter = new Book_listAdapter(this,books);
        lvBooks.setAdapter(lvadapter);
        //書籍列表被點擊
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "item被選中" + id, Toast.LENGTH_SHORT).show();
            }
        };
        lvBooks.setOnItemClickListener(itemClickListener);


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable); // 清除Handler回調，避免內存泄漏
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.nav_home){
            Toast.makeText(MainActivity.this, "首頁選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_books) {
            Toast.makeText(MainActivity.this, "書籍選項被選中", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_settings) {
            Toast.makeText(MainActivity.this, "設置選項被選中", Toast.LENGTH_SHORT).show();
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

