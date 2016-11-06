package beme.ingram.com.popularmovies.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import beme.ingram.com.popularmovies.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PosterActivityFragment extends Fragment {

    public PosterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_poster_detail, container, false);
    }
}
