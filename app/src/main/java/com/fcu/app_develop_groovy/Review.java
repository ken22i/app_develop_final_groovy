package com.fcu.app_develop_groovy;

import java.io.Serializable;

public class Review implements Serializable {
    private String reviewerName;
    private String reviewContent;
    private int reviewImage;
    private int review_score;

    public Review(String reviewerName, String reviewContent, int reviewImage,int review_score) {
        this.reviewerName = reviewerName;
        this.reviewContent = reviewContent;
        this.reviewImage = reviewImage;
        this.review_score = review_score;
    }
    public Review(){

    }
    public void setReview_score(int review_score) {
        this.review_score = review_score;
    }

    public int getReview_score() {
        return review_score;
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

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewImage(int reviewImage) {
        this.reviewImage = reviewImage;
    }
}
