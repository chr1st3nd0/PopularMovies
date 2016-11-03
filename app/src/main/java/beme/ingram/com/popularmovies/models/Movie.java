package beme.ingram.com.popularmovies.models;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import beme.ingram.com.popularmovies.R;

/**
 * Created by chr1s_000 on 10/19/2016.
 */

public class Movie {

    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String OVERVIEW = "overview";
    private static final String ADULT = "adult";
    private static final String RELEASE_DATE = "release_date";
    private static final String TITLE = "title";
    private static final String VOTE_AVERAGE = "vote_average";
    public static final String ID = "id";
    private static final String URL_IMAGE = "http://image.tmdb.org/t/p/";




    public Movie(JSONObject jsonObject, Context context) {

        String imageWidth = context.getResources().getString(R.string.poster_width);
        try {
            setPosterPath(URL_IMAGE  + imageWidth + "/"  + jsonObject.getString(POSTER_PATH));
            Log.d("Movie","Poster Path " + getPosterPath());
            setOverView(jsonObject.getString(OVERVIEW));
            setAdult(jsonObject.getString(ADULT));
            setReleaseDate(jsonObject.getString(RELEASE_DATE));
            setTitle(jsonObject.getString(TITLE));
            setVote_average(jsonObject.getString(VOTE_AVERAGE));
            setBackdropPath(jsonObject.getString(BACKDROP_PATH));
            setId(jsonObject.getString(ID));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    String posterPath;
    String backdropPath;
    String overView;
    String adult;
    String releaseDate;
    String title;
    String vote_average;
    String id;

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

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
