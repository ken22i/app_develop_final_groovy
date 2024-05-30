package com.fcu.app_develop_groovy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ReviewListAdapter extends BaseAdapter {
    private Context context;
    private List<Review> reviews;
    public ReviewListAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }
    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.review_item,parent,false);
        }
        ImageView review_image = convertView.findViewById(R.id.review_imge);
        TextView review_name = convertView.findViewById(R.id.tv_review_name);
        TextView review_content = convertView.findViewById(R.id.tv_review_content);
        ImageView review_score_image = convertView.findViewById(R.id.score);

        Review review = reviews.get(position);
        review_image.setImageResource(review.getReviewImage());
        review_name.setText(review.getReviewerName());
        review_content.setText(review.getReviewContent());
        switch (review.getReview_score()){
            case 1:
                review_score_image.setImageResource(R.drawable.star1);
                break;
            case 2:
                review_score_image.setImageResource(R.drawable.star2);
                break;
            case 3:
                review_score_image.setImageResource(R.drawable.star3);
                break;
            case 4:
                review_score_image.setImageResource(R.drawable.star4);
                break;
            case 5:
                review_score_image.setImageResource(R.drawable.star5);
                break;
        }

        return convertView;
    }
}
