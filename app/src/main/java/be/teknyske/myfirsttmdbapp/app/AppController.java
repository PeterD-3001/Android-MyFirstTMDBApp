package be.teknyske.myfirsttmdbapp.app;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.model.movie.MovieInfo;
import com.omertron.themoviedbapi.results.ResultList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import be.teknyske.myfirsttmdbapp.MainActivity;
import be.teknyske.myfirsttmdbapp.MovieDetailActivity;
import be.teknyske.myfirsttmdbapp.MovieListAdapter;
import be.teknyske.myfirsttmdbapp.R;

import static be.teknyske.myfirsttmdbapp.R.id.image;
import static be.teknyske.myfirsttmdbapp.R.id.recyclerView;

/**
 * Created by cerseilannister on 25/10/16.
 **/

// Inferno ID = 207932

public class AppController extends Application
{
    private static AppController instance;
    private TheMovieDbApi api;
    private List<MovieBasic> movieList = new ArrayList<>();
    private Configuration configuration;
    final static String API_KEY = "4d47b8fb2f4125a5bd76ab0f464a6b5f";

    private List<OnMovieListChangedListener> allListeners = new ArrayList<>();

    // Appcontroller
    public static synchronized AppController getInstance()
        {
        return instance;
        }

    @Override
    public void onCreate()
        {
        super.onCreate();
        instance = this;
        try
            {
            api = new TheMovieDbApi(API_KEY);
            AppController.FetchConfiguration fetchConfiguration = new AppController.FetchConfiguration();
            // AsyncTask 1
            fetchConfiguration.execute();

            AppController.FetchMovieInfo fetchMovieInfo = new AppController.FetchMovieInfo();
            // AsyncTask 2
            fetchMovieInfo.execute();

            // This is too early !!!
            //AppController.FetchOneMovieInfo fetchOneMovieInfo = new AppController.FetchOneMovieInfo();
            //fetchOneMovieInfo.execute();

            }
        catch (MovieDbException e)
            {
            e.printStackTrace();
            Log.e("TheMovieDBAPI", "Error: " + e.getMessage());
            }

        DisplayImageOptions defaultOptions =
                new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .showImageForEmptyUri(R.drawable.ic_wait)
                        .showImageOnLoading(R.drawable.ic_wait)
                        .displayer(new FadeInBitmapDisplayer(500))
                        .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                        .defaultDisplayImageOptions(defaultOptions)
                        .denyCacheImageMultipleSizesInMemory()
                        .build();

        ImageLoader.getInstance().init(config);
        }

    public List<MovieBasic> getMovieList()
        {
        return movieList;
        }

    public URL createMyImageUrl(String imagePath, String size)
        {
        URL imageURL = null;
        try
            {
            imageURL = configuration.createImageUrl(imagePath, size);
            }
        catch (MovieDbException e)
            {
            e.printStackTrace();
            }

        return imageURL;
        }

    // ADD/REMOVE SUBSCRIPTIONS
    public void addOnMovieListChangedListener(OnMovieListChangedListener listener)
        {
        allListeners.add(listener);
        }

    public void removeOnMovieListChangedListener(OnMovieListChangedListener listener)
        {
        allListeners.remove(listener);
        }

    // NOTIFY ALL LISTENERS WHEN MOVIELIST-CHANGES
    // OnMovieListChanged is implemented by the listener itself
    public void notifyAllListeners()
        {
        for (OnMovieListChangedListener listener : allListeners)
            {
            listener.onMovieListChanged();
            }
        }



    // ASYNC TASK 1
    private class FetchMovieInfo extends AsyncTask<Void, Void, ResultList<MovieBasic>>
    {
        @Override
        protected ResultList<MovieBasic> doInBackground(Void... params)
            {
            try
                {
                return api.getDiscoverMovies(new Discover());
                }
            catch (MovieDbException e)
                {
                e.printStackTrace();
                }
            return null;
            }

        @Override
        protected void onPostExecute(ResultList<MovieBasic> movieBasicResultList)
            {
            super.onPostExecute(movieBasicResultList);
            Log.v("Found", movieBasicResultList.toString());
            movieList.clear();
            movieList.addAll(movieBasicResultList.getResults());
            // ADDED
            AppController.this.notifyAllListeners();
            }
    }

    // ASYNC TASK 2
    private class FetchConfiguration extends AsyncTask<Void, Void, Configuration>
    {
        @Override
        protected Configuration doInBackground(Void... params)
            {
            try
                {
                return api.getConfiguration();
                } catch (MovieDbException e)
                {
                e.printStackTrace();
                }
            return null;
            }

        @Override
        protected void onPostExecute(Configuration configuration)
            {
            AppController.this.configuration = configuration;
            // movieListAdapter = new MovieListAdapter(movieList, AppController.this, configuration);
            // recyclerView.setAdapter(movieListAdapter);
            // super.onPostExecute(configuration);
            }
    }

    // ASYNC TASK 3:
    private class FetchOneMovieInfo extends AsyncTask<Integer, Void, MovieInfo>
        // <input-type / progress / output-type>
    {
        @Override
        protected MovieInfo doInBackground(Integer ... params)
        // Meaning: params is an array of an unknow number of parameters of type Integer
            {
            try
                {
                return api.getMovieInfo(params [0].intValue(), "en");
                // Parameter params[0] is the Id of the selected movie !
                }
            catch (MovieDbException e)
                {
                e.printStackTrace();
                }
            return null;
            }

        @Override
        protected void onPostExecute(MovieInfo movieInfo)
            {
            // super.onPostExecute(movieBasicResultList);
            Log.v("Found", movieInfo.toString());

            Intent movieDetailIntent = new Intent(AppController.this, MovieDetailActivity.class);
            movieDetailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            movieDetailIntent.putExtra("Movie", movieInfo);
            startActivity(movieDetailIntent);
            }
    }

    public void fetchOneMovie(int movieId)
        {
        AppController.FetchOneMovieInfo fetchOneMovieInfo = new AppController.FetchOneMovieInfo();
        fetchOneMovieInfo.execute(movieId);
        }



    // INTERFACE-SPECIFICATIE
    public interface OnMovieListChangedListener
    {
        void onMovieListChanged();
    }
}
