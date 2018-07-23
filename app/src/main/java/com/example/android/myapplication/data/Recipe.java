package com.example.android.myapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    private int id;
    private int servings;
    private String name;
    private String image;
    private ArrayList<RecipeSteps> steps = new ArrayList<RecipeSteps>();
    private ArrayList<RecipeIngredients> ingredients = new ArrayList<RecipeIngredients>();;

    public Recipe(){}

    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setImage(String image) {
        this.image = image;
    }

    public void setmRecipeSteps(ArrayList<RecipeSteps> mRecipeSteps) {
        this.steps = mRecipeSteps;
    }

    public void setmRecipeIngredients(ArrayList<RecipeIngredients> mRecipeIngredients) {
        this.ingredients = mRecipeIngredients;
    }

    //getters
    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<RecipeSteps> getmRecipeSteps() {
        return steps;
    }

    public ArrayList<RecipeIngredients> getmRecipeIngredients() {
        return ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeInt(this.servings);
        parcel.writeString(this.name);
        parcel.writeString(this.image);
        parcel.writeTypedList(this.steps);
        parcel.writeTypedList(this.ingredients);
    }

    protected Recipe(Parcel in){
        this.id = in.readInt();
        this.servings = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        in.createTypedArrayList(RecipeSteps.CREATOR);
        in.createTypedArrayList(RecipeIngredients.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
