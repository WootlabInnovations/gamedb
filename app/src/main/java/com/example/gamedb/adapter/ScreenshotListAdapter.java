package com.example.gamedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.R;

public class ScreenshotListAdapter extends RecyclerView.Adapter<ScreenshotListAdapter
        .ScreenshotViewHolder> {
    private int[] mSampleScreenshots = {
            R.drawable.screenshot1, R.drawable.screenshot2, R.drawable.screenshot3,
            R.drawable.screenshot4, R.drawable.screenshot5, R.drawable.screenshot6,
            R.drawable.screenshot7, R.drawable.screenshot8, R.drawable.screenshot9,
            R.drawable.screenshot10
    };

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_list_item,
                parent, false);

        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        Glide.with(holder.mContext)
                .load(mSampleScreenshots[position])
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mSampleScreenshots.length;
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
