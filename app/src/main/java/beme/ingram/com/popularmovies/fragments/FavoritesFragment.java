package beme.ingram.com.popularmovies.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.adapters.FavoritesAdapter;
import beme.ingram.com.popularmovies.models.Movie;
import beme.ingram.com.popularmovies.sql.FeedReaderContract;
import beme.ingram.com.popularmovies.sql.FeedReaderDbHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoritesFragment extends Fragment {

    ArrayList<Movie> movies;

    View rootView;
    @BindView(R.id.favorites_recycler)RecyclerView favoriteRecycler;
    FavoritesAdapter favoritesAdapter;
    FeedReaderDbHelper feedReaderDbHelper;



    public FavoritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.favorites_layout, container, false);
        ButterKnife.bind(this,rootView);

        movies = new ArrayList<>();



        getFavorites();
        favoriteRecycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        favoriteRecycler.setHasFixedSize(true);

        return rootView;
    }


    private void getFavorites() {

        List<Movie> array = new ArrayList();

        feedReaderDbHelper = new FeedReaderDbHelper(getActivity());
        SQLiteDatabase db = feedReaderDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + FeedReaderContract.FeedEntry.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {

                Movie tempMovie = new Movie();
                tempMovie.setTitle(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.ENTRY_TITLE)));
                tempMovie.setOverView(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.ENTRY_SYNOPSIS)));
                tempMovie.setVote_average(cursor.getString(cursor.getColumnIndex(FeedReaderContract.FeedEntry.ENTRY_RATING)));
                tempMovie.setBuffer(cursor.getBlob(cursor.getColumnIndex(FeedReaderContract.FeedEntry.ENTRY_POSTER)));
                movies.add(tempMovie);
                cursor.moveToNext();
            }
        }

        favoritesAdapter = new FavoritesAdapter(getActivity(),movies);
        favoriteRecycler.setAdapter(favoritesAdapter);

    }

}
