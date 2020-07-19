package com.example.gamedb.workermanager;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.gamedb.db.GameDatabase;
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.Genre;
import com.example.gamedb.db.entity.Platform;
import com.example.gamedb.db.entity.Screenshot;
import com.example.gamedb.db.entity.Video;
import com.example.gamedb.ui.fragment.GameListFragment;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DownloadGamesWorker extends Worker {
    private static final String LOG_TAG = DownloadGamesWorker.class.getCanonicalName();

    public DownloadGamesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            String igdbBaseUrl = getInputData().getString(GameListFragment.BASE_URL);
            String userKey = getInputData().getString(GameListFragment.USER_KEY);

            // TODO: Build Uri to download game from remote server


            // We only want the first 500 games from 2015
            String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                    "genres.*,name,platforms.*,screenshots.*,storyline," +
                    "summary,total_rating,total_rating_count,videos.*;" +
                    "where platforms.category = 1 & first_release_date > 1420070400;" +
                    "sort release_dates.date desc;" +
                    "limit 500;";

            // TODO: Download games from remote server.
            JSONArray response = null;
            saveGames(response);

            return Result.success();
        } catch (Exception ex) {
            return Result.failure();
        }
    }

    private void saveGames(JSONArray jsonArray) {
        GameDatabase gameDatabase = GameDatabase.getDatabase(getApplicationContext());

        /*
         * TODO: Save downloaded list of games in database.
         *  NOTE: Instead of accessing repositories, use the database instance to call the DAO
         *  methods directly and perform database insert operations.
         */
    }
}
