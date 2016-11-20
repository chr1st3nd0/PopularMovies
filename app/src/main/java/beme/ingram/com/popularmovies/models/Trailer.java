package beme.ingram.com.popularmovies.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by chr1s_000 on 11/12/2016.
 */

public class Trailer {

    private static final String TRAILER_ID = "id";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    String id;
    String name;
    String key;

    public Trailer(JSONObject jsonObject) {
        try {
            setId(jsonObject.getString(TRAILER_ID));
            setName(jsonObject.getString(TRAILER_NAME));
            setKey(jsonObject.getString(TRAILER_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
