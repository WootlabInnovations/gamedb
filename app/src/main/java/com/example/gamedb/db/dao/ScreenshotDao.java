package com.example.gamedb.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamedb.db.entity.Screenshot;

@Dao
public interface ScreenshotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Screenshot... screenshots);

    @Update
    void update(Screenshot... screenshots);

    @Query("DELETE FROM Screenshot WHERE expiryDate > :date")
    void deleteAll(Long date);
}
