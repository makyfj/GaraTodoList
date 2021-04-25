package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

public class GetRatingActivity extends AppCompatActivity {

    private RatingBar ratingRatingBar;
    private Button filterRatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_rating);

        ratingRatingBar = findViewById(R.id.ratingBarFilterRating);
        filterRatingButton = findViewById(R.id.buttonFilterByRating);

        filterRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = ratingRatingBar.getRating();
                int ratingInt = (int)rating;

                Intent intent = new Intent();
                intent.putExtra("result", ratingInt);

                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}