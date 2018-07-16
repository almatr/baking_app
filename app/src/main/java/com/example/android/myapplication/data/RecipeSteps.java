package com.example.android.myapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeSteps implements Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    public RecipeSteps() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.shortDescription);
        parcel.writeString(this.description);
        parcel.writeString(this.videoURL);
        parcel.writeString(thumbnailURL);
    }

    protected RecipeSteps (Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Creator<RecipeSteps> CREATOR = new Creator<RecipeSteps>() {
        public RecipeSteps createFromParcel(Parcel source) {
            return new RecipeSteps(source);
        }
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };
}
