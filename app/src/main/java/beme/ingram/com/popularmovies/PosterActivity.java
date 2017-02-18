package beme.ingram.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import beme.ingram.com.popularmovies.adapters.TabAdapter;
import beme.ingram.com.popularmovies.adapters.YoutubeAdapter;
import beme.ingram.com.popularmovies.fragments.PosterDetailFragment;
import beme.ingram.com.popularmovies.fragments.ReviewFragment;
import beme.ingram.com.popularmovies.models.MovieParceable;
import beme.ingram.com.popularmovies.offline.OfflineMovieParceable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class PosterActivity extends AppCompatActivity implements YoutubeAdapter.ShowTrailer, PosterDetailFragment.SetCollapsableInterface {

    String backDropPath;
    @BindView(R.id.backdrop)
    ImageView backDrop;
    @BindView(R.id.collapsing_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.tab_layout)TabLayout tabLayout;
    @BindView(R.id.viewpager_create)ViewPager viewPager;
    @BindView(R.id.the_app_bar)AppBarLayout appBarLayout;
    PosterDetailFragment posterDetailFragment;
    ReviewFragment reviewFragment;
    boolean offline = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();

        if(bundle.getParcelable("myData") instanceof OfflineMovieParceable)
        {
            OfflineMovieParceable movieParceable = bundle.getParcelable("myData");
            getSupportActionBar().setTitle(movieParceable.getTitle());
            bundle.putParcelable("myData",movieParceable);
            offline = true;
        }
        else
        {
            MovieParceable movieParceable = bundle.getParcelable("myData");
            getSupportActionBar().setTitle(movieParceable.getTitle());
            backDropPath = movieParceable.getBackdrop_path();
            bundle.putParcelable("myData",movieParceable);
            afterRenderedBackdrop(backDrop);

            offline = false;
            reviewFragment = new ReviewFragment();

            reviewFragment.setArguments(bundle);
        }

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));


            posterDetailFragment = new PosterDetailFragment();
            posterDetailFragment.setArguments(bundle);




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        adapter.addFragment(posterDetailFragment,"About");
        if(!offline) {
            adapter.addFragment(reviewFragment, "Reviews");
        }
        viewPager.setAdapter(adapter);
    }
    private void afterRenderedBackdrop(final View view)
    {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                ///getLocationOnScreen here
                getBackdrop();
                ViewTreeObserver obs = view.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
    }

    private void getBackdrop()
    {
        Glide.with(this).load("http://image.tmdb.org/t/p/w500/" + backDropPath).into(backDrop);
    }


    @Override
    public void showTrailers(String videoID) {
        Intent intent = new Intent(this, TrailerActivity.class);
        intent.putExtra(EXTRA_MESSAGE, videoID);
        startActivity(intent);
    }

    @Override
    public void setCollapse(boolean value) {

        appBarLayout.setExpanded(value);
    }
}
