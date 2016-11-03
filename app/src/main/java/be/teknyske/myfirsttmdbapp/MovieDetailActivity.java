package be.teknyske.myfirsttmdbapp;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.net.URL;
import java.util.ArrayList;

import be.teknyske.myfirsttmdbapp.app.AppController;

public class MovieDetailActivity extends AppCompatActivity
{

    private ImageView imageView;
    private TextView titleTextView;
    private TextView taglineTextView;
    private TextView descriptionTextview;
    private TextView releaseDateView;
    private TextView runTimeTextView;
    private TextView genresTextView;
    // private ArrayList<Genre> genres;
    private String genrelijst= "";
    private TextView avgVoteTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MovieInfo movieInfo = (MovieInfo) getIntent().getExtras().get("Movie");

        imageView = (ImageView) findViewById(R.id.imageView);
        titleTextView = (TextView) findViewById(R.id.title);
        taglineTextView = (TextView) findViewById(R.id.tagline);
        descriptionTextview = (TextView) findViewById(R.id.description);
        releaseDateView = (TextView) findViewById(R.id.releasedate);
        runTimeTextView = (TextView) findViewById(R.id.runtime);
        genresTextView = (TextView) findViewById(R.id.genres);
        avgVoteTextView = (TextView) findViewById(R.id.avgvote);

        URL imageUrl = AppController.getInstance().createMyImageUrl(movieInfo.getPosterPath(), "w185");
        ImageLoader.getInstance().displayImage(imageUrl.toString(),imageView);
        // imageView.setImageResource(movieInfo.getPosterPath());
        titleTextView.setText(movieInfo.getTitle());
        taglineTextView.setText(movieInfo.getTagline());
        descriptionTextview.setText(movieInfo.getOverview());
        releaseDateView.setText(movieInfo.getReleaseDate());
        runTimeTextView.setText(movieInfo.getRuntime()+" minutes.");

        // String met genres samenstellen..
        for (Genre genre: movieInfo.getGenres())
            {
            genrelijst += genre.getName() + " ";
            }
        genresTextView.setText(genrelijst);
        avgVoteTextView.setText(movieInfo.getVoteAverage()+ " / 10 ");



        System.out.println(movieInfo.toString());
        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
    // Respond to the action bar's Up/Home button
    case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
    }


}
