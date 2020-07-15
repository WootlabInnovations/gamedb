package com.example.gamedb.ui.fragment;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.BuildConfig;
import com.example.gamedb.R;
import com.example.gamedb.adapter.ScreenshotListAdapter;
import com.example.gamedb.adapter.VideoListAdapter;
import com.example.gamedb.util.Utilities;
import com.example.gamedb.viewmodel.GameDetailViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class GameDetailFragment extends Fragment {
    private RecyclerView mScreenshotRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private ScreenshotListAdapter mScreenshotListAdapter;
    private VideoListAdapter mVideoListAdapter;
    private LinearLayoutManager screenshotLinearLayoutManager;
    private LinearLayoutManager videoLinearLayoutManager;
    private ProgressBar mProgressBar;
    private View mView;
    private int mGameId;
    private GameDetailViewModel mViewModel;
    private String mIgdbImageUrl;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(int gameId) {
        GameDetailFragment gameDetailFragment = new GameDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(GameListFragment.GAME_ID, gameId);
        gameDetailFragment.setArguments(arguments);

        return gameDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_game_detail, container, false);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(GameListFragment.GAME_ID)) {
            mGameId = arguments.getInt(GameListFragment.GAME_ID, -1);

            mProgressBar = mView.findViewById(R.id.progress_bar);

            screenshotLinearLayoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL, false);

            mScreenshotListAdapter = new ScreenshotListAdapter();
            mScreenshotRecyclerView = mView.findViewById(R.id.recycler_view_screenshot_list);
            mScreenshotRecyclerView.setLayoutManager(screenshotLinearLayoutManager);
            mScreenshotRecyclerView.setAdapter(mScreenshotListAdapter);

            videoLinearLayoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.HORIZONTAL, false);

            mVideoListAdapter = new VideoListAdapter();
            mVideoRecyclerView = mView.findViewById(R.id.recycler_view_video_list);
            mVideoRecyclerView.setLayoutManager(videoLinearLayoutManager);
            mVideoRecyclerView.setAdapter(mVideoListAdapter);

            mViewModel = new ViewModelProvider(requireActivity()).get(GameDetailViewModel.class);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                    requireContext());
            String userKey = preferences.getString(getResources().getString(R.string.user_key), "");
            String igdbBaseUrl = preferences.getString(getResources().getString(R.string.igdb_base_url), "");
            mIgdbImageUrl = preferences.getString(getResources().getString(R.string.igdb_image_url), "");
            mViewModel.loadGame(mGameId, userKey, igdbBaseUrl);
            mViewModel.getGame().observe(getViewLifecycleOwner(), new Observer<JSONArray>() {
                @Override
                public void onChanged(JSONArray jsonArray) {
                    if (jsonArray == null) {
                        mProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        mProgressBar.setVisibility(View.GONE);
                        displayGameDetail(jsonArray);
                    }
                }
            });
        }

        return mView;
    }

    private void displayGameDetail(JSONArray jsonArray) {
        try {
            JSONObject game = jsonArray.getJSONObject(0);

            JSONArray screenshotArray = game.getJSONArray("screenshots");
            JSONArray videoArray = game.getJSONArray("videos");

            // Background Image
            JSONObject backgroundImage = screenshotArray.getJSONObject(0);
            Uri backgroundImageUri = Uri.parse(mIgdbImageUrl).buildUpon()
                    .appendPath("t_screenshot_big")
                    .appendPath(backgroundImage.getString("image_id") + ".jpg")
                    .build();
            Glide.with(mView)
                    .load(backgroundImageUri.toString())
                    .fitCenter()
                    .into((ImageView) mView.findViewById(R.id.image_view_game_cover));

            // Poster Image
            JSONObject posterImage = game.getJSONObject("cover");
            Uri posterImageUri = Uri.parse(mIgdbImageUrl).buildUpon()
                    .appendPath("t_logo_med")
                    .appendPath(posterImage.getString("image_id") + ".jpg")
                    .build();
            Glide.with(mView)
                    .load(posterImageUri.toString())
                    .fitCenter()
                    .into((ImageView) mView.findViewById(R.id.image_view_game_poster));

            // Game name, release date and rating
            TextView nameTextView = mView.findViewById(R.id.text_view_name);
            nameTextView.setText(game.getString("name"));

            TextView releaseDateTextView = mView.findViewById(R.id.text_view_release_date);
            try {
                releaseDateTextView.setText(Utilities.convertTimestampToDate(game.getString(
                        "first_release_date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView ratingTextView = mView.findViewById(R.id.text_view_rating);
            ratingTextView.setText(String.valueOf(Utilities.convertDoubleToInteger(game.getString(
                    "total_rating"))));

            // Genre
            TextView genreTextView = mView.findViewById(R.id.text_view_genre);
            StringBuilder genres = new StringBuilder();
            for (int i = 0; i < game.getJSONArray("genres").length(); i++) {
                if (i == 0 && i != game.getJSONArray("genres").length() - 1) {
                    genres.append(" ")
                            .append(game.getJSONArray("genres").getJSONObject(i)
                                    .getString("name"))
                            .append(", ");
                } else if (i == game.getJSONArray("genres").length() - 1) {
                    genres.append(game.getJSONArray("genres").getJSONObject(i)
                            .getString("name"));
                } else {
                    genres.append(game.getJSONArray("genres").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                }
            }
            genreTextView.setText(genres.toString());

            // Platforms
            TextView platformsTextView = mView.findViewById(R.id.text_view_platforms);
            StringBuilder platforms = new StringBuilder();
            for (int i = 0; i < game.getJSONArray("platforms").length(); i++) {
                if (i == 0 && i != game.getJSONArray("platforms").length() - 1) {
                    platforms.append(" ")
                            .append(game.getJSONArray("platforms").getJSONObject(i)
                                    .getString("name"))
                            .append(", ");
                } else if (i == game.getJSONArray("platforms").length() - 1) {
                    platforms.append(game.getJSONArray("platforms").getJSONObject(i)
                            .getString("name"));
                } else {
                    platforms.append(game.getJSONArray("platforms").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                }
            }
            platformsTextView.setText(platforms.toString());

            // Summary
            TextView summaryTextView = mView.findViewById(R.id.text_view_summary);
            summaryTextView.setText(game.getString("summary"));

            // Storyline
            TextView storylineTextView = mView.findViewById(R.id.text_view_storyline);
            if (game.has("storyline")) {
                storylineTextView.setText(game.getString("storyline"));
            }

            // Videos
            mVideoListAdapter.setVideos(videoArray);

            // Screenshots
            mScreenshotListAdapter.setScreenshots(screenshotArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}