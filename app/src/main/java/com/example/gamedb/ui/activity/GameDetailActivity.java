package com.example.gamedb.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamedb.R;
import com.example.gamedb.ui.fragment.GameDetailFragment;

public class GameDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // TODO: Attach the GameDetailFragment to the Activity
        
    }
}
