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
import com.example.gamedb.BuildConfig;
import com.example.gamedb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScreenshotListAdapter extends RecyclerView.Adapter<ScreenshotListAdapter
        .ScreenshotViewHolder> {
    private JSONArray mScreenshots = new JSONArray();

    public void setScreenshots(JSONArray screenshots) {
        mScreenshots = screenshots;
        notifyDataSetChanged();
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
        try {
            JSONObject screenshot = mScreenshots.getJSONObject(position);
            Uri imageUri = Uri.parse(holder.mIgdbImageUrl).buildUpon()
                    .appendPath("t_screenshot_med")
                    .appendPath(screenshot.getString("image_id") + ".jpg")
                    .build();

            Glide.with(holder.mContext)
                    .load(imageUri.toString())
                    .centerCrop()
                    .into(holder.mImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mScreenshots.length();
    }

    public static class ScreenshotViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;
        private final String mIgdbImageUrl;

        public ScreenshotViewHolder(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_screenshot_list_item);

            // TODO: Get the image url from SharedPreference
            SharedPreferences preferences = null;
            mIgdbImageUrl = "";
        }
    }
}
