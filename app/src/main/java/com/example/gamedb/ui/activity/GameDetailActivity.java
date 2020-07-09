package com.example.gamedb.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamedb.R;
import com.example.gamedb.ui.fragment.GameDetailFragment;
import com.example.gamedb.ui.fragment.GameListFragment;

import static com.example.gamedb.ui.fragment.GameListFragment.GAME_ID;

public class GameDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Attach the GameDetailFragment to the Activity. Also ensure the pass the game ID
        //  to the GameDetailFragment instance.
        Intent intent = getIntent();
        if (intent.hasExtra(GAME_ID)) {

        }
    }
}