package com.example.gamedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class GameDetailActivity extends AppCompatActivity {
    private RecyclerView mScreenshotRecyclerView;
    private RecyclerView mVideoRecyclerView;
    private ScreenshotListAdapter mScreenshotListAdapter;
    private VideoListAdapter mVideoListAdapter;
    private LinearLayoutManager screenshotLinearLayoutManager;
    private LinearLayoutManager videoLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        screenshotLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        mScreenshotListAdapter = new ScreenshotListAdapter();
        mScreenshotRecyclerView = findViewById(R.id.recycler_view_screenshot_list);
        mScreenshotRecyclerView.setLayoutManager(screenshotLinearLayoutManager);
        mScreenshotRecyclerView.setAdapter(mScreenshotListAdapter);

        videoLinearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        mVideoListAdapter = new VideoListAdapter();
        mVideoRecyclerView = findViewById(R.id.recycler_view_video_list);
        mVideoRecyclerView.setLayoutManager(videoLinearLayoutManager);
        mVideoRecyclerView.setAdapter(mVideoListAdapter);
    }
}