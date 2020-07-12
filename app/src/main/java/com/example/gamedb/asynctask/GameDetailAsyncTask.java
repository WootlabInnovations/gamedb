package com.example.gamedb.asynctask;

import android.net.Uri;
import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;

public class GameDetailAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private MutableLiveData<JSONArray> mGame;

    public GameDetailAsyncTask(MutableLiveData<JSONArray> game) {
        mGame = game;
    }

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        int gameId = integers[0];
        Uri uri = Uri.parse(BuildConfig.IGDB_BASE_URL).buildUpon()
                .appendPath("games")
                .build();

        String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                "genres.*,name,platforms.*,player_perspectives.*,screenshots.*,storyline," +
                "summary,total_rating,total_rating_count,videos.*;" +
                "where platforms.category = 1 & id = " + gameId + ";";

        return (JSONArray) Utilities.httpRequest("POST", body, uri);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        // TODO: Set the MutableLiveData (mGame) with the JSONArray response.
    }
}
