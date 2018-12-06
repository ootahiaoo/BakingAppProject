package com.example.android.bakingappproject.ui.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.ui.RecipeDetailsActivity;

import java.util.ArrayList;

public class WidgetService extends IntentService {

    public static final String KEY_WIDGET_INGREDIENTS_LIST = RecipeDetailsActivity
            .KEY_WIDGET_INGREDIENTS_LIST;
    public static ArrayList<String> ingredientsForWidget;

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ingredientsForWidget = intent.getExtras().getStringArrayList
                    (KEY_WIDGET_INGREDIENTS_LIST);
            handleActionUpdateWidgetIngredients(ingredientsForWidget);
        }
    }

    private void handleActionUpdateWidgetIngredients(ArrayList<String> ingredientsForWidget) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                RecipeIngredientsWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_listview);

        RecipeIngredientsWidgetProvider.updateAllAppWidgets(this, appWidgetManager,
                ingredientsForWidget, appWidgetIds);
    }


}
