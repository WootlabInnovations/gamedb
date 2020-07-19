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

            Uri uri = Uri.parse(igdbBaseUrl).buildUpon()
                    .appendPath("games")
                    .build();

            // We only want the first 500 games from 2015
            String body = "fields cover.*,artworks.*,cover.*,first_release_date,game_modes.*," +
                    "genres.*,name,platforms.*,screenshots.*,storyline," +
                    "summary,total_rating,total_rating_count,videos.*;" +
                    "where platforms.category = 1 & first_release_date > 1420070400;" +
                    "sort release_dates.date desc;" +
                    "limit 500;";

            JSONArray response = (JSONArray) Utilities.httpRequest("POST", body, uri,
                    userKey);
            saveGames(response);

            return Result.success();
        } catch (Exception ex) {
            return Result.failure();
        }
    }

    private void saveGames(JSONArray jsonArray) {
        GameDatabase gameDatabase = GameDatabase.getDatabase(getApplicationContext());

        Log.d(LOG_TAG, "================= Starting Download =================");

        try {
            if (jsonArray != null) {
                Date currentDate = new Date();
                Date expiryDate = Utilities.calculateExpiryDate(currentDate, 24);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject gameArray = jsonArray.getJSONObject(i);

                    JSONArray artworksArray = gameArray.has("artworks") ?
                            gameArray.getJSONArray("artworks") : null;
                    JSONArray screenshotArray = gameArray.has("screenshots") ?
                            gameArray.getJSONArray("screenshots") : null;
                    JSONArray videoArray = gameArray.has("videos") ?
                            gameArray.getJSONArray("videos") : null;
                    JSONObject coverImage = gameArray.has("cover") ?
                            gameArray.getJSONObject("cover") : null;

                    // Game information
                    Log.d(LOG_TAG, "================= Saving Game =================");

                    int id = gameArray.getInt("id");
                    String backgroundImage;
                    if (artworksArray != null) {
                        backgroundImage = artworksArray.getJSONObject(0)
                                .getString("image_id");
                    } else if (screenshotArray != null) {
                        backgroundImage = screenshotArray.getJSONObject(0)
                                .getString("image_id");
                    } else if (coverImage != null) {
                        backgroundImage = coverImage.getString("image_id");
                    } else {
                        backgroundImage = null;
                    }

                    String posterImage = coverImage != null ? coverImage.getString("image_id")
                            : null;
                    String name = gameArray.getString("name");
                    Long firstReleaseDate = null;
                    if (gameArray.has("first_release_date")) {
                        firstReleaseDate = gameArray.getLong("first_release_date");
                    }

                    Double totalRating = null;
                    if (gameArray.has("total_rating")) {
                        totalRating = gameArray.getDouble("total_rating");
                    }

                    String summary = null;
                    if (gameArray.has("summary")) {
                        summary = gameArray.getString("summary");
                    }

                    String storyline = null;
                    if (gameArray.has("storyline")) {
                        storyline = gameArray.getString("storyline");
                    }

                    Game game = new Game(id, backgroundImage, posterImage, name, firstReleaseDate,
                            totalRating, summary, storyline, expiryDate.getTime());
                    gameDatabase.gameDao().insert(game);

                    // Genre information
                    Log.d(LOG_TAG, "================= Saving Genres =================");
                    if (gameArray.has("genres")) {
                        for (int j = 0; j < gameArray.getJSONArray("genres").length(); j++) {
                            JSONObject genreObject = gameArray.getJSONArray("genres")
                                    .getJSONObject(j);
                            int genreId = genreObject.getInt("id");
                            String genreName = genreObject.getString("name");
                            gameDatabase.genreDao().insert(new Genre(genreId, genreName, id,
                                    expiryDate.getTime()));
                        }
                    }

                    // Platform information
                    Log.d(LOG_TAG, "================= Saving Platforms =================");
                    if (gameArray.has("platforms")) {
                        for (int j = 0; j < gameArray.getJSONArray("platforms").length(); j++) {
                            JSONObject platformObject = gameArray.getJSONArray("platforms")
                                    .getJSONObject(j);
                            int platformId = platformObject.getInt("id");
                            String platformName = platformObject.getString("name");
                            gameDatabase.platformDao().insert(new Platform(platformId, platformName,
                                    id, expiryDate.getTime()));
                        }
                    }

                    // Video information
                    Log.d(LOG_TAG, "================= Saving Videos =================");
                    if (videoArray != null) {
                        for (int j = 0; j < videoArray.length(); j++) {
                            JSONObject videoObject = videoArray.getJSONObject(j);
                            int videoId = videoObject.getInt("id");
                            String videoImage = videoObject.getString("video_id");
                            gameDatabase.videoDao().insert(new Video(videoId, videoImage, id,
                                    expiryDate.getTime()));
                        }
                    }

                    // Screenshot information
                    Log.d(LOG_TAG, "================= Saving Screenshots =================");
                    if (screenshotArray != null) {
                        for (int j = 0; j < screenshotArray.length(); j++) {
                            JSONObject screenshotObject = screenshotArray.getJSONObject(j);
                            int screenshotId = screenshotObject.getInt("id");
                            String screenshotImage = screenshotObject.getString("image_id");
                            gameDatabase.screenshotDao().insert(new Screenshot(screenshotId,
                                    screenshotImage, id, expiryDate.getTime()));
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "================= Finished Download =================");
    }
}
