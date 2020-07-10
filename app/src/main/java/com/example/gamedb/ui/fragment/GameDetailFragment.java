package com.example.gamedb.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamedb.R;
import com.example.gamedb.adapter.ScreenshotListAdapter;
import com.example.gamedb.adapter.VideoListAdapter;

// TODO: Implement the LoaderManager callbacks
public class GameDetailFragment extends Fragment {
    private RecyclerView mScreenshotRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private ScreenshotListAdapter mScreenshotListAdapter;
    private VideoListAdapter mVideoListAdapter;
    private LinearLayoutManager screenshotLinearLayoutManager;
    private LinearLayoutManager videoLinearLayoutManager;
    private LoaderManager mLoaderManager;
    private ProgressBar mProgressBar;
    private View mView;
    private int mGameId;
    private int LOADER_ID = 2;

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
        }

        return mView;
    }
}