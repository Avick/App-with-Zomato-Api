package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by avick on 10/26/16.
 */

public class SearchResultModel {


    @SerializedName("results_found")
    @Expose
    private String resultsFound;
    @SerializedName("results_start")
    @Expose
    private String resultsStart;
    @SerializedName("results_shown")
    @Expose
    private String resultsShown;
    @SerializedName("restaurants")
    @Expose
    private ArrayList<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();

    /**
     * @return The resultsFound
     */
    public String getResultsFound() {
        return resultsFound;
    }

    /**
     * @param resultsFound The results_found
     */
    public void setResultsFound(String resultsFound) {
        this.resultsFound = resultsFound;
    }

    /**
     * @return The resultsStart
     */
    public String getResultsStart() {
        return resultsStart;
    }

    /**
     * @param resultsStart The results_start
     */
    public void setResultsStart(String resultsStart) {
        this.resultsStart = resultsStart;
    }

    /**
     * @return The resultsShown
     */
    public String getResultsShown() {
        return resultsShown;
    }

    /**
     * @param resultsShown The results_shown
     */
    public void setResultsShown(String resultsShown) {
        this.resultsShown = resultsShown;
    }

    /**
     * @return The restaurants
     */
    public ArrayList<RestaurantModel> getRestaurants() {
        return restaurants;
    }

    /**
     * @param restaurants The restaurants
     */
    public void setRestaurants(ArrayList<RestaurantModel> restaurants) {
        this.restaurants = restaurants;
    }

}
