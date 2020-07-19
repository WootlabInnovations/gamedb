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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScreenshotImage() {
        return screenshotImage;
    }

    public void setScreenshotImage(String screenshotImage) {
        this.screenshotImage = screenshotImage;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
