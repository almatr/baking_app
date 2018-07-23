package com.example.android.myapplication.uiDetail;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.android.myapplication.R;
import com.example.android.myapplication.data.RecipeIngredients;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{

    private ArrayList<RecipeIngredients> mIngredients;

    //constructor
    public IngredientsAdapter(ArrayList<RecipeIngredients> ingredients)
    {mIngredients = ingredients;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mIngredients_text;
        private TextView mQuantity_text;
        private TextView mMeasure_text;

        public ViewHolder(View itemView) {
            super(itemView);
            mIngredients_text = itemView.findViewById(R.id.ingredient_text);
            mQuantity_text = itemView.findViewById(R.id.ingredient_text_quantity);
            mMeasure_text = itemView.findViewById(R.id.ingredient_text_measure);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingredientsView =
                inflater.inflate(R.layout.recipe_ingredients, parent, false);
        IngredientsAdapter.ViewHolder viewHolder =
                new IngredientsAdapter.ViewHolder(ingredientsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecipeIngredients singleIngredient = mIngredients.get(position);
        holder.mIngredients_text.setText(singleIngredient.getIngredient());
        holder.mQuantity_text.setText(Double.toString(singleIngredient.getQuantity()));
        holder.mMeasure_text.setText(singleIngredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) return 0;
        return mIngredients.size();
    }
}
