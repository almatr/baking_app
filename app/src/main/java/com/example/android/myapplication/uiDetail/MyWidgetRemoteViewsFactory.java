package com.example.android.myapplication.uiDetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.RecipeIngredients;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory{

    private Context mContext;
    private ArrayList<RecipeIngredients> ingredients;
    private int mWidgetId;
    private static final String LAST_CLICKED_RECIPE = "lastClickedRecipe";

    public MyWidgetRemoteViewsFactory(Context applicationContext, int appWidgetId) {
        mContext = applicationContext;
        mWidgetId = appWidgetId;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences preferences = mContext.getSharedPreferences(LAST_CLICKED_RECIPE,
                Context.MODE_PRIVATE);
        String ingredientsL = preferences.getString("ingredients", "");
        Gson gson = new Gson();
        RecipeIngredients[] ingredientsParse = gson.fromJson(ingredientsL,
                RecipeIngredients[].class);
        ingredients = new ArrayList<RecipeIngredients>
                (Arrays.asList(ingredientsParse));
    }

    @Override
    public void onDestroy() {
        ingredients.clear();
    }

    @Override
    public int getCount() {
        if(ingredients.size() == 0)
            return 0;
        else
            return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position == AdapterView.INVALID_POSITION || ingredients.isEmpty()){
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setTextViewText(R.id.ingredient_text, ingredients.get(position).getIngredient());
        rv.setTextViewText(R.id.ingredient_text_quantity,
                Double.toString(ingredients.get(position).getQuantity()));
        rv.setTextViewText(R.id.ingredient_text_measure, ingredients.get(position).getMeasure());

        return rv;
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
        return false;
    }
}
