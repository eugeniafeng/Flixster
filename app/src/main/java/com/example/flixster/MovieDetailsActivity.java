package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;


public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsAct";

    String requestUrl;
    String videoId;
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
        requestUrl = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + getString(R.string.themoviedb_api_key);

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

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(requestUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    videoId = results.getJSONObject(0).getString("key");
                    Log.d(TAG, "https://www.youtube.com/watch?v=" + videoId);
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        binding.ivDetailsPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked video");
                if (!videoId.isEmpty()) {
                    // Create the new activity
                    Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    // Pass the data being edited
                    i.putExtra("videoId", videoId);
                    // Display the activity
                    startActivity(i);
                }
            }
        });

    }
}