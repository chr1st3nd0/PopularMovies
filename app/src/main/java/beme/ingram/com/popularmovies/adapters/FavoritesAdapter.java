package beme.ingram.com.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.offline.OfflineMovie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private ArrayList<OfflineMovie> mMovies;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.poster_image) ImageView posterImage;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this,v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoritesAdapter(Context context, ArrayList<OfflineMovie> movies) {
        mMovies = movies;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_poster_element, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Display display = ((Activity)mContext).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x / 2;
        int height = size.y / 2;
        holder.posterImage.requestLayout();


        // Apply the new height for ImageView programmatically
        holder.posterImage.getLayoutParams().height = height;

        // Apply the new width for ImageView programmatically
        holder.posterImage.getLayoutParams().width = width;

        // Set the scale type for ImageView image scaling
        holder.posterImage.setScaleType(ImageView.ScaleType.FIT_XY);

        byte[] blob = mMovies.get(position).getBuffer();
        // Convert the byte array to Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        
        holder.posterImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 300, false));

        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowFavoriteDetails showDetails = (ShowFavoriteDetails) mContext;
                showDetails.showFavoriteDetails(mMovies.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public interface ShowFavoriteDetails
    {
        void showFavoriteDetails(OfflineMovie movie);
    }
}