package com.example.android.bakingappproject.data.network;

import com.example.android.bakingappproject.data.database.RecipeEntry;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Call<List<RecipeEntry>> getRecipeList();

//    @GET()
//    Call<List<RecipeEntry>> getRecipeEntry(int recipeId);
}


