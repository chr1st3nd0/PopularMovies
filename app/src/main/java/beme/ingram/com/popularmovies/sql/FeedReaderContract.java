package beme.ingram.com.popularmovies.sql;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String ENTRY_TITLE = "title";
        public static final String ENTRY_POSTER = "poster";
        public static final String ENTRY_SYNOPSIS = "synopsis";
        public static final String ENTRY_RATING = "rating";
        public static final String ENTRY_DATE = "date";
        public static final String ENTRY_BACKDROP = "backdrop";
    }
}