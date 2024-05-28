package com.fcu.app_develop_groovy;


import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;


public class Book_data {
    SQLiteDatabase database;

    public void setDatabase(SQLiteDatabase.CursorFactory mode) {
        database = SQLiteDatabase.openOrCreateDatabase("bookdata.db",mode,null);
        String table = "CREATE TABLE book(_id PRIMARY KEY,bookname TEXT,author TEXT,isbn INTEGER,publishing TEXT,money INTEGER,category TEXT)";
        database.execSQL(table);
    }
    public void addDatabase(String bookname,String authorname,String isbn,String pulishingname,int money,String category_str){
        String add = "INSERT INTO book(bookname,author,isbn,publishing,money,category) values ('" + bookname + "','" + authorname + "'," + isbn + ",'" +
                pulishingname + "'," + money + ",'" + category_str + "')";
        ContentValues cv = new ContentValues();
        cv.put("bookname",bookname);
        cv.put("author",authorname);
        cv.put("isbn",isbn);
        cv.put("publishing",pulishingname);
        cv.put("money",money);
        cv.put("category",category_str);
        database.insert("table",null,cv);
    }
    public List<Book> searchDatabase(String bookname, String authorname, String isbn, String pulishingname, int money, String category_str)
    {
        String search = "";

        if (bookname.equals("") != true)
            search += "bookname="+bookname;
        if(authorname.equals("") != true)
            search += "author="+authorname;
        if(isbn.equals("") != true)
            search += "isbn=" + isbn;
        if(pulishingname.equals("") != true)
            search += "publishing=" + pulishingname;
        if(money != 0)
            search += "money=" + money;
        if(category_str.equals("") != true)
            search += "category=" + category_str;
        Cursor table = database.query("table",null,search,null,null,null,null,null);//要寫資料庫的search最後的return
    }
}
