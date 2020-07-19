package com.example.gamedb.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.dao.PlatformDao;
import com.example.gamedb.db.entity.Platform;

public class PlatformRepository {
    private PlatformDao mPlatformDao;

    public PlatformRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        mPlatformDao = database.platformDao();
    }

    public void insert(Platform... platforms) {
        new InsertAsyncTask(mPlatformDao).execute(platforms);
    }

    public void update(Platform... platforms) {
        new UpdateAsyncTask(mPlatformDao).execute(platforms);
    }

    public void deleteAll(Long date) {
        new DeleteAsyncTask(mPlatformDao).execute(date);
    }

    private static class InsertAsyncTask extends AsyncTask<Platform, Void, Void> {
        private PlatformDao mDao;

        public InsertAsyncTask(PlatformDao platformDao) {
            mDao = platformDao;
        }

        @Override
        protected Void doInBackground(Platform... platforms) {
            mDao.insert(platforms);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Platform, Void, Void> {
        private PlatformDao mDao;

        public UpdateAsyncTask(PlatformDao platformDao) {
            mDao = platformDao;
        }

        @Override
        protected Void doInBackground(Platform... platforms) {
            mDao.update(platforms);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {
        private PlatformDao mDao;

        public DeleteAsyncTask(PlatformDao platformDao) {
            mDao = platformDao;
        }

        @Override
        protected Void doInBackground(Long... dates) {
            mDao.deleteAll(dates[0]);
            return null;
        }
    }
}
