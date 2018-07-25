package com.example.android.myapplication.uiDetail;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private static final String RECIPE_NAME = "recipe_name";
    private static final String STEPS = "Steps";
    private static final String POSITION = "position";
    private static final String RECIPE_IMAGE = "recipe_image";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        //Previous and next buttons
        prevButton = findViewById(R.id.button_previous);
        nextButton = findViewById(R.id.button_next);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(STEPS) && intent.hasExtra(POSITION) &&
                    intent.hasExtra(RECIPE_NAME) && intent.hasExtra(RECIPE_IMAGE)) {
                steps = getIntent().getExtras()
                        .getParcelableArrayList(STEPS);
                position = getIntent().getExtras().getInt(POSITION, 0);
                mRecipeName = getIntent().getExtras().getString(RECIPE_NAME);
                mImage = getIntent().getExtras().getString(RECIPE_IMAGE);

                //Set title to be the name of the selected recipe
                setTitle(mRecipeName);
                final RecipeSteps singleStep = steps.get(position);
                if (savedInstanceState == null) {
                    setFragment(singleStep);
                }
            }
        }

       //Previous/next buttons click listener
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
        String thumbnailUrlString = singleStep.getThumbnailURL();
        if(!singleStep.getVideoURL().isEmpty()){
            videoUrl = singleStep.getVideoURL(); //get video url
        }else{
            if (mImage.isEmpty()&& (
                    thumbnailUrlString.endsWith("webp")
                    || thumbnailUrlString.endsWith("jpg")
                    || thumbnailUrlString.endsWith("png")
                    || thumbnailUrlString.endsWith("bmp")
                    || thumbnailUrlString.endsWith("gif"))) {
                mImage = thumbnailUrlString;
            }else {
                videoUrl = singleStep.getThumbnailURL();
            }
        }
        description = singleStep.getDescription();

        //start fragment transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        VideoFragment videoFragment = VideoFragment.newInstance(videoUrl, description, mImage);
        fragmentTransaction.replace(R.id.video_player, videoFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("position", position);
    }
}
