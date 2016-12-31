package beme.ingram.com.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chr1s_000 on 11/6/2016.
 */

public class MovieParceable implements Parcelable {

    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String adult;
    private String release_date;
    private String title;
    private String vote_average;
    private  String id;

    public MovieParceable() {}

    protected MovieParceable(Parcel in) {
        poster_path = in.readString() ;
        overview = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        release_date = in.readString();
        title = in.readString();
        vote_average = in.readString();
        id = in.readString();
    }

    public MovieParceable(Movie  movie) {
        // Normal actions performed by class, since this is still a normal object!
        poster_path = movie.getPosterPath();
        overview = movie.getOverView();
        backdrop_path = movie.getBackdropPath();
        adult = movie.getAdult();
        release_date = movie.getReleaseDate();
        title = movie.getTitle();
        vote_average = movie.getVote_average();
        id = movie.getId();
    }

    public static final Creator<MovieParceable> CREATOR = new Creator<MovieParceable>() {
        @Override
        public MovieParceable createFromParcel(Parcel in) {

            return new MovieParceable(in);
        }

        @Override
        public MovieParceable[] newArray(int size) {
            return new MovieParceable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(poster_path);
        out.writeString(overview);
        out.writeString(backdrop_path);
        out.writeString(adult);
        out.writeString(release_date);
        out.writeString(title);
        out.writeString(vote_average);
        out.writeString(id);
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getAdult() {
        return adult;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public String getId() {
        return id;
    }

}
