package com.example.android.myapplication.uiDetail;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.RecipeSteps;

import java.util.ArrayList;

public class MediaView extends AppCompatActivity {

    private String videoUrl;
    private String description;
    private String mRecipeName;
    private String mImage;
    private ArrayList<RecipeSteps> steps;
    private int position;
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        prevButton = findViewById(R.id.button_previous);
        nextButton = findViewById(R.id.button_next);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("Steps") && intent.hasExtra("position") &&
                    intent.hasExtra("recipe_name") && intent.hasExtra("recipe_image")) {
                steps = getIntent().getExtras()
                        .getParcelableArrayList("Steps");
                position = getIntent().getExtras().getInt("position", 0);
                mRecipeName = getIntent().getExtras().getString("recipe_name");
                mImage = getIntent().getExtras().getString("recipe_image");

                setTitle(mRecipeName);
                final RecipeSteps singleStep = steps.get(position);

                if (savedInstanceState == null) {
                    setFragment(singleStep);
                }
            }
        }


       View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.equals(prevButton) && position > 0) {
                    position = position -1 ;
                    setFragment(steps.get(position));
                    return;
                }

                if(v.equals(nextButton) && position < steps.size() - 1) {
                    position = position +1 ;
                    setFragment(steps.get(position));
                    return;
                }
            }
        };

        nextButton.setOnClickListener(listener);
        prevButton.setOnClickListener(listener);


    }

    private void setFragment(RecipeSteps singleStep){
        if(!singleStep.getVideoURL().isEmpty()){
            videoUrl = singleStep.getVideoURL();
        }else{
            videoUrl = singleStep.getThumbnailURL();
        }
        description = singleStep.getDescription();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        VideoFragment videoFragment = VideoFragment.newInstance(videoUrl, description, mImage);
        fragmentTransaction.replace(R.id.video_player, videoFragment);
        fragmentTransaction.commit();

    }

}
