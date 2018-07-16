package com.example.android.myapplication.uiDetail;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.myapplication.R;
import com.example.android.myapplication.data.RecipeIngredients;
import com.example.android.myapplication.data.RecipeSteps;

import java.util.ArrayList;

public class StepsFragment extends Fragment {

    private RecyclerView mSteps;
    private RecyclerView mIngredients;
    private StepsAdapter stepAdapter;
    private IngredientsAdapter mIngredientAdapter;

    ArrayList<RecipeSteps> recipeSteps = new ArrayList<RecipeSteps>();
    ArrayList<RecipeIngredients> ingredients = new ArrayList<RecipeIngredients>();

    public static StepsFragment newInstance(ArrayList<RecipeSteps> steps,
                                            ArrayList<RecipeIngredients> ingredients) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Steps", steps);
        bundle.putParcelableArrayList("Ingredients", ingredients);

        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            recipeSteps = bundle.getParcelableArrayList("Steps");
            ingredients = bundle.getParcelableArrayList("Ingredients");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.recipe_detail_recycler, container,
                false);
        readBundle(getArguments());
        mIngredients = rootView.findViewById(R.id.ingredients);
        mIngredients.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mIngredients.setHasFixedSize(false);
        mIngredientAdapter = new IngredientsAdapter(ingredients);
        mIngredients.setAdapter(mIngredientAdapter);

        mSteps = rootView.findViewById(R.id.steps);
        mSteps.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        mSteps.setHasFixedSize(false);
        stepAdapter = new StepsAdapter(recipeSteps);
        mSteps.setAdapter(stepAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
