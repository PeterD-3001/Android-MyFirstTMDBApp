package be.teknyske.myfirsttmdbapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.movie.MovieBasic;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import be.teknyske.myfirsttmdbapp.app.AppController;

import static be.teknyske.myfirsttmdbapp.R.id.imageView;

/**
 * Created by cerseilannister on 19/10/16.
 */


public class MovieListAdapter extends RecyclerView.Adapter<MovieViewHolder>
{
    private List<MovieBasic> movieList;
    private Context context;


    public MovieListAdapter(List<MovieBasic> movieList, Context context)
        {
        this.movieList = movieList;
        this.context = context;
        // this.configuration = configuration;
        }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
        // load XML and create viewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MovieViewHolder(view);
        }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
        {
        // Conversie van velden uit MovieBasic-object naar elementen van ViewHolder
        final MovieBasic currentMovie = movieList.get(position);

        holder.getTitleTextView().setText(currentMovie.getTitle());
        holder.getDescriptionTextview().setText(currentMovie.getOverview());
        holder.getReleaseDateView().setText(currentMovie.getReleaseDate());
        holder.getCardView().setOnClickListener
                (new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                                {
                                // start AsyncTask fetchOneMovie indirectly from AppController instead of directly from here!!!
                                AppController.getInstance().fetchOneMovie(currentMovie.getId());
                                }
                        }
                      );

        // toegevoegd
        URL imageUrl = AppController.getInstance().createMyImageUrl(currentMovie.getPosterPath(), "w185");
        ImageLoader.getInstance().displayImage(imageUrl.toString(),holder.getImageView());
        animate(holder);

        }

    @Override
    public int getItemCount()
        {
        return movieList.size();
        }

    public void insertMovie(int position, MovieBasic movie)
        {
        movieList.add(position, movie);
        notifyItemInserted(position);
        }

    public void removeMovie(MovieBasic movie)
        {
        int position = movieList.indexOf(movie);
        movieList.remove(position);
        notifyItemRemoved(position);
        }

    public void animate(RecyclerView.ViewHolder viewHolder)
        {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
        viewHolder.itemView.setAnimation(animation);
        }


}
