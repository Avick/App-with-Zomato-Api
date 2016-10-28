package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by avick on 10/27/16.
 */

public class Cuisine {
    @SerializedName("cuisine_id")
    @Expose
    String id;

    @SerializedName("cuisine_name")
    @Expose
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
