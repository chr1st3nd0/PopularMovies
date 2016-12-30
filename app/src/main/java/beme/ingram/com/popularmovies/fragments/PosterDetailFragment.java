package beme.ingram.com.popularmovies.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import beme.ingram.com.popularmovies.DividerItemDecoration;
import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.Utils;
import beme.ingram.com.popularmovies.adapters.YoutubeAdapter;
import beme.ingram.com.popularmovies.models.MovieParceable;
import beme.ingram.com.popularmovies.models.Trailer;
import beme.ingram.com.popularmovies.offline.OfflineMovieParceable;
import beme.ingram.com.popularmovies.sql.FeedReaderContract;
import beme.ingram.com.popularmovies.sql.FeedReaderDbHelper;
import butterknife.BindView;
import butterknife.ButterKnife;

import static beme.ingram.com.popularmovies.R.string.api_key;
import static beme.ingram.com.popularmovies.Utils.getImageBuffer;

/**
 * A simple {@link Fragment} subclass.
 */
public class PosterDetailFragment extends Fragment {

    View rootView;
    @BindView(R.id.movie_image)
    ImageView movieImage;
    @BindView(R.id.release_date)
    TextView releaseDate;
    @BindView(R.id.synopsis)
    TextView synopsis;
    @BindView(R.id.vote_average)
    TextView voteAverage;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.trailer_recycler)
    RecyclerView trailerRecycler;
    @BindView(R.id.fav_heart) ImageView favHeart;
    @BindView(R.id.text_trailer) TextView trailerHeader;
    @BindView(R.id.the_nested_scroll)NestedScrollView nestedScrollView;
    YoutubeAdapter youtubeAdapter;
    ArrayList<Trailer> trailers;

    FeedReaderDbHelper feedReaderDbHelper;
    String posterPath;
    SQLiteDatabase db;

    boolean isLikedAready = false;

    public PosterDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_poster_detail, container, false);
        ButterKnife.bind(this, rootView);


        Bundle bundle = this.getArguments();
        feedReaderDbHelper = new FeedReaderDbHelper(getActivity());
        db = feedReaderDbHelper.getWritableDatabase();

        SetCollapsableInterface setCollapsableInterface = (SetCollapsableInterface) getActivity();


        if(bundle.getParcelable("myData") instanceof OfflineMovieParceable)
        {
            final OfflineMovieParceable movieParceable = bundle.getParcelable("myData");
            setCollapsableInterface.setCollapse(false);

            trailerHeader.setVisibility(View.GONE);
            trailerRecycler.setVisibility(View.GONE);

            byte[] blob = movieParceable.getBuffer();
            // Convert the byte array to Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);

            movieImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300, 400, false));

            movieTitle.setText(movieParceable.getTitle());
            synopsis.setText(movieParceable.getOverview());
            voteAverage.setText(movieParceable.getVote_average());
            releaseDate.setText(getActivity().getResources().getString(R.string.released_label) + " " + Utils.formatDate(movieParceable.getRelease_date()));


        }
        else
        {
            final MovieParceable movieParceable = bundle.getParcelable("myData");
            setCollapsableInterface.setCollapse(true);

            posterPath = movieParceable.getPoster_path();
            movieTitle.setText(movieParceable.getTitle());
            synopsis.setText(movieParceable.getOverview());
            voteAverage.setText(movieParceable.getVote_average());
            releaseDate.setText(getActivity().getResources().getString(R.string.released_label) + " " + Utils.formatDate(movieParceable.getRelease_date()));

            trailers = new ArrayList<>();
            trailerRecycler.setHasFixedSize(true);
            trailerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
            trailerRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

            afterRenderedPoster(movieImage);

            favHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(CheckIsDataAlreadyInDBorNot(FeedReaderContract.FeedEntry.TABLE_NAME, FeedReaderContract.FeedEntry.ENTRY_TITLE,movieParceable.getTitle()))
                    {
                        removeFavorite(movieParceable);
                    }
                    else
                    {
                        makeFavorite(movieParceable);
                    }
                }
            });

            runVolley(movieParceable.getId());

        }

        return rootView;
    }

    private void makeFavorite(MovieParceable movieParceable) {


        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.ENTRY_TITLE, movieParceable.getTitle());
        values.put(FeedReaderContract.FeedEntry.ENTRY_RATING, movieParceable.getVote_average());
        values.put(FeedReaderContract.FeedEntry.ENTRY_SYNOPSIS, movieParceable.getOverview());
        values.put(FeedReaderContract.FeedEntry.ENTRY_SYNOPSIS, movieParceable.getOverview());
        values.put(FeedReaderContract.FeedEntry.ENTRY_DATE, movieParceable.getRelease_date());
        values.put(FeedReaderContract.FeedEntry.ENTRY_POSTER, getImageBuffer(movieImage));
        db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);

        Snackbar snackbar = Snackbar
                .make(nestedScrollView, movieParceable.getTitle() + " Added to Favorites", Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    private void removeFavorite(MovieParceable movieParceable) {
        // Define 'where' part of query.

        String selection = FeedReaderContract.FeedEntry.ENTRY_TITLE + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { movieParceable.getTitle() };
// Issue SQL statement.
        db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);

        Snackbar snackbar = Snackbar
                .make(nestedScrollView, "Removed " + movieParceable.getTitle() + " from Favorites", Snackbar.LENGTH_LONG);

        snackbar.show();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue) {

        String Query = "Select * from " + TableName + " where " + dbfield + " = \"" + fieldValue + "\";";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    private void afterRenderedPoster(final View view) {
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


    void runVolley(String id) {
        String apiKey = getActivity().getResources().getString(api_key);

        Uri builtUri = Uri.parse(Utils.IMDB_BASE_URL + id + "/videos").buildUpon()
                .appendQueryParameter(Utils.API_KEY, apiKey).build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, builtUri.toString(), null, new Response.Listener<JSONObject>() {

                    JSONArray ja_data;

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ja_data = response.getJSONArray(getActivity().getResources().getString(R.string.array_name));
                            int length = ja_data.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject jObj = ja_data.getJSONObject(i);
                                trailers.add(new Trailer(jObj));
                            }
                            youtubeAdapter = new YoutubeAdapter(getActivity(), trailers);
                            trailerRecycler.setAdapter(youtubeAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(jsObjRequest);

    }

    public interface SetCollapsableInterface
    {
        void setCollapse(boolean value);
    }

    private void getPoster(View view) {
        Glide.with(this).load(posterPath).into((ImageView) view);
    }

}
