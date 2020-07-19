package com.example.gamedb.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Game {
    @PrimaryKey
    private Integer id;

    private String backgroundImage;
    private String posterImage;
    private String name;
    private Long firstReleaseDate;
    private Double totalRating;
    private String summary;
    private String storyline;
    private Long expiryDate;

    public Game(Integer id, String backgroundImage, String posterImage, String name,
                Long firstReleaseDate, Double totalRating, String summary, String storyline,
                Long expiryDate) {
        this.id = id;
        this.backgroundImage = backgroundImage;
        this.posterImage = posterImage;
        this.name = name;
        this.firstReleaseDate = firstReleaseDate;
        this.totalRating = totalRating;
        this.summary = summary;
        this.storyline = storyline;
        this.expiryDate = expiryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(Long firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getStoryline() {
        return storyline;
    }

    public void setStoryline(String storyline) {
        this.storyline = storyline;
    }

    public Long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Long expiryDate) {
        this.expiryDate = expiryDate;
    }
}
