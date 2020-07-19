package com.example.gamedb.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Screenshot {
    @PrimaryKey
    private Integer id;
    private String screenshotImage;
    private Integer gameId;
    private Long expiryDate;

    public Screenshot(Integer id, String screenshotImage, Integer gameId,
                      Long expiryDate) {
        this.id = id;
        this.screenshotImage = screenshotImage;
        this.gameId = gameId;
        this.expiryDate = expiryDate;
    }

    // TODO: Add Getters and Setters
}
