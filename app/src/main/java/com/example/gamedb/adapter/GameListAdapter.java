package com.example.gamedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.R;
import com.example.gamedb.ui.activity.GameDetailActivity;
import com.example.gamedb.ui.fragment.GameListFragment;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {
    private int[] mGamesPosters = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,
            R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12,
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,
            R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12
    };

    // The fragment's interaction listener is needed to perform actions from the recyclerview
    private GameListFragment.OnGameListFragmentInteractionListener mListener;

    public GameListAdapter(GameListFragment.OnGameListFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);

        // TODO: Return an instance of the GameListViewHolder
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        Glide.with(holder.mContext)
                .load(mGamesPosters[position])
                .fitCenter()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mGamesPosters.length;
    }

    public static class GameListViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;
        private GameListFragment.OnGameListFragmentInteractionListener mListener;

        public GameListViewHolder(@NonNull final View itemView,
                                  GameListFragment.OnGameListFragmentInteractionListener listener) {
            super(itemView);
            this.mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_game_list_item);
            mListener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Set the method to listener calls when a click event occurs on the view

                }
            });
        }
    }
}
