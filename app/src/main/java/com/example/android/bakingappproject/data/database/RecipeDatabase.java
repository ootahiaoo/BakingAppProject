package com.example.android.bakingappproject.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

//@Database(entities = {RecipeEntry.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {
//
//    private static final String LOG_TAG = RecipeDatabase.class.getSimpleName();
//    private static final Object LOCK = new Object();
//    private static final String DATABASE_NAME = "recipedatabase";
//    private static RecipeDatabase databaseInstance;
//
//    public static RecipeDatabase getInstance(Context context) {
//        if (databaseInstance == null) {
//            synchronized (LOCK) {
//                Log.e(LOG_TAG, "Creating new database instance");
//                databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
//                        RecipeDatabase.class, RecipeDatabase.DATABASE_NAME).build();
//            }
//        }
//        Log.e(LOG_TAG, "Getting the database instance");
//        return databaseInstance;
//    }
//
//    public abstract RecipeDao movieDao();

}

