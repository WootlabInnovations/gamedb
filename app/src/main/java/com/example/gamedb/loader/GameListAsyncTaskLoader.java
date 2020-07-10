package com.example.gamedb.loader;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.gamedb.BuildConfig;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;

public class GameListAsyncTaskLoader extends AsyncTaskLoader<JSONArray> {
    private int mPage;
    private int mLimit = 24;
    private int mOffset = 0;

    public GameListAsyncTaskLoader(@NonNull Context context, int page) {
        super(context);
        mPage = page;
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {
        if (mPage > 1) {
            mOffset = (mPage - 1) * mLimit;
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
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
