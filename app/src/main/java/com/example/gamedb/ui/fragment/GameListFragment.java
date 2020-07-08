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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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

    // TODO: Write the onAttach method that forces the host activity to implement the listener.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        /*
         * TODO: Initialize the recyclerview, adapter and layout manager. The layout manager should
         *  have a span count of 3. Then apply the layout manager and adapter to the recycler view.
         */


        return view;
    }

    public interface OnGameListFragmentInteractionListener {
        void onGameSelected(boolean isGameSelected);
    }
}