package com.example.gamedb.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gamedb.asynctask.GameDetailAsyncTask;

import org.json.JSONArray;

public class GameDetailViewModel extends ViewModel {
    private MutableLiveData<JSONArray> game = new MutableLiveData<>();

    public GameDetailViewModel() { }

    public MutableLiveData<JSONArray> getGame() {
        // TODO: Return the Game MutableLiveData
        return null;
    }

    public void loadGame(int id) {
        // TODO: Make a async call to the remote server to get the game detail
    }
}
