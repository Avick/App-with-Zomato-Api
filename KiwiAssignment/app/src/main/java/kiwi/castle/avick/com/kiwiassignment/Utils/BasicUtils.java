package kiwi.castle.avick.com.kiwiassignment.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by avick on 10/26/16.
 */

public class BasicUtils {




    public static String[] getLastLocationCoordinate(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        String coordinate = pref.getString(Constants.LAST_LOCATION_COORDINATE, null);
        String [] latlon = new String[2];
        if(coordinate != null) {
            latlon[0] = coordinate.split(",")[0];
            latlon[1] = coordinate.split(",")[1];

        } else {
            latlon[0] = "19.0948958";
            latlon[1] = "72.8865248";

        }

        return latlon;

    }

    public static void setLastLocationCoordinate(Context context, String location_corordinate) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constants.LAST_LOCATION_COORDINATE, location_corordinate).commit();
    }

    public static String getLastLocationName(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(Constants.LAST_LOCATION_NAME, null);

    }

    public static void setLastLocationName(Context context, String location_name) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Constants.LAST_LOCATION_NAME, location_name).commit();
    }
}
