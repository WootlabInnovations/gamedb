package com.example.gamedb.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamedb.asynctask.GameListAsyncTask;

import org.json.JSONArray;

public class GameListViewModel extends ViewModel {
    private MutableLiveData<JSONArray> games = new MutableLiveData<>();

    public GameListViewModel() { }

    public MutableLiveData<JSONArray> getGames() {
        // TODO: Return the Games MutableLiveData
        return null;
    }

    public void loadGames(int page) {
        // TODO: Make a async call to the remote server to get the list of games
    }
}
