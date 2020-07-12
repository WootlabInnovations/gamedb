package com.example.gamedb.ui.fragment;

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

            /*
             * TODO: Initialize the view model, fetch the game detail from the remote server and
             *  observe the view model for changes (i.e. when the remote server has downloaded the
             *  game.
             *  Remember to set the game list adapter when the view model observes changes
             */
            // ...
        }

        return mView;
    }

    private void displayGameDetail(JSONArray jsonArray) {
        /*
         * TODO: Display the returned game detail in the view.
         *  This is a helper method to display the game details in the view. You should call this
         *  method from the view model observer in the onCreateView method.
         */
    }
}