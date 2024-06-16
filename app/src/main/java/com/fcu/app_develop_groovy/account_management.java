package com.fcu.app_develop_groovy;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import androidx.drawerlayout.widget.DrawerLayout;

public class account_management extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText studient_id_input;
    Button back,search,mana_open_menu;
    ListView listView;
    DrawerLayout drawerLayout;
    private String userMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.account_management_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        studient_id_input = findViewById(R.id.edit_account);
        back = findViewById(R.id.btn_management_back);
        search = findViewById(R.id.btn_search);
        listView = findViewById(R.id.management_lw);
        //mana_open_menu = findViewById(R.id.management_open_menu);
        Intent intent = getIntent();
        if(intent != null){
            userMail = intent.getStringExtra("userMail");
        }
        /*
        mana_open_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

         */

        View.OnClickListener backtofirst = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent();
                change.setClass(account_management.this, MainActivity.class);
                account_management.this.startActivity(change);
            }
        };
        /*
        View.OnClickListener btn_search = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllBook();
            }
        };

         */
        //search.setOnClickListener(btn_search);
        getAllBook(userMail);
        back.setOnClickListener(backtofirst);

    }

    private void getAllBook(String userMail) {
        List<Book> open = new ArrayList<>();
        List<String> select = new ArrayList<>();
        String user = userMail;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("books");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book book = snapshot.getValue(Book.class);
                    if (book != null) {
                        open.add(book);
                    }
                }
                for(int i = 0;i < open.size();i++)
                    if(open.get(i).getBorrowed().equals(user))
                        select.add(open.get(i).getName());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        account_management.this, android.R.layout.simple_list_item_1,select);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(account_management.this, "讀取數據失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });//123@gmail.com
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}