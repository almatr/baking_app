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
import com.example.android.myapplication.data.CallBackListener;
import com.example.android.myapplication.data.RecipeIngredients;
import com.example.android.myapplication.data.RecipeSteps;
import java.util.ArrayList;

public class StepsFragment extends Fragment {

    private static final String STEPS = "Steps";
    private static final String INGREDIENTS = "Ingredients";
    private static final String RECIPE_NAME = "recipe_name";
    private static final String RECIPE_IMAGE = "recipe_image";
    private static final String TWO_PANE = "two_pane";

    private RecyclerView mSteps;
    private RecyclerView mIngredients;
    private StepsAdapter stepAdapter;
    private IngredientsAdapter mIngredientAdapter;
    private String mRecipeName;
    private String mImage;
    private boolean twoPane;
    private CallBackListener mCallBackListener;
    private ArrayList<RecipeSteps> recipeSteps = new ArrayList<RecipeSteps>();
    private ArrayList<RecipeIngredients> ingredients = new ArrayList<RecipeIngredients>();

    public static StepsFragment newInstance(ArrayList<RecipeSteps> steps,
                                            ArrayList<RecipeIngredients> ingredients,
                                            String mRecipeName, String image, boolean twoPane) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS, steps);
        bundle.putParcelableArrayList(INGREDIENTS, ingredients);
        bundle.putString(RECIPE_NAME, mRecipeName);
        bundle.putString(RECIPE_IMAGE, image);
        bundle.putBoolean(TWO_PANE, twoPane);

        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            recipeSteps = bundle.getParcelableArrayList(STEPS);
            ingredients = bundle.getParcelableArrayList(INGREDIENTS);
            mRecipeName = bundle.getString(RECIPE_NAME);
            mImage = bundle.getString(RECIPE_IMAGE);
            twoPane = bundle.getBoolean(TWO_PANE);
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
        stepAdapter = new StepsAdapter(recipeSteps, mRecipeName, mImage, twoPane,
                mCallBackListener);
        mSteps.setAdapter(stepAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mCallBackListener = ((CallBackListener) context);
    }
}
