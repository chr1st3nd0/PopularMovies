package beme.ingram.com.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PosterDetailFragment extends Fragment {

    View rootView;
    @BindView(R.id.movie_image)ImageView movieImage;
    @BindView(R.id.release_date)TextView releaseDate;
    @BindView(R.id.synopsis)TextView synopsis;
    @BindView(R.id.vote_average)TextView voteAverage;
    @BindView(R.id.movie_title)TextView movieTitle;

    String posterPath;

    public PosterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        ButterKnife.bind(this,rootView);
        posterPath = getArguments().getString(Utils.POSTER_IMAGE);
        movieTitle.setText(getArguments().getString(Utils.TITLE));
        releaseDate.setText( getActivity().getResources().getString(R.string.released_label)+ " " + Utils.formatDate(getArguments().getString(Utils.RELEASE_DATE)));
        synopsis.setText(getArguments().getString(Utils.OVERVIEW));
        voteAverage.setText(getArguments().getString(Utils.VOTE_AVERAGE));

        afterRenderedPoster(movieImage);


        ButterKnife.bind(this,rootView);

        return rootView;
    }

    private void afterRenderedPoster(final View view)
    {
        ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                getPoster(view);
                ViewTreeObserver obs = view.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
            }
        });
    }



    private void getPoster(View view)
    {
        Glide.with(this).load(posterPath).into((ImageView) view);
    }

}
