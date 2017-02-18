package beme.ingram.com.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import beme.ingram.com.popularmovies.R;
import beme.ingram.com.popularmovies.models.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PoserAdapter extends RecyclerView.Adapter<PoserAdapter.ViewHolder> {
    private ArrayList<Movie> mMovies;
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
    public PoserAdapter(Context context,ArrayList<Movie> movies) {
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

        holder.posterImage.requestLayout();

        holder.posterImage.setScaleType(ImageView.ScaleType.FIT_XY);

        Glide.with(mContext).load(mMovies.get(position).getPosterPath()).into(holder.posterImage);

        holder.posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDetails showDetails = (ShowDetails)mContext;
                showDetails.showDetails(mMovies.get(position));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public interface ShowDetails
    {
        void showDetails(Movie movie);
    }
}