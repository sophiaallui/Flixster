package com.example.flixster.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // context: inflate a view
    Context context;
    List<Movie> movies;

    // Constructor for those two data set points
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Involve inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        // wrap into a viewholder
        return new ViewHolder(movieView);
    }

    /* populating data into the item through holder
        position: take data from this location
        holder: put into this holder
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder" + position);
        //Get the movie at the passed in position
        // already in a list
        Movie movie = movies.get(position);
        // bind the movie into the view holder
        holder.bind(movie);
        
    }

    // Returns the totla count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        // Member variables >> related to the labels from activity_main.xml
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            container = itemView.findViewById(R.id.container);

        }

        public void bind(final Movie movie) {
            // use getter methods to populate the variables
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;


            // render the image, android doesn't have inbuilt way, use library for that
            //Landscape
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
            }else {
                imageUrl = movie.getPosterPath();
            }
            // Portrait
            Glide.with(context).load(imageUrl).into(ivPoster);

            // 1. Register click listener on the whole row <get reference to container element>
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 2. Navigate to a new activity on tap
                    //Toast.makeText(context, movie.getTitle() , Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, DetailActivity.class);
                    //i.putExtra("title", movie.getTitle());
                    // Make movie into a parsebel
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation
                            ((Activity) context, ivPoster, ViewCompat.getTransitionName(ivPoster));
                    context.startActivity(i,options.toBundle());

                }
            });

            }

        }
    }

