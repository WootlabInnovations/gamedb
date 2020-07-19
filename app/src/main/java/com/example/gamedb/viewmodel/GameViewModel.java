package com.example.gamedb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.GameGenres;
import com.example.gamedb.db.entity.GamePlatforms;
import com.example.gamedb.db.entity.GameScreenshots;
import com.example.gamedb.db.entity.GameVideos;
import com.example.gamedb.db.repository.GameRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {
    private GameRepository mGameRepository;

    public GameViewModel(@NonNull Application application) {
        super(application);
        mGameRepository = new GameRepository(application);
    }

    // TODO: Complete GameViewModel methods, making calls to their respective repository methods.
}
