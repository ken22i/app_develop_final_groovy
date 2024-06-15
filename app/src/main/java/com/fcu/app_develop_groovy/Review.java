package com.fcu.app_develop_groovy;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
    private String reviewerName;
    private String reviewContent;
    private int reviewImage;
    private int review_score;

    public Review(String reviewerName, String reviewContent, int reviewImage, int review_score) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.review_score = review_score;
    }
    public Review(){

    }


    // Parcelable 的建構子，用於反序列化
    protected Review(Parcel in) {
        reviewerName = in.readString();
        reviewContent = in.readString();
        reviewImage = in.readInt();
        review_score = in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    // 寫入序列化數據到 Parcel 對象中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewerName);
        dest.writeString(reviewContent);
        dest.writeInt(reviewImage);
        dest.writeInt(review_score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public int getReviewImage() {
        return reviewImage;
    }

    public int getReview_score() {
        return review_score;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewImage(int reviewImage) {
        this.reviewImage = reviewImage;
    }

    public void setReview_score(int review_score) {
        this.review_score = review_score;
    }
}
