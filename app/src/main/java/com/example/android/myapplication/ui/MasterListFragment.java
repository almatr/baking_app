package com.example.android.myapplication.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.myapplication.R;
import com.example.android.myapplication.data.JsonParse;
import com.example.android.myapplication.data.Recipe;

import java.util.ArrayList;

public class MasterListFragment extends Fragment {

    GridView gridView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    // Mandatory empty constructor
    public MasterListFragment() {
    }

    // Inflates the GridView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list, container,
                false);

        // Get a reference to the GridView in the fragment_master_list xml layout file
        gridView = (GridView) rootView.findViewById(R.id.images_grid_view);
        JsonParse jsonParse = new JsonParse(this);
        jsonParse.getJsonUrl();

        // Return the root view
        return rootView;
    }

    public void gridCreation(ArrayList<Recipe> recipes) {
        // Create the adapter
        // This adapter takes in the context and an ArrayList of ALL the resources to display
        MasterListAdapter mAdapter = new MasterListAdapter(getContext(),recipes);
        // Set the adapter on the GridView
        gridView.setAdapter(mAdapter);
    }
}