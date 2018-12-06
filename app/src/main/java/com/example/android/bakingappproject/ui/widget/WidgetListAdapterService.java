package com.example.android.bakingappproject.ui.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingappproject.R;
import java.util.ArrayList;
public class WidgetListAdapterService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetListAdapter(this.getApplicationContext(), intent));
    }

    class WidgetListAdapter implements RemoteViewsService.RemoteViewsFactory {

        ArrayList<String> ingredientsForWidget;
        private Context context;

        public WidgetListAdapter(Context applicationContext, Intent intent) {
            this.context = applicationContext;
            ingredientsForWidget = intent.getExtras().getStringArrayList
                    (WidgetService.KEY_WIDGET_INGREDIENTS_LIST);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            ingredientsForWidget = WidgetService.ingredientsForWidget;
        }

        @Override
        public void onDestroy() {
        }

        @Override
        public int getCount() {
            if (null == ingredientsForWidget) return 0;
            return ingredientsForWidget.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout
                    .widget_listview_item);
            String currentIngredientEntry = ingredientsForWidget.get(position);
            views.setTextViewText(R.id.textview_ingredient_entry, currentIngredientEntry);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}