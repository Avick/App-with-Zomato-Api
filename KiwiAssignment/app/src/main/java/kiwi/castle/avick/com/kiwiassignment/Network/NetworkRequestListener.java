package kiwi.castle.avick.com.kiwiassignment.Network;

import android.util.Log;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import kiwi.castle.avick.com.kiwiassignment.Models.ErrorVO;
import kiwi.castle.avick.com.kiwiassignment.Utils.Constants;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by avick on 10/26/16.
 */

public class NetworkRequestListener implements Callback {

    private static NetworkRequestListener instance;


//    private NetworkRequestListener() {
//        super();
//    }

    public static NetworkRequestListener getInstance() {
        if (instance == null) {
            instance = new NetworkRequestListener();
        }
        return instance;
    }

    @Override
    public void success(Object o, Response response) {
        EventBus.getDefault().post(o);
    }

    @Override
    public void failure(RetrofitError error) {

        Log.e("RequestFailure", "RetrofitError = " + error.getMessage());
        ErrorVO errorVO = null;
        if (error.getResponse() != null) {
            try {
                errorVO = (ErrorVO) error.getBodyAs(ErrorVO.class);
                errorVO.setStatusCode(error.getResponse().getStatus());
            } catch (Exception e) {
                Log.e("RequestFailure", "No error message found. Response: " + error.getResponse());
            }
        }



        if (errorVO == null) {
            errorVO = new ErrorVO();
            ArrayList<String> errorList = new ArrayList<>(1);
            errorList.add(Constants.GENERIC_ERROR);
            errorVO.setErrors(errorList);
        }

        EventBus.getDefault().post(errorVO);
    }

}
