package com.example.android.myapplication.uiDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.CallBackListener;
import com.example.android.myapplication.data.RecipeSteps;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private ArrayList<RecipeSteps> mSteps;
    private String mRecipeName;
    private String mImage;
    private boolean twoPane;
    private CallBackListener mCallBackListener;

    //constructor
    public StepsAdapter(ArrayList<RecipeSteps> steps, String recipeName, String image,
                        boolean twoPaneLayout, CallBackListener mCallBackListener) {
        mSteps = steps;
        mRecipeName = recipeName;
        mImage = image;
        twoPane = twoPaneLayout;
        this.mCallBackListener = mCallBackListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView recipe_step;

        public ViewHolder(View itemView) {
            super(itemView);
            recipe_step = itemView.findViewById(R.id.recipe_single_step);
        }
    }

    @NonNull
    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stepsView = inflater.inflate(R.layout.recipe_steps, parent, false);
        ViewHolder viewHolder = new ViewHolder(stepsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, final int position) {
        final RecipeSteps singleStep = mSteps.get(position);
        holder.recipe_step.setText(singleStep.getShortDescription().toString());
        holder.recipe_step.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Bundle mBundle = new Bundle();
                mBundle.putParcelableArrayList("Steps", mSteps);
                mBundle.putInt("position", position);
                mBundle.putString("recipe_name", mRecipeName);
                mBundle.putString("recipe_image", mImage);
                if(!twoPane){
                    Intent intent = new Intent(view.getContext(), MediaView.class);
                    intent.putExtras(mBundle);
                    if (intent.resolveActivity(view.getContext().getPackageManager()) != null){
                        view.getContext().startActivity(intent);
                    }
                } else {
                    String video_url = singleStep.getVideoURL();
                    if (singleStep.getVideoURL().isEmpty()) {
                        video_url = singleStep.getThumbnailURL();
                    }
                    mCallBackListener.onCallBack(
                            video_url,
                            singleStep.getDescription(),
                            mImage);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mSteps == null) return 0;
        return mSteps.size();
    }
}
