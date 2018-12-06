package com.example.android.bakingappproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.model.Ingredients;
import com.example.android.bakingappproject.data.model.RecipeEntry;
import com.example.android.bakingappproject.data.model.Steps;
import com.example.android.bakingappproject.ui.fragments.RecipeStepDetailsFragment;
import com.example.android.bakingappproject.ui.fragments.RecipeStepFragment;
import com.example.android.bakingappproject.ui.widget.WidgetService;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepFragment
        .CurrentStepClickListener {

    public static final String KEY_RECIPE_ITEM = MainActivity.KEY_RECIPE_ITEM;
    public static final String KEY_WIDGET_INGREDIENTS_LIST = "widgetIngredientsListKey";

    private RecipeEntry recipeEntry;
    private List<Ingredients> ingredientsList;
    private List<Steps> recipeSteps;
    private Steps currentSteps;
    private boolean mTwoPane;
    private String stepFragmentTag;
    private RecipeStepFragment stepFragment;
    private RecipeStepDetailsFragment stepDetailsFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        if (findViewById(R.id.fragment_linear_layout) != null) {mTwoPane = true;}

        if (savedInstanceState != null) {
            recipeEntry = savedInstanceState.getParcelable(KEY_RECIPE_ITEM);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                recipeEntry = bundle.getParcelable(KEY_RECIPE_ITEM);
            }
        }
        String recipeName = recipeEntry.getRecipeName();
        ingredientsList = recipeEntry.getRecipeIngredients();
        recipeSteps = recipeEntry.getRecipeSteps();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(recipeName);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        fragmentManager = getSupportFragmentManager();

        stepFragment = (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id
                .master_list_fragment);
        stepFragmentTag = stepFragment.getTag();
        stepFragment.setIngredientsList(ingredientsList);
        stepFragment.setDirectionsList(recipeSteps);

        if (mTwoPane) {
            stepDetailsFragment = RecipeStepDetailsFragment.newInstance(recipeSteps.get(0));
            fragmentManager.beginTransaction().add(R.id.step_details_container,
                    stepDetailsFragment).commit();
        }
    }


    public void onStepSelected(Steps currentStep) {
        currentSteps = currentStep;
        stepDetailsFragment = RecipeStepDetailsFragment.newInstance(currentStep);
        if (mTwoPane) {
            fragmentManager.beginTransaction().replace(R.id.step_details_container,
                    stepDetailsFragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.master_list_fragment,
                    stepDetailsFragment).addToBackStack(stepFragmentTag).commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putParcelable(KEY_RECIPE_ITEM, recipeEntry);
        super.onSaveInstanceState(currentState);
    }


    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            addFavorite();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Send the recipe ingredients to the widget
     **/
    public void addFavorite() {
        ArrayList<String> ingredientsForWidget = new ArrayList<>();
        int ingredientListSize = ingredientsList.size();
        for (int i = 0; i < ingredientListSize; i++) {
            final Ingredients currentIngredient = ingredientsList.get(i);
            String currentIngredientName = currentIngredient.getIngredient();
            String currentIngredientQuantity = currentIngredient.getQuantity();
            String currentIngredientMeasure = currentIngredient.getMeasure();
            String ingredientEntry = currentIngredientName + " (" + currentIngredientQuantity +
                    "" + " " + currentIngredientMeasure + ")";
            ingredientsForWidget.add(ingredientEntry);
        }
        Intent intent = new Intent(this, WidgetService.class);
        intent.putExtra(KEY_WIDGET_INGREDIENTS_LIST, ingredientsForWidget);
        startService(intent);
    }

}
