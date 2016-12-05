package beme.ingram.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import beme.ingram.com.popularmovies.adapters.FavoritesAdapter;
import beme.ingram.com.popularmovies.adapters.PoserAdapter;
import beme.ingram.com.popularmovies.adapters.TabAdapter;
import beme.ingram.com.popularmovies.fragments.FavoritesFragment;
import beme.ingram.com.popularmovies.fragments.MoviePostersPopularFragment;
import beme.ingram.com.popularmovies.fragments.MoviePostersRatedFragment;
import beme.ingram.com.popularmovies.models.Movie;
import beme.ingram.com.popularmovies.models.MovieParceable;

public class MainActivity extends AppCompatActivity implements PoserAdapter.ShowDetails,FavoritesAdapter.ShowFavoriteDetails {


    ViewPager viewPager;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        adapter.addFragment(new FavoritesFragment(), "Favorites");
        viewPager.setAdapter(adapter);
    }


    @Override
    public void showDetails(Movie movie) {
        MovieParceable movieParceable = new MovieParceable(movie);
        Intent intent = new Intent(this, PosterActivity.class);
        intent.putExtra("myData", movieParceable);
        startActivity(intent);
    }

    @Override
    public void showFavoriteDetails(Movie movie) {
        MovieParceable movieParceable = new MovieParceable(movie);
        Intent intent = new Intent(this, PosterActivity.class);
        intent.putExtra("myData", movieParceable);
        startActivity(intent);
    }
}
