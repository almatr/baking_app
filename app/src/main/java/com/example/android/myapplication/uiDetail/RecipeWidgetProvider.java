package com.example.android.myapplication.uiDetail;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;
import com.example.android.myapplication.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    private static final String LAST_CLICKED_RECIPE = "lastClickedRecipe";
    private static final String CLICKED_RECIPE_NAME = "clickedRecipeName";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int appWidgetId : appWidgetIds) {
                RemoteViews removeView = createRemoteViews(context, appWidgetId);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_listview);
            appWidgetManager.updateAppWidget(appWidgetId, removeView);
        }
    }

    @Override
    public void onEnabled(Context context) { }

    @Override
    public void onDisabled(Context context) { }

    public static RemoteViews createRemoteViews(Context context, int appWidgetId){
        Intent intent = new Intent(context, WidgetRemoteService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        RemoteViews result = new RemoteViews(context.getPackageName(), R.layout.widget_listview);
        result.setRemoteAdapter(R.id.widget_listview, intent);
        SharedPreferences preferences = context.getSharedPreferences(LAST_CLICKED_RECIPE,
                Context.MODE_PRIVATE);
        String recipeName = preferences.getString(CLICKED_RECIPE_NAME, "");
        result.setTextViewText(R.id.widget_recipeName, recipeName);
        return result;
    }

    // Send a brodcast to widgetProvider.
    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, RecipeWidgetProvider.class));
        context.sendBroadcast(intent);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        super.onReceive(context, intent);
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            // refresh all widgets
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, RecipeWidgetProvider.class);
            onUpdate(context, mgr, mgr.getAppWidgetIds(cn));
        }super.onReceive(context,intent);
    }
}

