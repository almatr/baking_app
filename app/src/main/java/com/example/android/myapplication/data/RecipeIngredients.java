package com.example.android.myapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeIngredients implements Parcelable{

    private double quantity;
    private String measure;
    private String ingredient;

    public RecipeIngredients(){}

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.quantity);
        parcel.writeString(this.measure);
        parcel.writeString(this.ingredient);
    }

    protected RecipeIngredients (Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Creator<RecipeIngredients> CREATOR = new Creator<RecipeIngredients>() {
        public RecipeIngredients createFromParcel(Parcel source) {
            return new RecipeIngredients(source);
        }
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };
}
