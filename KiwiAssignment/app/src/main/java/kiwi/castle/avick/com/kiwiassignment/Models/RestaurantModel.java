package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avick on 10/27/16.
 */

public class RestaurantModel extends BaseVO {

    @SerializedName("restaurant")
    @Expose
    Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
