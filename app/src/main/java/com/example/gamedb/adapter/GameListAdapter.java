package com.example.gamedb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.BuildConfig;
import com.example.gamedb.R;
import com.example.gamedb.ui.activity.GameDetailActivity;
import com.example.gamedb.ui.fragment.GameListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListViewHolder> {
    private JSONArray mGames = new JSONArray();

    // The fragment's interaction listener is needed to perform actions from the recyclerview
    private GameListFragment.OnGameListFragmentInteractionListener mListener;

    public GameListAdapter(GameListFragment.OnGameListFragmentInteractionListener listener) {
        this.mListener = listener;
    }

    public void setGames(JSONArray games) throws JSONException {
        for (int i = 0 ; i < games.length(); i++) {
            mGames.put(games.getJSONObject(i));
        }
        notifyDataSetChanged();
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
        try {
            JSONObject game = mGames.getJSONObject(position);
            Uri imageUri = Uri.parse(BuildConfig.IGDB_IMAGE_URL).buildUpon()
                    .appendPath("t_logo_med")
                    .appendPath(game.getJSONObject("cover").getString("image_id") + ".jpg")
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
        return mGames.length();
    }

    public class GameListViewHolder extends RecyclerView.ViewHolder {
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
                    try {
                        JSONObject game = mGames.getJSONObject(getAdapterPosition());
                        mListener.onGameSelected(game.getInt("id"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
