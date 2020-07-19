package com.example.gamedb.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.R;
import com.example.gamedb.db.entity.Screenshot;

import java.util.ArrayList;
import java.util.List;

public class ScreenshotListAdapter extends RecyclerView.Adapter<ScreenshotListAdapter
        .ScreenshotViewHolder> {
    private List<Screenshot> mScreenshots = new ArrayList<>();

    public void setScreenshots(List<Screenshot> screenshots) {
        mScreenshots = screenshots;
    }

    @NonNull
    @Override
    public ScreenshotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screenshot_list_item,
                parent, false);

        return new ScreenshotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenshotViewHolder holder, int position) {
        Screenshot screenshot = mScreenshots.get(position);
        Uri imageUri = Uri.parse(holder.mIgdbImageUrl).buildUpon()
                .appendPath("t_screenshot_med")
                .appendPath(screenshot.getScreenshotImage() + ".jpg")
                .build();

        Glide.with(holder.mContext)
                .load(imageUri.toString())
                .centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mScreenshots.size();
    }

    public static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;
        private final String mIgdbImageUrl;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_screenshot_list_item);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mIgdbImageUrl = preferences.getString(mContext.getResources().getString(
                    R.string.igdb_image_url), "");
        }
    }
}
