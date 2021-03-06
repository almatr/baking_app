package com.example.android.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.android.myapplication.R;
import com.example.android.myapplication.data.Recipe;
import com.example.android.myapplication.data.RecipeIngredients;
import com.example.android.myapplication.data.RecipeSteps;
import com.example.android.myapplication.uiDetail.StepsIngredients;

import java.util.ArrayList;

public class MasterListAdapter extends BaseAdapter {

    // Keeps track of the context and list of recipe names to display
    private Context mContext;
    private ArrayList<Recipe> mRecipes;
    private static final String SERVINGS = "Servings: ";
    private static final String RECIPE = "Recipe";
    private static final String STEPS = "Steps";
    private static final String INGREDIENTS = "Ingredients";

     // Constructor method
    public MasterListAdapter(Context context, ArrayList<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    static class ViewHolder {
        TextView recipeName;
        TextView recipeServings;
    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();
        final Recipe singleRecipe = mRecipes.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipes_list_all, parent, false);
        }
        CardView cardView = convertView.findViewById(R.id.recipe_card);
        holder.recipeName = convertView.findViewById(R.id.recipe_name);
        holder.recipeServings = convertView.findViewById(R.id.recipe_servings);
        holder.recipeName.setText(singleRecipe.getName());
        holder.recipeServings.setText(SERVINGS + Integer.toString
                (singleRecipe.getServings()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, StepsIngredients.class);
                Bundle mBundle = new Bundle();
                ArrayList<RecipeSteps> recipeSteps = new ArrayList<RecipeSteps>();
                ArrayList<RecipeIngredients> recipeIngredients = new ArrayList<RecipeIngredients>();
                recipeSteps = singleRecipe.getmRecipeSteps();
                recipeIngredients = singleRecipe.getmRecipeIngredients();
                mBundle.putParcelable(RECIPE, singleRecipe);
                mBundle.putParcelableArrayList(STEPS, recipeSteps);
                mBundle.putParcelableArrayList(INGREDIENTS, recipeIngredients);
                mIntent.putExtras(mBundle);
                if (mIntent.resolveActivity(mContext.getPackageManager()) != null){
                    mContext.startActivity(mIntent);
                }
            }
        });
        return convertView;
    }
}


