package com.example.gamedb.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamedb.asynctask.GameDetailAsyncTask;

import org.json.JSONArray;

public class GameDetailViewModel extends ViewModel {
    private MutableLiveData<JSONArray> game = new MutableLiveData<>();

    public GameDetailViewModel() { }

    public MutableLiveData<JSONArray> getGame() {
        return game;
    }

    public void loadGame(int id) {
        new GameDetailAsyncTask(game).execute(id);
    }
}
