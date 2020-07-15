package com.example.gamedb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(GAME_ID)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_game_detail_container, GameDetailFragment.newInstance(
                            intent.getIntExtra(GAME_ID, -1)))
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO: Inflate the menu.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*
         * TODO: Create an intent that opens the Settings Activity when Settings is clicked
         *  on the menu.
         */

        return super.onOptionsItemSelected(item);
    }
}