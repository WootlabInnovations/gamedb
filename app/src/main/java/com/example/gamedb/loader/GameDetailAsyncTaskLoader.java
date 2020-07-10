package com.example.gamedb.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

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
        /*
         * TODO: Fetch game details from the remote server.
         *  Hint: Use your implementation from the AsyncTask class
         */

        return null;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
