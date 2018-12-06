package com.example.android.bakingappproject.ui.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.model.Ingredients;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragmentIngredientsAdapter extends RecyclerView
        .Adapter<StepFragmentIngredientsAdapter.StepFragmentIngredientsAdapterViewHolder> {

    @BindView(R.id.textview_quantity)
    TextView quantityTextView;
    @BindView(R.id.textview_measure)
    TextView measureTextView;
    @BindView(R.id.textview_ingredient_name)
    TextView ingredientNameTextView;

    private Context context;
    private final List<Ingredients> ingredientsList;


    public StepFragmentIngredientsAdapter(Context context, List<Ingredients> ingredientsList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public StepFragmentIngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.details_recyclerview_ingredients_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ButterKnife.bind(this, view);
        StepFragmentIngredientsAdapterViewHolder viewHolder = new
                StepFragmentIngredientsAdapterViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepFragmentIngredientsAdapterViewHolder viewHolder, int i) {
        Ingredients currentIngredient = ingredientsList.get(i);
        quantityTextView.setText(currentIngredient.getQuantity());
        measureTextView.setText(currentIngredient.getMeasure());
        ingredientNameTextView.setText(currentIngredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if (null == ingredientsList) return 0;
        return ingredientsList.size();
    }


    public class StepFragmentIngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        private StepFragmentIngredientsAdapterViewHolder(View view) {
            super(view);
        }

    }
}

