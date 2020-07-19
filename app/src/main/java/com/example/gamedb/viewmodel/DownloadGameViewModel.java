package com.example.gamedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.gamedb.asynctask.DownloadGameAsyncTask;
import com.example.gamedb.db.repository.GameRepository;
import com.example.gamedb.db.repository.GenreRepository;
import com.example.gamedb.db.repository.PlatformRepository;
import com.example.gamedb.db.repository.ScreenshotRepository;
import com.example.gamedb.db.repository.VideoRepository;

public class DownloadGameViewModel extends AndroidViewModel {
    private GameRepository mGameRepository;
    private GenreRepository mGenreRepository;
    private PlatformRepository mPlatformRepository;
    private ScreenshotRepository mScreenshotRepository;
    private VideoRepository mVideoRepository;


    public DownloadGameViewModel(@NonNull Application application) {
        super(application);
        mGameRepository = new GameRepository(application);
        mGenreRepository = new GenreRepository(application);
        mPlatformRepository = new PlatformRepository(application);
        mScreenshotRepository = new ScreenshotRepository(application);
        mVideoRepository = new VideoRepository(application);
    }

    public void downloadGames(int page, int limit, String userKey, String igdbBaseUrl) {
        new DownloadGameAsyncTask(mGameRepository, mGenreRepository, mPlatformRepository,
                mScreenshotRepository, mVideoRepository, userKey, igdbBaseUrl)
                .execute(page, limit);
    }
}
