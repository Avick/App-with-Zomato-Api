package kiwi.castle.avick.com.kiwiassignment.Network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import kiwi.castle.avick.com.kiwiassignment.R;
import kiwi.castle.avick.com.kiwiassignment.Utils.Constants;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by avick on 10/25/16.
 */

public class NetworkDAO {

    private static NetworkDAO instance;
    private final KiwiServices services;
    private static Context mContext;



    private NetworkDAO(String endpoint, Context mContext) {
        this.mContext = mContext;
        Gson gson = new GsonBuilder().disableHtmlEscaping()
                .create();
        RestAdapter.Builder networkAdapterBuilder = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .setRequestInterceptor(getRequestInterceptor())
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(new OkHttpClient()))
                .setLogLevel(RestAdapter.LogLevel.FULL);
        services = networkAdapterBuilder.build().create(KiwiServices.class);
    }

    private static RequestInterceptor getRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {

//                CommonNetworkHeaders headers = CommonNetworkHeaders.getInstance();
                request.addHeader(NetworkConstants.USER_KEY, Constants.Zomato_API_key);
//                request.addHeader(NetworkConstants.KEY_DEVICE_TYPE, headers.getDeviceType());
//                request.addHeader(NetworkConstants.KEY_APP_VERSION_NAME, headers.getAppVersionName());
//                request.addHeader(NetworkConstants.KEY_DEVICE_ID, headers.getDeviceId());
//                request.addHeader(NetworkConstants.KEY_DEVICE_OS, headers.getDeviceOS());
//                request.addHeader(NetworkConstants.KEY_GMS_REGISTRATION_ID, headers.getGcmRegistrationId());
//                request.addHeader(NetworkConstants.KEY_BOOKING_SOURCE, headers.getBookingSource());
//                request.addHeader(NetworkConstants.KEY_VERTICAL, SyncStateContract.Constants.VERTICAL_SALONS_SPAS);
            }
        };
    }

    public static NetworkDAO getInstance(String endpoint, Context mContext) {
        if (instance == null) {
            instance = new NetworkDAO(endpoint, mContext);
        }
        return instance;
    }


    public void getSearchResult(String lat, String lon, String cuisines, String q) {
        services.getResult(lat, lon, cuisines, "5", q, NetworkRequestListener.getInstance());
    }

    public void getCuisines(String lat, String lon ) {
        services.getCuisines(lat, lon, NetworkRequestListener.getInstance());
    }
}
