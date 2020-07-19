package com.example.gamedb.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.dao.GenreDao;
import com.example.gamedb.db.entity.Genre;

public class GenreRepository {
    private GenreDao mGenreDao;

    public GenreRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        mGenreDao = database.genreDao();
    }

    public void insert(Genre... genres) {
        new InsertAsyncTask(mGenreDao).execute(genres);
    }

    public void update(Genre... genres) {
        new UpdateAsyncTask(mGenreDao).execute(genres);
    }

    public void deleteAll(Long date) {
        new DeleteAsyncTask(mGenreDao).execute(date);
    }

    private static class InsertAsyncTask extends AsyncTask<Genre, Void, Void> {
        private GenreDao mDao;

        public InsertAsyncTask(GenreDao genreDao) {
            mDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {
            mDao.insert(genres);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Genre, Void, Void> {
        private GenreDao mDao;

        public UpdateAsyncTask(GenreDao genreDao) {
            mDao = genreDao;
        }

        @Override
        protected Void doInBackground(Genre... genres) {
            mDao.update(genres);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {
        private GenreDao mDao;

        public DeleteAsyncTask(GenreDao genreDao) {
            mDao = genreDao;
        }

        @Override
        protected Void doInBackground(Long... dates) {
            mDao.deleteAll(dates[0]);
            return null;
        }
    }
}
