package com.example.gamedb.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamedb.asynctask.GameListAsyncTask;

import org.json.JSONArray;

public class GameListViewModel extends ViewModel {
    private MutableLiveData<JSONArray> games = new MutableLiveData<>();

    public GameListViewModel() { }

    public MutableLiveData<JSONArray> getGames() {
        return games;
    }

    public void loadGames(int page) {
        new GameListAsyncTask(games).execute(page);
    }
}
