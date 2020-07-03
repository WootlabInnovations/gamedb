package com.example.gamedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private GameListAdapter mGameListAdapter;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_game_list);

        // This piece of code programmatically detects the orientation of the phone and decides
        // how many columns to display in the grid.
        // Portrait orientation displays 3 columns while landscape displays 6.
        int orientation = this.getResources().getConfiguration().orientation;
        int spanCount = 3;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 6;
        }
        gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        mGameListAdapter = new GameListAdapter();
        recyclerView.setAdapter(mGameListAdapter);
    }
}