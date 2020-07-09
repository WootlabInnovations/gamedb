package com.example.gamedb.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gamedb.R;
import com.example.gamedb.adapter.GameListAdapter;
import com.example.gamedb.asynctask.GameListAsyncTask;

public class GameListFragment extends Fragment {
    private RecyclerView recyclerView;
    private GameListAdapter mGameListAdapter;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar mProgressBar;
    private OnGameListFragmentInteractionListener mListener;
    private int mPage = 1;
    private int mPosition;

    public static final String GAME_ID = "GAME_ID";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_game_list_fragment);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        mGameListAdapter = new GameListAdapter(mListener);
        recyclerView.setAdapter(mGameListAdapter);

        mProgressBar = view.findViewById(R.id.progress_bar);

        new GameListAsyncTask(mProgressBar, mGameListAdapter).execute(mPage);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMoreGames(recyclerView);
            }
        });

        return view;
    }

    public void loadMoreGames(RecyclerView recyclerView) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mGameListAdapter.getItemCount() - 1) {
            mProgressBar.setVisibility(View.VISIBLE);
            mPage += 1;
            mPosition = lastPosition + 1;

            new GameListAsyncTask(mProgressBar, mGameListAdapter).execute(mPage);
        }
    }

    public interface OnGameListFragmentInteractionListener {
        void onGameSelected(int gameId);
    }
}