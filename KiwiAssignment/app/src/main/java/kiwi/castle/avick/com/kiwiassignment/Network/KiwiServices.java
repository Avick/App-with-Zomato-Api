package kiwi.castle.avick.com.kiwiassignment.Network;

import kiwi.castle.avick.com.kiwiassignment.Models.CuisineList;
import kiwi.castle.avick.com.kiwiassignment.Models.SearchResultModel;
import kiwi.castle.avick.com.kiwiassignment.Models.ZomatoDataModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by avick on 10/25/16.
 */

public interface KiwiServices {


    String GET_CUISINES = "/cuisines";
    String SEARCH = "/search";


    @GET(GET_CUISINES)
    void getCuisines(@Query("lat") String lat, @Query("lon") String lon, Callback<CuisineList> callback);

    @GET(SEARCH)
    void getResult(@Query("lat") String lat, @Query("lon") String lon, @Query("cuisines") String cuisines, @Query("count") String count, @Query("q") String q, Callback<SearchResultModel> callback);




    //void getResults();
}
