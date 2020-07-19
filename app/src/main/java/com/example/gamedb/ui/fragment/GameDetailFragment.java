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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gamedb.R;
import com.example.gamedb.adapter.ScreenshotListAdapter;
import com.example.gamedb.adapter.VideoListAdapter;
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.GameGenres;
import com.example.gamedb.db.entity.GamePlatforms;
import com.example.gamedb.db.entity.GameScreenshots;
import com.example.gamedb.db.entity.GameVideos;
import com.example.gamedb.db.entity.Genre;
import com.example.gamedb.db.entity.Platform;
import com.example.gamedb.util.Utilities;
import com.example.gamedb.viewmodel.GameViewModel;

import java.text.ParseException;
import java.util.List;

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
    private GameViewModel mViewModel;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(
                requireContext());
        mIgdbImageUrl = preferences.getString(getResources().getString(R.string.igdb_image_url), "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_game_detail, container, false);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(GameListFragment.GAME_ID)) {
            mGameId = arguments.getInt(GameListFragment.GAME_ID, -1);

            mProgressBar = mView.findViewById(R.id.progress_bar);
            mProgressBar.setVisibility(View.GONE);

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

            mViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
            mViewModel.getGame(mGameId).observe(getViewLifecycleOwner(), new Observer<Game>() {
                @Override
                public void onChanged(Game game) {
                    displayGameDetail(game);
                }
            });
            mViewModel.getGameGenres(mGameId).observe(getViewLifecycleOwner(),
                    new Observer<GameGenres>() {
                @Override
                public void onChanged(GameGenres gameGenres) {
                    displayGameGenres(gameGenres);
                }
            });
            mViewModel.getGamePlatforms(mGameId).observe(getViewLifecycleOwner(),
                    new Observer<GamePlatforms>() {
                @Override
                public void onChanged(GamePlatforms gamePlatforms) {
                    displayGamePlatforms(gamePlatforms);
                }
            });
            mViewModel.getGameScreenshots(mGameId).observe(getViewLifecycleOwner(),
                    new Observer<GameScreenshots>() {
                @Override
                public void onChanged(GameScreenshots gameScreenshots) {
                    mScreenshotListAdapter.setScreenshots(gameScreenshots.screenshots);
                    mScreenshotListAdapter.notifyDataSetChanged();
                }
            });
            mViewModel.getGameVideos(mGameId).observe(getViewLifecycleOwner(),
                    new Observer<GameVideos>() {
                @Override
                public void onChanged(GameVideos gameVideos) {
                    mVideoListAdapter.setVideos(gameVideos.videos);
                    mVideoListAdapter.notifyDataSetChanged();
                }
            });
        }

        return mView;
    }

    private void displayGameDetail(Game game) {
        if (game.getBackgroundImage() != null) {
            Uri backgroundImageUri = Uri.parse(mIgdbImageUrl).buildUpon()
                    .appendPath("t_screenshot_big")
                    .appendPath(game.getBackgroundImage() + ".jpg")
                    .build();
            Glide.with(mView)
                    .load(backgroundImageUri)
                    .fitCenter()
                    .into((ImageView) mView.findViewById(R.id.image_view_game_cover));
        }

        if (game.getPosterImage() != null) {
            Uri posterImageUri = Uri.parse(mIgdbImageUrl).buildUpon()
                    .appendPath("t_logo_med")
                    .appendPath(game.getPosterImage() + ".jpg")
                    .build();
            Glide.with(mView)
                    .load(posterImageUri)
                    .fitCenter()
                    .into((ImageView) mView.findViewById(R.id.image_view_game_poster));
        }

        if (game.getName() != null) {
            TextView nameTextView = mView.findViewById(R.id.text_view_name);
            nameTextView.setText(game.getName());
        }

        if (game.getFirstReleaseDate() != null) {
            TextView releaseDateTextView = mView.findViewById(R.id.text_view_release_date);
            try {
                releaseDateTextView.setText(Utilities.convertTimestampToDate(String.valueOf(game
                        .getFirstReleaseDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (game.getTotalRating() != null) {
            TextView ratingTextView = mView.findViewById(R.id.text_view_rating);
            ratingTextView.setText(String.valueOf(Utilities.convertDoubleToInteger(String.valueOf(game
                    .getTotalRating()))));
        }

        if (game.getSummary() != null) {
            TextView summaryTextView = mView.findViewById(R.id.text_view_summary);
            summaryTextView.setText(game.getSummary());
        }

        if (game.getStoryline() != null) {
            TextView storylineTextView = mView.findViewById(R.id.text_view_storyline);
            storylineTextView.setText(game.getStoryline());
        }
    }

    private void displayGameGenres(GameGenres gameGenres) {
        TextView genreTextView = mView.findViewById(R.id.text_view_genre);
        StringBuilder genres = new StringBuilder();
        List<Genre> genreList = gameGenres.genres;
        for (int i = 0; i < genreList.size(); i++) {
            if (i == 0 && i != genreList.size() - 1) {
                genres.append(" ")
                        .append(genreList.get(i).getName())
                        .append(", ");
            } else if (i == genreList.size() - 1) {
                genres.append(genreList.get(i).getName());
            } else {
                genres.append(genreList.get(i).getName())
                        .append(", ");
            }
        }
        genreTextView.setText(genres.toString());
    }

    private void displayGamePlatforms(GamePlatforms gamePlatforms) {
        TextView platformsTextView = mView.findViewById(R.id.text_view_platforms);
        StringBuilder platforms = new StringBuilder();
        List<Platform> platformList = gamePlatforms.platforms;
        for (int i = 0; i < platformList.size(); i++) {
            if (i == 0 && i != platformList.size() - 1) {
                platforms.append(" ")
                        .append(platformList.get(i).getName())
                        .append(", ");
            } else if (i == platformList.size() - 1) {
                platforms.append(platformList.get(i).getName());
            } else {
                platforms.append(platformList.get(i).getName())
                        .append(", ");
            }
        }
        platformsTextView.setText(platforms.toString());
    }
}