package com.example.android.bakingappproject.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.database.Ingredients;
import com.example.android.bakingappproject.data.database.Steps;
import com.example.android.bakingappproject.ui.RecipeDetailsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepFragment extends Fragment {

    @BindView(R.id.recycler_view_ingredients)
    RecyclerView ingredientsRecyclerView;

    @BindView(R.id.recycler_view_steps)
    RecyclerView stepsRecyclerView;
    public static final String KEY_CURRENT_STEP = "currentStepKey";

    CurrentStepClickListener mCallback;
    RecipeDetailsActivity activity;

    public interface CurrentStepClickListener {
        void onStepSelected(Steps currentStep);
    }

    public RecipeStepFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeDetailsActivity) {
            this.activity = (RecipeDetailsActivity) context;
        }
        try {
            mCallback = (CurrentStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement CurrentStepClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_step, container, false);
        ButterKnife.bind(this, rootView);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }


    public void setIngredientsList(List<Ingredients> ingredientsList) {
        StepFragmentIngredientsAdapter ingredientsAdapter = new StepFragmentIngredientsAdapter
                (getContext(), ingredientsList);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        ingredientsRecyclerView.addItemDecoration(new DividerItemDecoration(ingredientsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
    }

    public void setDirectionsList(final List<Steps> recipeSteps) {
        StepFragmentDirectionsAdapter directionsAdapter = new StepFragmentDirectionsAdapter
                (getContext(), recipeSteps);
        stepsRecyclerView.setAdapter(directionsAdapter);
        stepsRecyclerView.addItemDecoration(new DividerItemDecoration(stepsRecyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        directionsAdapter.setOnItemClickListener(new StepFragmentDirectionsAdapter.ClickListener
                () {
            @Override
            public void onItemClick(int position, View v) {
                Steps currentStep = recipeSteps.get(position);
                mCallback.onStepSelected(currentStep);
            }
        });
    }


}
