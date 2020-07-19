package com.example.gamedb.db.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.dao.VideoDao;
import com.example.gamedb.db.entity.Video;

public class VideoRepository {
    private VideoDao mVideoDao;

    public VideoRepository(Application application) {
        GameDatabase database = GameDatabase.getDatabase(application);
        mVideoDao = database.videoDao();
    }

    public void insert(Video... videos) {
        new InsertAsyncTask(mVideoDao).execute(videos);
    }

    public void update(Video... videos) {
        new UpdateAsyncTask(mVideoDao).execute(videos);
    }

    public void deleteAll(Long date) {
        new DeleteAsyncTask(mVideoDao).execute(date);
    }

    private static class InsertAsyncTask extends AsyncTask<Video, Void, Void> {
        private VideoDao mDao;

        public InsertAsyncTask(VideoDao videoDao) {
            mDao = videoDao;
        }

        @Override
        protected Void doInBackground(Video... videos) {
            mDao.insert(videos);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Video, Void, Void> {
        private VideoDao mDao;

        public UpdateAsyncTask(VideoDao videoDao) {
            mDao = videoDao;
        }

        @Override
        protected Void doInBackground(Video... videos) {
            mDao.update(videos);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Long, Void, Void> {
        private VideoDao mDao;

        public DeleteAsyncTask(VideoDao videoDao) {
            mDao = videoDao;
        }

        @Override
        protected Void doInBackground(Long... dates) {
            mDao.deleteAll(dates[0]);
            return null;
        }
    }
}
