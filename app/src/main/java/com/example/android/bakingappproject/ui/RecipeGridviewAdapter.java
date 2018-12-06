package com.example.android.bakingappproject.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.database.RecipeEntry;

import java.util.List;

public class RecipeGridviewAdapter extends RecyclerView.Adapter<RecipeGridviewAdapter
        .RecipeAdapterViewHolder> {

    private Context context;
    private final List<RecipeEntry> recipeList;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(ClickListener clickListener){
        RecipeGridviewAdapter.clickListener = clickListener;
    }

    public RecipeGridviewAdapter(Context context, List<RecipeEntry> recipeList){
        this.context = context;
        this.recipeList = recipeList;
    }


    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.gridview_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        RecipeAdapterViewHolder viewHolder = new RecipeAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder viewHolder, int i) {
        RecipeEntry recipeItem = recipeList.get(i);
        String recipeName = recipeItem.getRecipeName();
        viewHolder.recipeNameTextView.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (null == recipeList) return 0;
        return recipeList.size();
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView recipeNameTextView;

        private RecipeAdapterViewHolder(View view) {
            super(view);
            recipeNameTextView = (TextView) view.findViewById(R.id.textview_recipe_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
