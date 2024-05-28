package com.fcu.app_develop_groovy;

public class Book {
    private int BookImageId;
    private String name;
    private int Borrowed;
    private int score;
    private String author;

    public Book(int bookImageId, String name, int borrowed, int score, String author) {
        BookImageId = bookImageId;
        this.name = name;
        Borrowed = borrowed;
        this.score = score;
        this.author = author;
    }

    public int getBookImageId() {
        return BookImageId;
    }

    public String getName() {
        return name;
    }

    public int getBorrowed() {
        return Borrowed;
    }

    public int getScore() {
        return score;
    }

    public String getAuthor() {
        return author;
    }
}
