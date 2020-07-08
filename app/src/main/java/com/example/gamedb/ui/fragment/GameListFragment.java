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

import com.example.gamedb.R;
import com.example.gamedb.adapter.GameListAdapter;

public class GameListFragment extends Fragment {
    private RecyclerView recyclerView;
    private GameListAdapter mGameListAdapter;
    private GridLayoutManager gridLayoutManager;
    private OnGameListFragmentInteractionListener mListener;

    public static final String IS_GAME_SELECTED = "IS_GAME_SELECTED";

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

        return view;
    }

    public interface OnGameListFragmentInteractionListener {
        void onGameSelected(boolean isGameSelected);
    }
}