package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avick on 10/26/16.
 */

public class CuisineModel extends BaseVO {

    @SerializedName("cuisine")
    @Expose
    Cuisine cuisine;

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }
}
