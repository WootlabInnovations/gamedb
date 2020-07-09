package com.example.gamedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.BuildConfig;
import com.example.gamedb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewModel> {
    private JSONArray mVideos = new JSONArray();

    public void setVideos(JSONArray videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoListViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item,
                parent, false);

        return new VideoListViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewModel holder, int position) {
        try {
            JSONObject video = mVideos.getJSONObject(position);
            Uri youtubeUri = Uri.parse(BuildConfig.YOUTUBE_IMAGE_URL).buildUpon()
                    .appendPath(video.getString("video_id"))
                    .appendPath("0")
                    .build();

            Glide.with(holder.mContext)
                    .load(youtubeUri.toString() + ".jpg")
                    .fitCenter()
                    .into(holder.mImageView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mVideos.length();
    }

    public class VideoListViewModel extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;

        public VideoListViewModel(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_video_list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        JSONObject video = mVideos.getJSONObject(getAdapterPosition());

                        // Open video in YouTube or Web Browser
                        Uri youtubeUri = Uri.parse(BuildConfig.YOUTUBE_WATCH_URL).buildUpon()
                                .appendPath(video.getString("video_id"))
                                .build();
                        Intent intent = new Intent(Intent.ACTION_VIEW, youtubeUri);

                        // Check if an app exists on the phone such as the YouTube app to show the
                        // selected video
                        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "No app available to play videos",
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
