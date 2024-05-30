package com.fcu.app_develop_groovy;


import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;


public class Book_data {
    SQLiteDatabase database;

    public Book_data(SQLiteDatabase bookData) {
    }
//"CREATE TABLE IF NOT EXISTS book(_id INTEGER PRIMARY KEY,bookname TEXT,author TEXT,isbn INTEGER,publishing TEXT,money INTEGER,category TEXT)"
    public void addDatabase(String bookname,String authorname,String isbn,String pulishingname,int money,String category_str){
        String add = "INSERT INTO book(bookname,author,isbn,publishing,money,category) VALUES ('" + bookname + "','" + authorname + "'," + isbn + ",'" +
                pulishingname + "'," + money + ",'" + category_str + "')";
        database.execSQL(add);
    }
    public Cursor searchDatabase(String bookname, String authorname, String isbn, String pulishingname, int money, String category_str)
    {
        String search = "";

        if (bookname.equals("") != true)
            search += "bookname="+bookname;
        if(authorname.equals("") != true)
            search += search.length() == 0 ? "author="+authorname : ",author="+authorname;
        if(isbn.equals("") != true)
            search += search.length() == 0 ? "isbn="+isbn : ",isbn="+isbn;
        if(pulishingname.equals("") != true)
            search += search.length() == 0 ? "publishing="+pulishingname : ",publishing="+pulishingname;
        if(money != 0)
            search += search.length() == 0 ? "money="+money : ",money="+money;
        if(category_str.equals("") != true)
            search += search.length() == 0 ? "category="+category_str : ",category="+category_str;
        Cursor cursor = database.query("table",null,search,null,null,null,null,null);//要寫資料庫的search最後的return
        return cursor;
    }
}
