package be.teknyske.myfirsttmdbapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.omertron.themoviedbapi.MovieDbException;
import com.omertron.themoviedbapi.TheMovieDbApi;
import com.omertron.themoviedbapi.model.config.Configuration;
import com.omertron.themoviedbapi.model.discover.Discover;
import com.omertron.themoviedbapi.model.movie.MovieBasic;
import com.omertron.themoviedbapi.results.ResultList;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import be.teknyske.myfirsttmdbapp.app.AppController;

import static be.teknyske.myfirsttmdbapp.R.id.imageView;

/*
https://developers.themoviedb.org/3/getting-started
*/


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AppController.OnMovieListChangedListener
{
    /*
    Moved to AppController
    // private TheMovieDbApi api;
    //final static String API_KEY = "4d47b8fb2f4125a5bd76ab0f464a6b5f";
    //private List<MovieBasic>  movieList = new ArrayList<>();
    */

    // private Configuration configuration;

    private RecyclerView recyclerView;
    private MovieListAdapter movieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Floating Action Button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
                {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                }
        });

        // Drawer Layout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ADDED===================================================
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieListAdapter = new MovieListAdapter(AppController.getInstance().getMovieList(), MainActivity.this);
        recyclerView.setAdapter(movieListAdapter);

        }

    // ADD/REMOVE SUBSCRIPTIONS WHEN APP STARTS / STOPS
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

    // has to be implemented as mainactivity implements interface appcontroller.onmovielistchangedlistener
    @Override
    public void onMovieListChanged()
        {
        movieListAdapter.notifyDataSetChanged();
        }

    @Override
    public void onBackPressed()
        {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
            {
            drawer.closeDrawer(GravityCompat.START);
            } else
            {
            super.onBackPressed();
            }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
        {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
        {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            {
            return true;
            }

        return super.onOptionsItemSelected(item);
        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
        {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera)
            {
            // Handle the camera action
            } else if (id == R.id.nav_gallery)
            {

            } else if (id == R.id.nav_slideshow)
            {

            } else if (id == R.id.nav_manage)
            {

            } else if (id == R.id.nav_share)
            {

            } else if (id == R.id.nav_send)
            {

            }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }


}
