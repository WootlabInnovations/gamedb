package com.example.gamedb.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamedb.db.entity.Video;

@Dao
public interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Video... videos);

    @Update
    void update(Video... videos);

    @Query("DELETE FROM Video WHERE expiryDate > :date")
    void deleteAll(Long date);
}
