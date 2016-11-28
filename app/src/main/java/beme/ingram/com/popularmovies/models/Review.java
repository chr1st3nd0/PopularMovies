package beme.ingram.com.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chr1s_000 on 11/27/2016.
 */

public class Review {

    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";

    String author;
    String review;

    public Review(JSONObject jsonObject) {
        try {
            setAuthor(jsonObject.getString(REVIEW_AUTHOR));
            setReview(jsonObject.getString(REVIEW_CONTENT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
