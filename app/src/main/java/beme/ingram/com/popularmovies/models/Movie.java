package beme.ingram.com.popularmovies.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chr1s_000 on 10/19/2016.
 */

public class Movie {

    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String ADULT = "adult";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String URL_IMAGE = "http://image.tmdb.org/t/p/w185/";




    public Movie(JSONObject jsonObject) {

        try {
            setPosterPath(URL_IMAGE + jsonObject.getString(POSTER_PATH));
            Log.d("Movie","Poster Path " + getPosterPath());
            setOverView(jsonObject.getString(OVERVIEW));
            setAdult(jsonObject.getString(ADULT));
            setReleaseDate(jsonObject.getString(RELEASE_DATE));
            setTitle(jsonObject.getString(TITLE));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    String posterPath;
    String overView;
    String adult;
    String releaseDate;
    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
