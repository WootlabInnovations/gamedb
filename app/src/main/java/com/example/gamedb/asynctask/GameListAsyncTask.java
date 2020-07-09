package com.example.gamedb.asynctask;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.adapter.GameListAdapter;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.ref.WeakReference;

public class GameListAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private WeakReference<ProgressBar> mProgressBar;
    private WeakReference<GameListAdapter> mGameListAdapter;
    private int mLimit = 24;
    private int mOffset = 0;

    private static final String LOG_TAG = GameListAsyncTask.class.getName();

    public GameListAsyncTask(ProgressBar progressBar, GameListAdapter gameListAdapter) {
        mProgressBar = new WeakReference<>(progressBar);
        mGameListAdapter = new WeakReference<>(gameListAdapter);
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        int page = integers[0];
        if (page > 1) {
            mOffset = (page - 1) * mLimit;
        }

        Uri uri = Uri.parse(BuildConfig.IGDB_BASE_URL).buildUpon()
                .appendPath("games")
                .build();

        // We only want games from 2015
        String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                "genres.*,name,platforms.*,player_perspectives.*,screenshots.*,storyline," +
                "summary,total_rating,total_rating_count,videos.*;" +
                "where platforms.category = 1 & first_release_date > 1420070400;" +
                "sort release_dates.date desc;" +
                "limit " + mLimit + ";" +
                "offset " + mOffset + ";";

        return (JSONArray) Utilities.httpRequest("POST", body, uri);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        // TODO: Display the progress bar to show users that data is loading from the remote server
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        // TODO: Remove the progress bar from view and set the returned data into the
        //  GameListAdapter
    }
}
