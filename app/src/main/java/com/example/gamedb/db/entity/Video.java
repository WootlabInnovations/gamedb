package com.example.gamedb.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Video {
    @PrimaryKey
    private Integer id;
    private String videoImage;
    private Integer gameId;
    private Long expiryDate;

    public Video(Integer id, String videoImage, Integer gameId, Long expiryDate) {
        this.id = id;
        this.videoImage = videoImage;
        this.gameId = gameId;
        this.expiryDate = expiryDate;
    }

    // TODO: Add Getters and Setters
}
