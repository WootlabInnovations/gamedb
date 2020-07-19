package com.example.gamedb.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamedb.R;
import com.example.gamedb.adapter.GameListAdapter;
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.viewmodel.DownloadGameViewModel;
import com.example.gamedb.viewmodel.GameViewModel;

import java.util.List;

public class GameListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private GameListAdapter mGameListAdapter;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar mProgressBar;
    private OnGameListFragmentInteractionListener mListener;
    private int mPage = 1;
    private int mLimit = 24;
    private GameViewModel mGameViewModel;
    private DownloadGameViewModel mDownloadGameViewModel;
    private SharedPreferences mPreference;
    private String mUserKey;
    private String mIgdbBaseUrl;

    public static final String GAME_ID = "com.example.gamedb.GAME_ID";

    public GameListFragment() {
        // Required empty public constructor
    }

    public static GameListFragment newInstance() {
        return new GameListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnGameListFragmentInteractionListener) {
            mListener = (OnGameListFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() +
                    " must implement OnGameListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPreference = PreferenceManager.getDefaultSharedPreferences(requireContext());
        mUserKey = mPreference.getString(getResources().getString(R.string.user_key), "");
        mIgdbBaseUrl = mPreference.getString(getResources().getString(R.string.igdb_base_url),
                "");
        String igdbImageUrl = mPreference.getString(getResources().getString(R.string.igdb_image_url),
                "");
        String youtubeImageUrl = mPreference.getString(getResources().getString(
                R.string.youtube_image_url), "");
        String youtubeVideoUrl = mPreference.getString(getResources().getString(
                R.string.youtube_watch_url), "");

        if (mUserKey.equals("") || mIgdbBaseUrl.equals("") || igdbImageUrl.equals("") ||
                youtubeImageUrl.equals("") || youtubeVideoUrl.equals("")) {
            mListener.onNoSettingsProvided("Complete all settings fields.");
        } else {
            mDownloadGameViewModel = new ViewModelProvider(requireActivity()).get(
                    DownloadGameViewModel.class);
            mDownloadGameViewModel.downloadGames(mPage, mLimit, mUserKey, mIgdbBaseUrl);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view_game_list_fragment);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mGameListAdapter = new GameListAdapter(mListener);
        mRecyclerView.setAdapter(mGameListAdapter);

        mProgressBar = view.findViewById(R.id.progress_bar);

        mGameViewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
        mGameViewModel.listAll().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                mProgressBar.setVisibility(View.GONE);
                mGameListAdapter.setGames(games);
                mGameListAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMoreGames(recyclerView);
            }
        });

        return view;
    }

    private void loadMoreGames(RecyclerView recyclerView) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (mGameListAdapter.getItemCount() >= mLimit && lastPosition == mGameListAdapter
                .getItemCount() - 1) {
            mProgressBar.setVisibility(View.VISIBLE);

            if (mGameListAdapter.getItemCount() > mLimit) {
                mPage = (mGameListAdapter.getItemCount() / mLimit) + 1;
            } else {
                mPage += 1;
            }

            mDownloadGameViewModel.downloadGames(mPage, mLimit, mUserKey, mIgdbBaseUrl);
        }
    }

    public interface OnGameListFragmentInteractionListener {
        void onGameSelected(int gameId);
        void onNoSettingsProvided(String message);
    }
}