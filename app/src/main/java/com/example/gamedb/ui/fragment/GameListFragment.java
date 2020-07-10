package com.example.gamedb.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.gamedb.R;
import com.example.gamedb.adapter.GameListAdapter;
import com.example.gamedb.asynctask.GameListAsyncTask;
import com.example.gamedb.loader.GameListAsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;

public class GameListFragment extends Fragment implements LoaderManager.LoaderCallbacks<JSONArray> {
    private RecyclerView recyclerView;
    private GameListAdapter mGameListAdapter;
    private GridLayoutManager gridLayoutManager;
    private ProgressBar mProgressBar;
    private OnGameListFragmentInteractionListener mListener;
    private int mPage = 1;
    private int mPosition;
    private LoaderManager mLoaderManager;

    private final int LOADER_ID = 1;

    public static final String GAME_ID = "GAME_ID";
    public static final String PAGE = GameListFragment.class.getName() + "_PAGE";

    public GameListFragment() {
        // Required empty public constructor
    }

    public static GameListFragment newInstance() {
        return new GameListFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnGameListFragmentInteractionListener) {
            mListener = (OnGameListFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString() +
                    " must implement OnGameListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate the loader manager
        mLoaderManager = LoaderManager.getInstance(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_game_list_fragment);

        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        mGameListAdapter = new GameListAdapter(mListener);
        recyclerView.setAdapter(mGameListAdapter);

        mProgressBar = view.findViewById(R.id.progress_bar);

        // Pass the page number as a bundle to the loader
        Bundle bundle = new Bundle();
        bundle.putInt(PAGE, mPage);
        mLoaderManager.restartLoader(LOADER_ID, bundle, this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMoreGames(recyclerView);
            }
        });

        return view;
    }

    public void loadMoreGames(RecyclerView recyclerView) {
        int lastPosition = ((GridLayoutManager) recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
        if (lastPosition == mGameListAdapter.getItemCount() - 1) {
            mProgressBar.setVisibility(View.VISIBLE);
            mPage += 1;
            mPosition = lastPosition + 1;

            Bundle bundle = new Bundle();
            bundle.putInt(PAGE, mPage);
            mLoaderManager.restartLoader(LOADER_ID, bundle, this);
        }
    }

    @NonNull
    @Override
    public Loader<JSONArray> onCreateLoader(int id, @Nullable Bundle args) {
        return new GameListAsyncTaskLoader(getContext(), args.getInt(PAGE));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONArray> loader, JSONArray data) {
        /*
         * TODO: Implement what happens after the loader finishes execution.
         *  Hint: Take a look at the onPostExecute method of your AsyncTask
         */
    }

    @Override
    public void onLoaderReset(@NonNull Loader<JSONArray> loader) {

    }

    public interface OnGameListFragmentInteractionListener {
        void onGameSelected(int gameId);
    }
}