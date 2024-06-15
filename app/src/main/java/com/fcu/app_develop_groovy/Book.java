package com.fcu.app_develop_groovy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Book implements Parcelable {
    private String imageUrl;
    private String name;
    private String Borrowed;
    private int score;
    private String author;
    private List<Review> reviews;

    public Book(String imageUrl, String name, String borrowed, int score, String author, List<Review> reviews) {
        this.imageUrl = imageUrl;
        this.name = name;
        Borrowed = borrowed;
        this.score = score;
        this.author = author;
        this.reviews = reviews;
    }

    // Parcelable 的建構子，用於反序列化
    protected Book(Parcel in) {
        imageUrl = in.readString();
        name = in.readString();
        Borrowed = in.readString();
        score = in.readInt();
        author = in.readString();
        reviews = in.createTypedArrayList(Review.CREATOR);
    }
    public Book(){

    }
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    // 寫入序列化數據到 Parcel 對象中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeString(name);
        dest.writeString(Borrowed);
        dest.writeInt(score);
        dest.writeString(author);
        dest.writeTypedList(reviews);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBorrowed(String borrowed) {
        Borrowed = borrowed;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public String getBorrowed() {
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
