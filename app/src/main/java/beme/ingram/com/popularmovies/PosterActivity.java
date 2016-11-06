package beme.ingram.com.popularmovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import beme.ingram.com.popularmovies.fragments.PosterDetailFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterActivity extends AppCompatActivity {

    String backDropPath;
    @BindView(R.id.backdrop)
    ImageView backDrop;
    @BindView(R.id.collapsing_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle(extras.getString(Utils.TITLE));

        backDropPath = extras.getString(Utils.BACKDROP);

        afterRenderedBackdrop(backDrop);

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.transparent));

        PosterDetailFragment posterDetailFragment = new PosterDetailFragment();
        posterDetailFragment.setArguments(extras);
        getSupportFragmentManager().beginTransaction().add(R.id.poster_fragment,posterDetailFragment,null).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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



}
