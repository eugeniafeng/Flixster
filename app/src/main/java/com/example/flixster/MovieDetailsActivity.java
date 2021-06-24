package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));

        binding.tvDetailsTitle.setText(movie.getTitle());
        binding.tvDetailsOverview.setText(movie.getOverview());
        binding.tvVoteCount.setText(movie.getVoteCount());
        binding.tvReleaseDate.setText(movie.getReleaseDate());
        binding.rbVoteAverage.setRating(movie.getVoteAverage().floatValue() / 2.0f);

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

        int radius = 30;
        int margin = 10;
        Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .transform(new RoundedCornersTransformation(radius, margin))
                .placeholder(placeholder)
                .into(binding.ivDetailsPoster);
    }
}