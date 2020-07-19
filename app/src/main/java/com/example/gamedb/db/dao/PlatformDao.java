package com.example.gamedb.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamedb.db.entity.Platform;

@Dao
public interface PlatformDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Platform... platforms);

    @Update
    void update(Platform... platforms);

    @Query("DELETE FROM Platform WHERE expiryDate > :date")
    void deleteAll(Long date);
}
