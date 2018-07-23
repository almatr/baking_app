package com.example.android.myapplication.data;

import android.net.Uri;
import android.os.AsyncTask;
import com.example.android.myapplication.ui.MasterListFragment;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class JsonParse {

    ArrayList<Recipe> recipes;
    MasterListFragment masterListFragment;
    private String RECIPE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    // public constructor is necessary for collections
    public JsonParse(MasterListFragment masterListFragment) {
        recipes = new ArrayList<Recipe>();
        this.masterListFragment = masterListFragment;
    }

    private String urlString = RECIPE_URL;

    //Using Gson as a third party library for parsing
    public static Recipe[] parseJSON(String response) {
        Gson gson = new Gson();
        Recipe[] recipeParse = gson.fromJson(response, Recipe[].class);
        return recipeParse;
    }

    public void getJsonUrl() {
        URL url = buildUrl(urlString);
        new recipeQueryTask().execute(url);
    }

    //Build URL from string RECIPE_URL and return url
    public URL buildUrl(String recipeUrl) {
        Uri builtUri = Uri.parse(recipeUrl).buildUpon().build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //Fetch http response and return its contents
    public String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    //perform AsyncTask
    public class recipeQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            if (urls == null) {
                return null;
            }
            URL searchUrl = urls[0];
            String result = null;
            try {
                result = getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && !result.equals("")) {
                if (recipes != null){
                    recipes.clear();
                }
                recipes = new ArrayList<Recipe>(Arrays.asList(JsonParse.parseJSON(result)));
                masterListFragment.gridCreation(recipes);
            }
        }
    }
}
