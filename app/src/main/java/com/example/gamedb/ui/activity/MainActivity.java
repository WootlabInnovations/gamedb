package com.example.gamedb.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.gamedb.adapter.GameListAdapter;
import com.example.gamedb.R;
import com.example.gamedb.ui.fragment.GameDetailFragment;
import com.example.gamedb.ui.fragment.GameListFragment;

public class MainActivity extends AppCompatActivity implements
        GameListFragment.OnGameListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * TODO: Implement interface method(s) from GameListFragment.
     *  Ensure to check that the activity contains two panes or one pane to determine whether to
     *  start the new activity or display a new fragment in the second pane.
     *  Also ensure the pass the game ID to the GameDetailFragment instance.
     */
    @Override
    public void onGameSelected(int gameId) {

    }
}