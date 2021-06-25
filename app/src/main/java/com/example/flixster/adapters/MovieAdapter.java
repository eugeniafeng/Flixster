package com.example.flixster.adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.MainActivity;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.MovieTrailerActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public static final String TAG = "MovieAdapter";

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ItemMovieBinding itemMovieBinding;

        public ViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            itemMovieBinding.getRoot().setOnClickListener(this);
        }

        public void bind(Movie movie) {
            itemMovieBinding.tvTitle.setText(movie.getTitle());
            itemMovieBinding.tvOverview.setText(movie.getOverview());
            String imageUrl;
            int placeholder;
            // if phone is in landscape
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
            Glide.with(context)
                    .load(imageUrl)
                    .centerCrop()
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .placeholder(placeholder)
                    .into(itemMovieBinding.ivPoster);

            // Listener to play movie trailer when image is clicked
            itemMovieBinding.ivPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Clicked video");
                    if (movie.getVideoId() == null || movie.getVideoId().isEmpty()) {
                        String requestUrl = "https://api.themoviedb.org/3/movie/" + movie.getId() + "/videos?api_key=" + context.getString(R.string.themoviedb_api_key);
                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(requestUrl, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                Log.d(TAG, "onSuccess");
                                JSONObject jsonObject = json.jsonObject;
                                int index = 0;
                                try {
                                    JSONArray results = jsonObject.getJSONArray("results");
                                    Log.i(TAG, "Results: " + results.toString());

                                    // check if the site is YouTube - if does not exist, will produce an error
                                    while (!results.getJSONObject(index).getString("site").equals("YouTube")) {
                                        index++;
                                        Log.d(TAG, results.getJSONObject(index).getString("site"));
                                    }

                                    movie.setVideoId(results.getJSONObject(index).getString("key"));
                                    Log.d(TAG, "https://www.youtube.com/watch?v=" + movie.getVideoId());

                                    // Create the new activity
                                    Intent i = new Intent(context, MovieTrailerActivity.class);
                                    // Pass the data being edited
                                    i.putExtra("videoId", movie.getVideoId());
                                    // Display the activity
                                    context.startActivity(i);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Hit json exception", e);
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.d(TAG, "onFailure");
                            }
                        });
                    }
                    else {
                        // Create the new activity
                        Intent i = new Intent(context, MovieTrailerActivity.class);
                        // Pass the data being edited
                        i.putExtra("videoId", movie.getVideoId());
                        // Display the activity
                        context.startActivity(i);
                    }
                }
            });
        }

        // When the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View v) {
            // get item position
            int position = getAdapterPosition();
            // make sure the position is valid (actually exists in the view)
            if (position != RecyclerView.NO_POSITION) {
                Movie movie = movies.get(position);
                // intent for new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }
    }
}
