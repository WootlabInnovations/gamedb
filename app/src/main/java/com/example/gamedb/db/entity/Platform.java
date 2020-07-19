package com.example.gamedb.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Platform {
    @PrimaryKey
    private Integer id;
    private String name;
    private Integer gameId;
    private Long expiryDate;

    public Platform(Integer id, String name, Integer gameId, Long expiryDate) {
        this.id = id;
        this.name = name;
        this.gameId = gameId;
        this.expiryDate = expiryDate;
    }

    // TODO: Add Getters and Setters
}
