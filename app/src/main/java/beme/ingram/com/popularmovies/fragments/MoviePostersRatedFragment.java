package beme.ingram.com.popularmovies.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import beme.ingram.com.popularmovies.Utils;
import beme.ingram.com.popularmovies.adapters.PoserAdapter;
import beme.ingram.com.popularmovies.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviePostersRatedFragment extends Fragment {

    ArrayList<Movie> movies;

    View rootView;
    @BindView(R.id.poster_recycler)RecyclerView posterRecycler;
    PoserAdapter poserAdapter;
    @BindView(R.id.retry_button)Button retryButton;


    public MoviePostersRatedFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.movie_poster_layout, container, false);
        ButterKnife.bind(this,rootView);

        if(Utils.checkConnection(getActivity()))
        {
            init();
        }
        else
        {
            retryButton.setVisibility(View.VISIBLE);
            retryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(Utils.checkConnection(getActivity()))
                    {
                        init();
                        retryButton.setVisibility(View.GONE);
                    }
                }
            });
        }


        return rootView;
    }


    private void init()
    {
        movies = new ArrayList<>();
        runVolley();
        if(!getActivity().getResources().getBoolean(R.bool.isTablet)) {
            posterRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        else
        {
            posterRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        posterRecycler.setHasFixedSize(true);
    }


    void runVolley()
    {
        String apiKey = getActivity().getResources().getString(R.string.api_key);

        Uri builtUri = Uri.parse(Utils.IMDB_URL_RATED).buildUpon()
                .appendQueryParameter(Utils.API_KEY,apiKey).build();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, builtUri.toString(), null, new Response.Listener<JSONObject>() {

                    JSONArray ja_data;
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            ja_data = response.getJSONArray(getActivity().getResources().getString(R.string.array_name));
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
