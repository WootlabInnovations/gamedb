package com.example.gamedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ScreenshotListAdapter extends RecyclerView.Adapter<ScreenshotListAdapter
        .ScreenshotViewHolder> {
    // TODO: Create an array of sample screenshots using the R.drawable.screenshot resources
    private int[] mSampleScreenshots = null;

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * TODO: Create a new view that will be passed to the ScreenshotViewHolder.
         *  Return the ScreenshotViewHolder
         */
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        /*
         * TODO: Use the Glide image transformation library to bind the image resources from the
         *  array to the image view.
         */
    }

    @Override
    public int getItemCount() {
        // TODO: Return the length of the dataset source.
        return 0;
    }

    public static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_screenshot_list_item);
        }
    }
}
