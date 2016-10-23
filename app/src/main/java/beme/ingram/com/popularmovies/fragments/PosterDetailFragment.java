package beme.ingram.com.popularmovies.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import beme.ingram.com.popularmovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PosterDetailFragment extends Fragment {

    View rootView;

    public PosterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_poster_detail, container, false);

        return rootView;
    }

}
