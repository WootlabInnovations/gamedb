package com.example.gamedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Arrays;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoListViewModel> {
    private int[] mVideoSamples = new int[10];

    public VideoListAdapter() {
        Arrays.fill(mVideoSamples, R.drawable.video_thumb);
    }

    @NonNull
    @Override
    public VideoListViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*
         * TODO: Create a new view that will be passed to the VideoListViewModel.
         *  Return the VideoListViewModel
         */
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoListViewModel holder, int position) {
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

    public static class VideoListViewModel extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;

        public VideoListViewModel(@NonNull View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_video_list_item);
        }
    }
}
