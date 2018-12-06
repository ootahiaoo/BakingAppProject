package com.example.android.bakingappproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.android.bakingappproject.DataRepository;
import com.example.android.bakingappproject.data.database.RecipeEntry;

import java.util.List;

//public class RecipeListViewModel  extends AndroidViewModel {
//
//    private final DataRepository dataRepository;
//    private LiveData<List<RecipeEntry>> recipeList;
//
//    public RecipeListViewModel(@NonNull Application application) {
//        super(application);
//        dataRepository = DataRepository.getInstance();
//        recipeList = dataRepository.getRecipeList();
//    }
//
//    public LiveData<List<RecipeEntry>> getRecipeList(){
//        return recipeList;
//    }
//}
