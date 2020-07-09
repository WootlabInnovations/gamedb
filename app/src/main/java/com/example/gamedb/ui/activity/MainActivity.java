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

    @Override
    public void onGameSelected(int gameId) {
        if (findViewById(R.id.fragment_game_detail_container) != null) {
            GameDetailFragment gameDetailFragment = GameDetailFragment.newInstance(gameId);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_game_detail_container, gameDetailFragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Intent intent = new Intent(this, GameDetailActivity.class);
            intent.putExtra(GameListFragment.GAME_ID, gameId);
            startActivity(intent);
        }
    }
}