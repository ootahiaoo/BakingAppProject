package com.example.android.bakingappproject.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.ui.MainActivity;
import com.example.android.bakingappproject.ui.RecipeDetailsActivity;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsWidgetProvider extends AppWidgetProvider {

    public static final String KEY_WIDGET_INGREDIENTS_LIST = RecipeDetailsActivity
            .KEY_WIDGET_INGREDIENTS_LIST;
    public ArrayList<String> ingredientsForWidget;

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                ArrayList<String> ingredientsForWidget, int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                .recipe_ingredients_widget);

        if(ingredientsForWidget !=null) {
            views.setViewVisibility(R.id.empty_view, View.GONE);
            //Open the adapter
            Intent adapterIntent = new Intent(context, WidgetListAdapterService.class);
            adapterIntent.putExtra(KEY_WIDGET_INGREDIENTS_LIST, ingredientsForWidget);
            views.setRemoteAdapter(R.id.appwidget_listview, adapterIntent);
        } else{
            views.setViewVisibility(R.id.appwidget_listview, View.GONE);
            views.setTextViewText(R.id.empty_view, context.getString(R.string.widget_empty_view));
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent
                .FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_title_textview, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                          ArrayList<String> ingredientsForWidget, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, ingredientsForWidget, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

