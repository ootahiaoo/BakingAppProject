package com.example.android.bakingappproject;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.android.bakingappproject.data.database.RecipeDao;
import com.example.android.bakingappproject.data.database.RecipeDatabase;
import com.example.android.bakingappproject.data.database.RecipeEntry;
import com.example.android.bakingappproject.data.network.NetworkUtils;
import com.example.android.bakingappproject.data.network.RecipeService;
import com.example.android.bakingappproject.data.network.RetrofitClientInstance;
import com.example.android.bakingappproject.ui.MainActivity;
import com.example.android.bakingappproject.ui.RecipeDetailsActivity;
import com.example.android.bakingappproject.ui.RecipeGridviewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static DataRepository repositoryInstance;
    private static final Object LOCK = new Object();
//    private static RecipeDatabase mDatabase;
    private static RecipeDao mRecipeDao;
    private static AppExecutors mExecutors;
    private static List<RecipeEntry> recipeList;
    private List<RecipeEntry> recipeItemList;

    private DataRepository(RecipeDao recipeDao, AppExecutors executors) {
//        this.mDatabase = database;
        this.mRecipeDao = recipeDao;
        this.mExecutors = executors;
    }

    public static DataRepository getInstance() {
        if (repositoryInstance == null) {
            synchronized (LOCK) {
                synchronized (DataRepository.class) {
                    if (repositoryInstance == null) {
                        repositoryInstance = new DataRepository(mRecipeDao, mExecutors);
                    }
                }
            }
        }
        return repositoryInstance;
    }

//    /**
//     * Get the list of products from the database and get notified when the data changes.
//     */
//    public LiveData<List<RecipeEntry>> getRecipeList() {
//        return mDatabase.movieDao().loadAllRecipes();
//    }
//
//    public LiveData<RecipeEntry> getRecipeEntryById(int recipeId) {
//        return mDatabase.movieDao().loadRecipeItem(recipeId);
//    }
//
//    public void addRecipeEntry(RecipeEntry recipeEntry) {
//        mDatabase.movieDao().insertRecipe(recipeEntry);
//    }
//
//    public void updateRecipeEntry(RecipeEntry recipeEntry) {
//        mDatabase.movieDao().updateRecipe(recipeEntry);
//    }


}
