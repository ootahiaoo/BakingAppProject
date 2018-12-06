package com.example.android.bakingappproject.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.android.bakingappproject.data.database.RecipeEntry;

import java.util.List;

//@Dao
public interface RecipeDao {

//    @Query("SELECT * FROM recipe ORDER BY recipe_id DESC")
//    LiveData<List<RecipeEntry>> loadAllRecipes();
//
//    @Insert
//    void insertRecipe(RecipeEntry recipe);
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    void updateRecipe(RecipeEntry recipe);
//
//    @Delete
//    void deleteRecipe(RecipeEntry recipe);
//
//    @Query("select * from recipe where recipe_id = :recipeId")
//    LiveData<RecipeEntry> loadRecipeItem(int recipeId);

}
