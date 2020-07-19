package com.example.gamedb.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamedb.db.entity.Genre;

@Dao
public interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Genre... genres);

    @Update
    void update(Genre... genres);

    @Query("DELETE FROM Genre WHERE expiryDate > :date")
    void deleteAll(Long date);
}
