package com.example.gamedb.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.dao.GameDao;
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.GameGenres;
import com.example.gamedb.db.entity.GamePlatforms;
import com.example.gamedb.db.entity.GameScreenshots;
import com.example.gamedb.db.entity.GameVideos;

import java.util.List;

public class GameRepository {
    private GameDao mGameDao;

    public GameRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        mGameDao = database.gameDao();
    }

    public void insert(Game... games) {
        new InsertAsyncTask(mGameDao).execute(games);
    }

    public void update(Game... games) {
        new UpdateAsyncTask(mGameDao).execute(games);
    }

    public void deleteAll(Long date) {
        new DeleteAsyncTask(mGameDao).execute(date);
    }

    public LiveData<List<Game>> listAll() {
        return mGameDao.listAll();
    }

    public LiveData<Game> getGame(int id) {
        return mGameDao.getGame(id);
    }

    public LiveData<GameGenres> getGameGenres(int id) {
        return mGameDao.getGameGenres(id);
    }

    public LiveData<GamePlatforms> getGamePlatforms(int id) {
        return mGameDao.getGamePlatforms(id);
    }

    public LiveData<GameScreenshots> getGameScreenshots(int id) {
        return mGameDao.getGameScreenshots(id);
    }

    public LiveData<GameVideos> getGameVideos(int id) {
        return mGameDao.getGameVideos(id);
    }

    private static class InsertAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao mDao;

        public InsertAsyncTask(GameDao gameDao) {
            mDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mDao.insert(games);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Game, Void, Void> {
        private GameDao mDao;

        public UpdateAsyncTask(GameDao gameDao) {
            mDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            mDao.update(games);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {
        private GameDao mDao;

        public DeleteAsyncTask(GameDao gameDao) {
            mDao = gameDao;
        }

        @Override
        protected Void doInBackground(Long... dates) {
            mDao.deleteAll(dates[0]);
            return null;
        }
    }
}
