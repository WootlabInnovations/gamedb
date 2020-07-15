package com.example.gamedb.asynctask;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;

public class GameDetailAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private MutableLiveData<JSONArray> mGame;
    private String mUserKey;
    private String mIgdbBaseUrl;

    public GameDetailAsyncTask(MutableLiveData<JSONArray> game, String userKey, String igdbBaseUrl) {
        mGame = game;
        mUserKey = userKey;
        mIgdbBaseUrl = igdbBaseUrl;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        int gameId = integers[0];
        Uri uri = Uri.parse(mIgdbBaseUrl).buildUpon()
                .appendPath("games")
                .build();

        String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                "genres.*,name,platforms.*,player_perspectives.*,screenshots.*,storyline," +
                "summary,total_rating,total_rating_count,videos.*;" +
                "where platforms.category = 1 & id = " + gameId + ";";

        return (JSONArray) Utilities.httpRequest("POST", body, uri, mUserKey);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        mGame.setValue(jsonArray);
    }
}
