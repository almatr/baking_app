package com.example.android.myapplication.uiDetail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.android.myapplication.R;
import com.example.android.myapplication.data.CallBackListener;
import com.example.android.myapplication.data.Recipe;
import com.example.android.myapplication.data.RecipeIngredients;
import com.example.android.myapplication.data.RecipeSteps;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StepsIngredients extends AppCompatActivity implements CallBackListener {

    private Recipe recipe;
    private ArrayList<RecipeSteps> recipeSteps;

    private static final String LAST_CLICKED_RECIPE = "lastClickedRecipe";
    private static final String CLICKED_RECIPE_NAME = "clickedRecipeName";
    private static final String INGREDIENTS = "Ingredients";
    private static final String STEPS = "Steps";
    private static final String RECIPE = "Recipe";


    @Override
    public void onCallBack(String url, String description, String mImage) {
        FragmentManager fragmentManagerMedia = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionMedia = fragmentManagerMedia.beginTransaction();
        VideoFragment videoFragment = VideoFragment.newInstance(url, description, mImage);
        fragmentTransactionMedia.replace(R.id.video_player, videoFragment);
        fragmentTransactionMedia.commit();
    }

    private ArrayList<RecipeIngredients> recipeIngredients;
    private String mRecipeName;
    private String mImage;
    private boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_main);

        Context context = getApplicationContext();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(RECIPE)) {
                recipe = (Recipe) getIntent().getParcelableExtra(RECIPE);
                recipeIngredients = getIntent().getExtras()
                        .getParcelableArrayList(INGREDIENTS);
                recipeSteps = getIntent().getExtras().getParcelableArrayList(STEPS);
                mRecipeName = recipe.getName();
                mImage = recipe.getImage();

                setPreferences(recipeIngredients, mRecipeName);
            }
        }

        //set title to be name of the recipe clicked
        setTitle(mRecipeName);
        if (findViewById(R.id.recipe_detail_landscape) != null) {
            mTwoPane = true;
        }

        FragmentManager fragmentManagerStep = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionStep = fragmentManagerStep.beginTransaction();
        StepsFragment fragmentStep = StepsFragment
                .newInstance(recipeSteps, recipeIngredients, mRecipeName, mImage, mTwoPane);
        fragmentTransactionStep.replace(R.id.master_list_fragment, fragmentStep);
        fragmentTransactionStep.commit();
    }

    private void setPreferences(ArrayList<RecipeIngredients> ingredients, String recipeName){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(LAST_CLICKED_RECIPE,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(ingredients);
        editor.putString(INGREDIENTS, json);
        editor.putString(CLICKED_RECIPE_NAME,recipeName);
        editor.commit();
        RecipeWidgetProvider.sendRefreshBroadcast(context);
    }
}
