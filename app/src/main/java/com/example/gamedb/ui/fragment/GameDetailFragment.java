package com.example.gamedb.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamedb.R;
import com.example.gamedb.adapter.ScreenshotListAdapter;
import com.example.gamedb.adapter.VideoListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameDetailFragment extends Fragment {
    private RecyclerView mScreenshotRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private ScreenshotListAdapter mScreenshotListAdapter;
    private VideoListAdapter mVideoListAdapter;
    private LinearLayoutManager screenshotLinearLayoutManager;
    private LinearLayoutManager videoLinearLayoutManager;

    public GameDetailFragment() {
        // Required empty public constructor
    }

    public static GameDetailFragment newInstance(boolean isGameSelected) {
        GameDetailFragment gameDetailFragment = new GameDetailFragment();
        Bundle arguments = new Bundle();
        arguments.putBoolean(GameListFragment.IS_GAME_SELECTED, isGameSelected);
        gameDetailFragment.setArguments(arguments);

        return gameDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_detail, container, false);

        /*
         * TODO: Create the layout managers for screenshot and video.
         *  Initialize the adapters for both and attach the layout managers and adapters to their
         *  respective screenshot and video displays.
         */


        return view;
    }
}