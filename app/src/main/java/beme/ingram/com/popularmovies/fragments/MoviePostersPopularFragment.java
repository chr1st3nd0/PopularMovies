package beme.ingram.com.popularmovies.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.adapters.PoserAdapter;
import beme.ingram.com.popularmovies.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

import static beme.ingram.com.popularmovies.Utils.API_KEY;
import static beme.ingram.com.popularmovies.Utils.IMDB_URL_POPULAR;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviePostersPopularFragment extends Fragment {


    ArrayList<Movie> movies;

    View rootView;
    @BindView(R.id.poster_recycler)RecyclerView posterRecycler;
    PoserAdapter poserAdapter;

    public MoviePostersPopularFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_poster_layout, container, false);
        ButterKnife.bind(this,rootView);

        movies = new ArrayList<>();

        runVolley();
        posterRecycler.setLayoutManager(new GridLayoutManager(getActivity(),2));
        posterRecycler.setHasFixedSize(true);

        return rootView;
    }


    void runVolley()
    {
        String apiKey = getActivity().getResources().getString(R.string.api_key);

        Uri builtUri = Uri.parse(IMDB_URL_POPULAR).buildUpon()
                .appendQueryParameter(API_KEY,apiKey).build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, builtUri.toString(), null, new Response.Listener<JSONObject>() {

                    JSONArray ja_data;
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ja_data = response.getJSONArray((getActivity().getResources().getString(R.string.array_name)));
                            int length = ja_data.length();
                            for(int i=0; i<length; i++)
                            {
                                JSONObject jObj = ja_data.getJSONObject(i);
                                movies.add(new Movie(jObj,getActivity()));
                            }
                            poserAdapter = new PoserAdapter(getActivity(),movies);
                            posterRecycler.setAdapter(poserAdapter);

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
}

