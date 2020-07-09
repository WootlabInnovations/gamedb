package com.example.gamedb.asynctask;

import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gamedb.BuildConfig;
import com.example.gamedb.R;
import com.example.gamedb.adapter.ScreenshotListAdapter;
import com.example.gamedb.adapter.VideoListAdapter;
import com.example.gamedb.util.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.text.ParseException;

public class GameDetailAsyncTask extends AsyncTask<Integer, Void, JSONArray> {
    private WeakReference<ProgressBar> mProgressBar;
    private WeakReference<View> mView;
    private WeakReference<ScreenshotListAdapter> mScreenshotListAdapter;
    private WeakReference<VideoListAdapter> mVideoListAdapter;

    public GameDetailAsyncTask(View view, ScreenshotListAdapter screenshotListAdapter,
                               VideoListAdapter videoListAdapter) {
        this.mView = new WeakReference<>(view);
        this.mScreenshotListAdapter = new WeakReference<>(screenshotListAdapter);
        this.mVideoListAdapter = new WeakReference<>(videoListAdapter);

        ProgressBar progressBar = mView.get().findViewById(R.id.progress_bar);
        mProgressBar = new WeakReference<>(progressBar);
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
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        mProgressBar.get().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        super.onPostExecute(jsonArray);
        mProgressBar.get().setVisibility(View.GONE);

        try {
            JSONObject game = jsonArray.getJSONObject(0);

            JSONArray screenshotArray = game.getJSONArray("screenshots");
            JSONArray videoArray = game.getJSONArray("videos");

            // Background Image
            JSONObject backgroundImage = screenshotArray.getJSONObject(0);
            Uri backgroundImageUri = Uri.parse(BuildConfig.IGDB_IMAGE_URL).buildUpon()
                    .appendPath("t_screenshot_big")
                    .appendPath(backgroundImage.getString("image_id") + ".jpg")
                    .build();
            Glide.with(mView.get())
                    .load(backgroundImageUri.toString())
                    .fitCenter()
                    .into((ImageView) mView.get().findViewById(R.id.image_view_game_cover));

            // Poster Image
            JSONObject posterImage = game.getJSONObject("cover");
            Uri posterImageUri = Uri.parse(BuildConfig.IGDB_IMAGE_URL).buildUpon()
                    .appendPath("t_logo_med")
                    .appendPath(posterImage.getString("image_id") + ".jpg")
                    .build();
            Glide.with(mView.get())
                    .load(posterImageUri.toString())
                    .fitCenter()
                    .into((ImageView) mView.get().findViewById(R.id.image_view_game_poster));

            // Game name, release date and rating
            TextView nameTextView = mView.get().findViewById(R.id.text_view_name);
            nameTextView.setText(game.getString("name"));

            TextView releaseDateTextView = mView.get().findViewById(R.id.text_view_release_date);
            try {
                releaseDateTextView.setText(Utilities.convertTimestampToDate(game.getString(
                        "first_release_date")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            TextView ratingTextView = mView.get().findViewById(R.id.text_view_rating);
            ratingTextView.setText(String.valueOf(Utilities.convertDoubleToInteger(game.getString(
                    "total_rating"))));

            // Genre
            TextView genreTextView = mView.get().findViewById(R.id.text_view_genre);
            StringBuilder genres = new StringBuilder();
            for (int i = 0; i < game.getJSONArray("genres").length(); i++) {
                if (i == 0 && i != game.getJSONArray("genres").length() - 1) {
                    genres.append(" ")
                            .append(game.getJSONArray("genres").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                } else if (i == game.getJSONArray("genres").length() - 1) {
                    genres.append(game.getJSONArray("genres").getJSONObject(i)
                            .getString("name"));
                } else {
                    genres.append(game.getJSONArray("genres").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                }
            }
            genreTextView.setText(genres.toString());

            // Platforms
            TextView platformsTextView = mView.get().findViewById(R.id.text_view_platforms);
            StringBuilder platforms = new StringBuilder();
            for (int i = 0; i < game.getJSONArray("platforms").length(); i++) {
                if (i == 0 && i != game.getJSONArray("platforms").length() - 1) {
                    platforms.append(" ")
                            .append(game.getJSONArray("platforms").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                } else if (i == game.getJSONArray("platforms").length() - 1) {
                    platforms.append(game.getJSONArray("platforms").getJSONObject(i)
                            .getString("name"));
                } else {
                    platforms.append(game.getJSONArray("platforms").getJSONObject(i)
                            .getString("name"))
                            .append(", ");
                }
            }
            platformsTextView.setText(platforms.toString());

            // Summary
            TextView summaryTextView = mView.get().findViewById(R.id.text_view_summary);
            summaryTextView.setText(game.getString("summary"));

            // Storyline
            TextView storylineTextView = mView.get().findViewById(R.id.text_view_storyline);
            if (game.has("storyline")) {
                storylineTextView.setText(game.getString("storyline"));
            }

            // Videos
            mVideoListAdapter.get().setVideos(videoArray);

            // Screenshots
            mScreenshotListAdapter.get().setScreenshots(screenshotArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
