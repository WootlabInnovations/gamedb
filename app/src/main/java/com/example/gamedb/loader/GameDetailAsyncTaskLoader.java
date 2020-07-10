package com.example.gamedb.loader;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;

public class GameDetailAsyncTaskLoader extends AsyncTaskLoader<JSONArray> {
    private int mGameId;

    public GameDetailAsyncTaskLoader(@NonNull Context context, int gameId) {
        super(context);
        mGameId = gameId;
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {
        Uri uri = Uri.parse(BuildConfig.IGDB_BASE_URL).buildUpon()
                .appendPath("games")
                .build();

        String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                "genres.*,name,platforms.*,player_perspectives.*,screenshots.*,storyline," +
                "summary,total_rating,total_rating_count,videos.*;" +
                "where platforms.category = 1 & id = " + mGameId + ";";

        return (JSONArray) Utilities.httpRequest("POST", body, uri);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
