package com.example.android.bakingappproject.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.bakingappproject.DataRepository;
import com.example.android.bakingappproject.data.database.RecipeEntry;

//public class RecipeEntryViewModel extends ViewModel {
//
//    private final DataRepository dataRepository;
//    private LiveData<RecipeEntry> recipeEntry;
//
//    public RecipeEntryViewModel(int recipeId) {
//        dataRepository = DataRepository.getInstance();
//        recipeEntry = dataRepository.getRecipeEntryById(recipeId);
//    }
//
//    public LiveData<RecipeEntry> getRecipeEntry() {
//        return recipeEntry;
//    }
//}