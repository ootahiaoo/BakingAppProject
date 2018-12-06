package com.example.android.bakingappproject.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingappproject.IdlingResource.SimpleIdlingResource;
import com.example.android.bakingappproject.R;
import com.example.android.bakingappproject.data.model.RecipeEntry;
import com.example.android.bakingappproject.data.network.NetworkUtils;
import com.example.android.bakingappproject.data.network.RecipeService;
import com.example.android.bakingappproject.data.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.loading_spinner)
    ProgressBar spinner;
    @BindView(R.id.empty_view)
    TextView emptyStateTextView;
    @BindView(R.id.recipe_gridview)
    RecyclerView recyclerView;

    public static final String KEY_RECIPE_ITEM = "recipeItem";
    private List<RecipeEntry> recipeItemList;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {mIdlingResource = new SimpleIdlingResource();}
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.main_activity_name);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinner.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

        getIdlingResource();
        if (mIdlingResource != null) { mIdlingResource.setIdleState(false);}

        boolean isConnected = NetworkUtils.checkNetworkAvailability(this);
        if (!isConnected) {
            noInternetConnection();
        } else {
            spinner.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);

            //Check if device is a phone or tablet, then decide the GridView number of column
            if (getApplicationContext().getResources().getConfiguration().smallestScreenWidthDp
                    >= 600) {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            }

            RecipeService service = RetrofitClientInstance.getRetrofitInstance().create
                    (RecipeService.class);
            Call<List<RecipeEntry>> recipeListCall = service.getRecipeList();
            recipeListCall.enqueue(new Callback<List<RecipeEntry>>() {
                @Override
                public void onResponse(Call<List<RecipeEntry>> call, Response<List<RecipeEntry>>
                        response) {
                    recipeItemList = response.body();
                    RecipeGridviewAdapter adapter = new RecipeGridviewAdapter(MainActivity.this,
                            recipeItemList);
                    recyclerView.setAdapter(adapter);

                    if (mIdlingResource != null) {mIdlingResource.setIdleState(true);}

                    adapter.setOnItemClickListener(new RecipeGridviewAdapter.ClickListener() {
                        @Override
                        public void onItemClick(int position, View v) {
                            RecipeEntry recipeEntry = recipeItemList.get(position);
                            Intent intent = new Intent(MainActivity
                                    .this, RecipeDetailsActivity.class);
                            intent.putExtra(KEY_RECIPE_ITEM, recipeEntry);
                            startActivity(intent);
                        }
                    });
                }
                @Override
                public void onFailure(Call<List<RecipeEntry>> call, Throwable t) {
                    noInternetConnection();
                }
            });
        }
    }

    public void noInternetConnection() {
        spinner.setVisibility(View.INVISIBLE);
        emptyStateTextView.setText(R.string.no_internet_connection);
    }

}
