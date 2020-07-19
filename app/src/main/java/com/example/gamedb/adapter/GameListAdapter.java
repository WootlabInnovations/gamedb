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
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.ui.fragment.GameListFragment;

import java.util.ArrayList;
import java.util.List;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {
    private List<Game> mGames = new ArrayList<>();

    // The fragment's interaction listener is needed to perform actions from the recyclerview
    private GameListFragment.OnGameListFragmentInteractionListener mListener;

    public GameListAdapter(GameListFragment.OnGameListFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    public void setGames(List<Game> games) {
        mGames = games;
    }

    @NonNull
    @Override
    public GameListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.game_list_item, parent, false);

        return new GameListViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListViewHolder holder, int position) {
        Game game = mGames.get(position);
        if (game.getPosterImage() != null) {
            Uri imageUri = Uri.parse(holder.mIgdbImageUrl).buildUpon()
                    .appendPath("t_logo_med")
                    .appendPath(game.getPosterImage() + ".jpg")
                    .build();

            Glide.with(holder.mContext)
                    .load(imageUri.toString())
                    .centerCrop()
                    .into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mGames.size();
    }

    public class GameListViewHolder extends RecyclerView.ViewHolder {
        private final Context mContext;
        private final ImageView mImageView;
        private GameListFragment.OnGameListFragmentInteractionListener mListener;
        private final String mIgdbImageUrl;

        public GameListViewHolder(@NonNull final View itemView,
                                  GameListFragment.OnGameListFragmentInteractionListener listener) {
            super(itemView);
            this.mContext = itemView.getContext();
            mImageView = itemView.findViewById(R.id.image_view_game_list_item);
            mListener = listener;
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(mContext);
            mIgdbImageUrl = preference.getString(mContext.getResources().getString(
                    R.string.igdb_image_url), "");

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Game game = mGames.get(getAdapterPosition());
                    mListener.onGameSelected(game.getId());
                }
            });
        }
    }
}
