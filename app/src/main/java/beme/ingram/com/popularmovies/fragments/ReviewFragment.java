package beme.ingram.com.popularmovies.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import beme.ingram.com.popularmovies.DividerItemDecoration;
import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.Utils;
import beme.ingram.com.popularmovies.adapters.ReviewsAdapter;
import beme.ingram.com.popularmovies.models.MovieParceable;
import beme.ingram.com.popularmovies.models.Review;
import butterknife.BindView;
import butterknife.ButterKnife;

import static beme.ingram.com.popularmovies.R.string.api_key;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    View rootView;
    ArrayList<Review> reviews;
    ReviewsAdapter reviewsAdapter;
    @BindView(R.id.review_recycler
    ) RecyclerView reviewsRecycler;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_review, container, false);

        ButterKnife.bind(this,rootView);
        reviews = new ArrayList();


        Bundle bundle = this.getArguments();
        MovieParceable movieParceable = bundle.getParcelable("myData");

        reviewsRecycler.setHasFixedSize(true);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewsRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL_LIST));


        runVolley(movieParceable.getId());
        return rootView;
    }


    void runVolley(String id)
    {
        String apiKey = getActivity().getResources().getString(api_key);

        Uri builtUri = Uri.parse(Utils.IMDB_BASE_URL  + id + "/reviews").buildUpon()
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
                                reviews.add(new Review(jObj));
                            }
                            reviewsAdapter = new ReviewsAdapter(reviews);
                            reviewsRecycler.setAdapter(reviewsAdapter);

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
