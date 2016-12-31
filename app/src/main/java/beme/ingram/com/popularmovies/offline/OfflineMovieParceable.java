package beme.ingram.com.popularmovies.offline;

import android.os.Parcel;

import beme.ingram.com.popularmovies.models.MovieParceable;

/**
 * Created by chr1s_000 on 11/6/2016.
 */

public class OfflineMovieParceable extends MovieParceable {

    private String poster_path;
    private String backdrop_path;
    private String overview;
    private String adult;
    private String release_date;
    private String title;
    private String vote_average;
    private  String id;
    private byte[] buffer;

    protected OfflineMovieParceable(Parcel in) {
        poster_path = in.readString() ;
        overview = in.readString();
        backdrop_path = in.readString();
        adult = in.readString();
        release_date = in.readString();
        title = in.readString();
        vote_average = in.readString();
        id = in.readString();
        buffer = new byte[in.readInt()];
        in.readByteArray(buffer);
    }

    public OfflineMovieParceable(OfflineMovie movie) {
        // Normal actions performed by class, since this is still a normal object!
        poster_path = movie.getPosterPath();
        overview = movie.getOverView();
        backdrop_path = movie.getBackdropPath();
        adult = movie.getAdult();
        release_date = movie.getReleaseDate();
        title = movie.getTitle();
        vote_average = movie.getVote_average();
        id = movie.getId();
        buffer = movie.getBuffer();
    }

    public static final Creator<OfflineMovieParceable> CREATOR = new Creator<OfflineMovieParceable>() {
        @Override
        public OfflineMovieParceable createFromParcel(Parcel in) {

            return new OfflineMovieParceable(in);
        }

        @Override
        public OfflineMovieParceable[] newArray(int size) {
            return new OfflineMovieParceable[size];
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
        out.writeInt(buffer.length);
        out.writeByteArray(buffer);
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

    public byte[] getBuffer() {
        return buffer;
    }
}
