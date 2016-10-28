package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by avick on 10/26/16.
 */

public class CuisineList {

    @SerializedName("cuisines")
    @Expose
    ArrayList<CuisineModel> cuisines;

    public ArrayList<CuisineModel> getCuisines() {
        return cuisines;
    }

    public void setCuisines(ArrayList<CuisineModel> cuisines) {
        this.cuisines = cuisines;
    }
}
