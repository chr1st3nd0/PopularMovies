package beme.ingram.com.popularmovies.asyncs;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by chr1s_000 on 10/16/2016.
 */

public class MoviePosterTask extends AsyncTask<Object, Object, JSONObject> {

    private static final String IMDB_URL = "http://api.themoviedb.org/3";
    private static final String POPULAR_URL = "/movie/popular";
    private static final String TOP_RATED_URL = "/movie/top_rated";
    private static final String API_URL = "?api_key=";
    private static final String API_KEY = "7520b6c8fbbdf7a6fc3c7686b13e6941";

    private String imageWidth = "w185";

    Context mContext;
    JSONObject responseObject;

    public MoviePosterTask(Context context) {
        super();

        mContext = context;
    }

    @Override
    protected JSONObject doInBackground(Object... params) {



        return responseObject;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
    }
}
