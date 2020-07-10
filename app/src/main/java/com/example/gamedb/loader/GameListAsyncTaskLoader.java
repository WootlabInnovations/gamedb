package com.example.gamedb.loader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;

public class GameListAsyncTaskLoader extends AsyncTaskLoader<JSONArray> {
    private int mPage;

    public GameListAsyncTaskLoader(@NonNull Context context, int page) {
        super(context);
        mPage = page;
    }

    @Nullable
    @Override
    public JSONArray loadInBackground() {
        /*
         * TODO: Fetch list of games from the remote server.
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
