package com.example.android.bakingappproject.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class RecipeEntry implements Parcelable {

    @SerializedName("id")
    private int recipeId;

    @SerializedName("name")
    private String recipeName;

    @SerializedName("ingredients")
    private List<Ingredients> recipeIngredients;

    @SerializedName("steps")
    private List<Steps> recipeSteps;

    public RecipeEntry(int recipeId, String recipeName, List<Ingredients> recipeIngredients,
                       List<Steps> recipeSteps) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.recipeIngredients = recipeIngredients;
        this.recipeSteps = recipeSteps;
    }

    private RecipeEntry(Parcel in) {
        recipeId = in.readInt();
        recipeName = in.readString();
        recipeIngredients = in.createTypedArrayList(Ingredients.CREATOR);
        recipeSteps = in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<RecipeEntry> CREATOR = new Creator<RecipeEntry>() {
        @Override
        public RecipeEntry createFromParcel(Parcel in) {
            return new RecipeEntry(in);
        }

        @Override
        public RecipeEntry[] newArray(int size) {
            return new RecipeEntry[size];
        }
    };

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Ingredients> getRecipeIngredients() {
        return recipeIngredients;
    }
    public void setRecipeIngredients(List<Ingredients> recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public List<Steps> getRecipeSteps() {
        return recipeSteps;
    }
    public void setRecipeSteps(List<Steps> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeString(recipeName);
        dest.writeTypedList(recipeIngredients);
        dest.writeTypedList(recipeSteps);
    }
}
