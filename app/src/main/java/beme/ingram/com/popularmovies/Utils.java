package beme.ingram.com.popularmovies;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by chr1s_000 on 10/27/2016.
 */

public class Utils {

    public static final String IMDB_URL_RATED = "http://api.themoviedb.org/3/movie/top_rated?";
    public static final String IMDB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String IMDB_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?";
    public static final String API_KEY = "api_key";
    public static final String TEST_URL = "http://api.themoviedb.org/3/movie/259316/reviews?api_key=7520b6c8fbbdf7a6fc3c7686b13e6941";
    public static final String YOUTUBE_API_KEY = "";


    public static String formatDate(String dateValue)
    {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(dateValue);
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(date);   // assigns calendar to given date
            return  new DateFormatSymbols().getMonths()[calendar.get(Calendar.MONTH)]+ " " +  String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + ", " +  String.valueOf(calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getImageBuffer(ImageView imageView)
    {
        imageView.buildDrawingCache();
        Bitmap bm = imageView.getDrawingCache();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, out);
        byte[] buffer=out.toByteArray();

        return buffer;
    }

}

