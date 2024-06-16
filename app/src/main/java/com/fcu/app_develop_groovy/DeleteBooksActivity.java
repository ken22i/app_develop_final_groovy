package com.fcu.app_develop_groovy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteBooksActivity extends AppCompatActivity {
    private ListView lvBooksToDelete;
    private EditText etSearch;
    private Button delete_back;
    List<Book> filteredBooks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_books);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        lvBooksToDelete = findViewById(R.id.lv_del);
        etSearch = findViewById(R.id.etSearch_del);
        delete_back = findViewById(R.id.delete_back);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = etSearch.getText().toString().trim();
                    loadBooksFromFirebaseWithSearch(searchText); // 執行搜尋
                    return true;
                }
                return false;
            }
        });
        delete_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteBooksActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(DeleteBooksActivity.this, "item被選中" + id, Toast.LENGTH_SHORT).show();
                Book selectedBook = filteredBooks.get(position);
                // 彈出確認刪除的對話框
                showDeleteConfirmationDialog(selectedBook);
            }
        };
        lvBooksToDelete.setOnItemClickListener(itemClickListener);



    }
    private void loadBooksFromFirebaseWithSearch(String searchText) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("books");


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

                Book_listAdapter lvadapter = new Book_listAdapter(DeleteBooksActivity.this, filteredBooks);
                lvBooksToDelete.setAdapter(lvadapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteBooksActivity.this, "讀取數據失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
    private void showDeleteConfirmationDialog(Book bookToDelete) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("確認刪除");
        builder.setMessage("確定要刪除書籍：" + bookToDelete.getName() + "？");

        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 確定刪除，調用方法從 Firebase 數據庫中刪除書籍
                deleteBookFromFirebase(bookToDelete);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 取消操作，關閉對話框
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void deleteBookFromFirebase(Book bookToDelete) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference booksRef = database.getReference("books");

        // 找到要刪除的書籍在 Firebase 中的節點，假設每本書有唯一的 ID
        booksRef.orderByChild("name").equalTo(bookToDelete.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue(); // 刪除該書籍節點
                }
                Toast.makeText(DeleteBooksActivity.this, "成功刪除書籍：" + bookToDelete.getName(), Toast.LENGTH_SHORT).show();
                // 刷新列表或執行其他操作
                loadBooksFromFirebaseWithSearch(""); // 可以重新加載數據或清空搜尋
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteBooksActivity.this, "刪除書籍失敗：" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}