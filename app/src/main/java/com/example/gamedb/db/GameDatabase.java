package com.example.gamedb.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gamedb.db.dao.GameDao;
import com.example.gamedb.db.dao.GenreDao;
import com.example.gamedb.db.dao.PlatformDao;
import com.example.gamedb.db.dao.ScreenshotDao;
import com.example.gamedb.db.dao.VideoDao;
import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.Genre;
import com.example.gamedb.db.entity.Platform;
import com.example.gamedb.db.entity.Screenshot;
import com.example.gamedb.db.entity.Video;

@Database(entities = {Game.class, Genre.class, Platform.class, Screenshot.class, Video.class},
        version = 1)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDao gameDao();
    public abstract GenreDao genreDao();
    public abstract PlatformDao platformDao();
    public abstract ScreenshotDao screenshotDao();
    public abstract VideoDao videoDao();

    private static GameDatabase INSTANCE;

    public static GameDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GameDatabase.class, "game_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
