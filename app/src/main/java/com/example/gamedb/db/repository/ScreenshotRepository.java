package com.example.gamedb.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.dao.ScreenshotDao;
import com.example.gamedb.db.entity.Screenshot;

public class ScreenshotRepository {
    private ScreenshotDao mScreenshotDao;

    public ScreenshotRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        mScreenshotDao = database.screenshotDao();
    }


    public void insert(Screenshot... screenshots) {
        new InsertAsyncTask(mScreenshotDao).execute(screenshots);
    }

    public void update(Screenshot... screenshots) {
        new UpdateAsyncTask(mScreenshotDao).execute(screenshots);
    }

    public void deleteAll(Long date) {
        new DeleteAsyncTask(mScreenshotDao).execute(date);
    }

    private static class InsertAsyncTask extends AsyncTask<Screenshot, Void, Void> {
        private ScreenshotDao mDao;

        public InsertAsyncTask(ScreenshotDao screenshotDao) {
            mDao = screenshotDao;
        }

        @Override
        protected Void doInBackground(Screenshot... screenshots) {
            mDao.insert(screenshots);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Screenshot, Void, Void> {
        private ScreenshotDao mDao;

        public UpdateAsyncTask(ScreenshotDao screenshotDao) {
            mDao = screenshotDao;
        }

        @Override
        protected Void doInBackground(Screenshot... screenshots) {
            mDao.update(screenshots);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {
        private ScreenshotDao mDao;

        public DeleteAsyncTask(ScreenshotDao screenshotDao) {
            mDao = screenshotDao;
        }

        @Override
        protected Void doInBackground(Long... dates) {
            mDao.deleteAll(dates[0]);
            return null;
        }
    }
}
