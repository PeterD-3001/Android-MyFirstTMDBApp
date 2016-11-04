package be.teknyske.myfirsttmdbapp;

import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.model.Genre;
import com.omertron.themoviedbapi.model.credits.MediaCreditCast;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.net.URL;
import java.util.ArrayList;

import be.teknyske.myfirsttmdbapp.app.AppController;

public class MovieDetailActivity extends AppCompatActivity
                                 implements AppController.OnMovieListChangedListener
{

    private ImageView imageView;
    private TextView titleTextView;
    private TextView taglineTextView;
    private TextView descriptionTextview;
    private TextView releaseDateView;
    private TextView runTimeTextView;
    private TextView genresTextView;
    // private ArrayList<Genre> genres;
    private String genrelijst = "";
    private TextView avgVoteTextView;
    private LinearLayout myCastGallery;


    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);

        MovieInfo movieInfo = (MovieInfo) getIntent().getExtras().get("Movie");

        imageView = (ImageView) findViewById(R.id.imageView);
        titleTextView = (TextView) findViewById(R.id.title);
        taglineTextView = (TextView) findViewById(R.id.tagline);
        descriptionTextview = (TextView) findViewById(R.id.description);
        releaseDateView = (TextView) findViewById(R.id.releasedate);
        runTimeTextView = (TextView) findViewById(R.id.runtime);
        genresTextView = (TextView) findViewById(R.id.genres);
        avgVoteTextView = (TextView) findViewById(R.id.avgvote);
        myCastGallery = (LinearLayout) findViewById(R.id.mycastgallery);


        URL imageUrl = AppController.getInstance().createMyImageUrl(movieInfo.getBackdropPath(), "w500");
        ImageLoader.getInstance().displayImage(imageUrl.toString(), imageView);
        // Oude manier die ImageLoader NIET gebruikt imageView.setImageResource(movieInfo.getPosterPath());
        titleTextView.setText(movieInfo.getTitle());
        taglineTextView.setText(movieInfo.getTagline());
        descriptionTextview.setText(movieInfo.getOverview());
        releaseDateView.setText(movieInfo.getReleaseDate());
        runTimeTextView.setText(movieInfo.getRuntime() + " minutes.");



        // String met genres samenstellen..
        for (Genre genre : movieInfo.getGenres())
            {
            genrelijst += genre.getName() + " ";
            }
        genresTextView.setText(genrelijst);
        avgVoteTextView.setText(movieInfo.getVoteAverage() + " / 10 ");

        AppController.getInstance().fetchCast(movieInfo.getId());

        // System.out.println(movieInfo.toString());
        }

    @Override
    protected void onResume()
        {
        super.onResume();
        AppController.getInstance().addOnMovieListChangedListener(this);
        }

    @Override
    protected void onPause()
        {
        super.onPause();
        AppController.getInstance().removeOnMovieListChangedListener(this);
        }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
        {
        switch (item.getItemId())
            {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }
        return super.onOptionsItemSelected(item);
        }

    @Override
    public void onMovieListChanged()
        {
        for (MediaCreditCast castMember : AppController.getInstance().cast)
            {
            myCastGallery.addView(insertPicture(castMember.getArtworkPath()));
            }
        }

    private View insertPicture(String artworkPath)
        {
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(92, 120));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        if (artworkPath != null)
            {
            ImageLoader.getInstance().displayImage(AppController.getInstance().createMyImageUrl(artworkPath, "w92").toString(), imageView);
            }
        else
            {
            ImageLoader.getInstance().displayImage(null, imageView);
            }
        return imageView;
        }

}
