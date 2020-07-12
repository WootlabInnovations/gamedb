package com.example.gamedb.asynctask;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;

public class GameListAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private int mLimit = 24;
    private int mOffset = 0;
    private MutableLiveData<JSONArray> mGames;

    private static final String LOG_TAG = GameListAsyncTask.class.getName();

    public GameListAsyncTask(MutableLiveData<JSONArray> games) {
        mGames = games;
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
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        // TODO: Set the MutableLiveData (mGames) with the JSONArray response.
    }
}
