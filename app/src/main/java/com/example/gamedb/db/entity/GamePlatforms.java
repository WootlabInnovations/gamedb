package com.example.gamedb.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GamePlatforms {
    @Embedded
    public Game game;

    @Relation(parentColumn = "id", entityColumn = "gameId")
    public List<Platform> platforms;
}
