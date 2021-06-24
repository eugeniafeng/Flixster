package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    TextView tvReleaseDate;
    TextView tvVoteCount;
    RatingBar rbVoteAverage;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvDetailsTitle);
        tvOverview = (TextView) findViewById(R.id.tvDetailsOverview);
        tvReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);
        tvVoteCount = (TextView) findViewById(R.id.tvVoteCount);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) findViewById(R.id.ivDetailsPoster);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        tvVoteCount.setText("" + movie.getVoteCount() + " Votes");
        tvReleaseDate.setText("Released " + movie.getReleaseDate());
        rbVoteAverage.setRating(movie.getVoteAverage().floatValue() / 2.0f);

        String imageUrl;
        int placeholder;
        // if phone is in landscape
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // then imageUrl = back drop image
            imageUrl = movie.getBackdropPath();
            placeholder = R.drawable.backdrop_placeholder;
        } else {
            // else imageUrl = poster image
            imageUrl = movie.getPosterPath();
            placeholder = R.drawable.placeholder;
        }
        Glide.with(this).load(imageUrl).placeholder(placeholder).into(ivPoster);
    }
}