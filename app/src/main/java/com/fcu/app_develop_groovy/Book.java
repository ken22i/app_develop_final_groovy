package com.fcu.app_develop_groovy;

import java.util.List;

public class Book {
    private int BookImageId;
    private String name;
    private int Borrowed;
    private int score;
    private String author;
    private List<Review> reviews;

    public Book(int bookImageId, String name, int borrowed, int score, String author, List<Review> reviews) {
        BookImageId = bookImageId;
        this.name = name;
        Borrowed = borrowed;
        this.score = score;
        this.author = author;
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
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
    // 新增計算平均分數的方法
    public void calculateScore() {
        if (reviews != null && !reviews.isEmpty()) {
            int sum = 0;
            for (Review review : reviews) {
                sum += review.getReview_score();
            }
            this.score = Math.round((float) sum / reviews.size());
        } else {
            this.score = 0; // 如果沒有reviews，設置score為0
        }
    }
}

