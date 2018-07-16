package com.example.android.myapplication.uiDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.Recipe;
import com.example.android.myapplication.data.RecipeIngredients;
import com.example.android.myapplication.data.RecipeSteps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.util.ArrayList;

public class StepsIngredients extends AppCompatActivity {

    Recipe recipe;
    ArrayList<RecipeSteps> recipeSteps;
    ArrayList<RecipeIngredients> recipeIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Recipe")) {
                recipe = (Recipe) getIntent().getParcelableExtra("Recipe");
                recipeIngredients = getIntent().getExtras()
                        .getParcelableArrayList("Ingredients");
                recipeSteps = getIntent().getExtras().getParcelableArrayList("Steps");
            }
        }

        if(savedInstanceState == null){
            StepsFragment fragmentStep = StepsFragment.newInstance(recipeSteps, recipeIngredients);
            fragmentTransaction.replace(android.R.id.content, fragmentStep);
            fragmentTransaction.commit();
        }
    }
}
