package beme.ingram.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import beme.ingram.com.popularmovies.adapters.PoserAdapter;
import beme.ingram.com.popularmovies.adapters.TabAdapter;
import beme.ingram.com.popularmovies.fragments.MoviePostersPopularFragment;
import beme.ingram.com.popularmovies.fragments.MoviePostersRatedFragment;
import beme.ingram.com.popularmovies.fragments.PosterDetailFragment;
import beme.ingram.com.popularmovies.models.Movie;

public class MainActivity extends AppCompatActivity implements PoserAdapter.ShowDetails {


    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewpager_create);
        setupViewPager(viewPager);



        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new MoviePostersPopularFragment(),getResources().getString(R.string.popular_rated_label));
        adapter.addFragment(new MoviePostersRatedFragment(), getResources().getString(R.string.highest_rated_label));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDetails(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putString(Utils.TITLE,movie.getTitle());
        bundle.putString(Utils.BACKDROP,movie.getBackdropPath());
        bundle.putString(Utils.POSTER_IMAGE,movie.getPosterPath());
        bundle.putString(Utils.OVERVIEW,movie.getOverView());
        bundle.putString(Utils.OVERVIEW,movie.getReleaseDate());
        bundle.putString(Utils.VOTE_AVERAGE,movie.getVote_average());

        PosterDetailFragment posterDetailFragment = new PosterDetailFragment();
        posterDetailFragment.setArguments(bundle);

        Intent intent = new Intent(this, PosterActivity.class);
//        String message = editText.getText().toString();
        intent.putExtra(Utils.TITLE,movie.getTitle());
        intent.putExtra(Utils.BACKDROP,movie.getBackdropPath());
        intent.putExtra(Utils.POSTER_IMAGE,movie.getPosterPath());
        intent.putExtra(Utils.OVERVIEW,movie.getOverView());
        intent.putExtra(Utils.RELEASE_DATE,movie.getReleaseDate());
        intent.putExtra(Utils.VOTE_AVERAGE,movie.getVote_average());
        startActivity(intent);
    }
}
