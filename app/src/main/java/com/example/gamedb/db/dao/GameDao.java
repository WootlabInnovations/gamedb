package com.example.gamedb.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.gamedb.db.entity.Game;
import com.example.gamedb.db.entity.GameGenres;
import com.example.gamedb.db.entity.GamePlatforms;
import com.example.gamedb.db.entity.GameScreenshots;
import com.example.gamedb.db.entity.GameVideos;

import java.util.List;

@Dao
public interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Game... games);

    @Update
    void update(Game... games);

    @Query("DELETE FROM Game WHERE expiryDate > :date")
    void deleteAll(Long date);

    @Query("SELECT * FROM Game")
    LiveData<List<Game>> listAll();

    @Query("SELECT * FROM Game WHERE id = :id")
    LiveData<Game> getGame(int id);

    @Transaction
    @Query("SELECT * FROM Game WHERE id = :id")
    LiveData<GameGenres> getGameGenres(int id);

    /*
      * TODO: Add Dao queries for GamePlatforms, GameScreenshots and GameVideos
     */

}
