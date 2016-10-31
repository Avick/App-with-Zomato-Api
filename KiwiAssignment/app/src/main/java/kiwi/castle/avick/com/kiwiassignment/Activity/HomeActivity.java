package kiwi.castle.avick.com.kiwiassignment.Activity;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import kiwi.castle.avick.com.kiwiassignment.Fragment.SearchFragment;
import kiwi.castle.avick.com.kiwiassignment.R;
import kiwi.castle.avick.com.kiwiassignment.Utils.BasicUtils;

import static android.R.attr.fragment;

/**
 * Created by avick on 10/24/16.
 */

public class HomeActivity extends BaseActivity {


    final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 5;
    final static int LOCATION_PERMISSION_CODE = 1;
    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(checkPermission()) {
            Fragment fragment = SearchFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.lnr_data_container, fragment, SearchFragment.class.getSimpleName()).addToBackStack(null).commit();
            //startLocationpicker();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocationpicker();
                } else {
                    Toast.makeText(this, "app won't wok", Toast.LENGTH_SHORT);
                }

                break;
            case LOCATION_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                BasicUtils.setLastLocationName(this,place.getName().toString());
                BasicUtils.setLastLocationCoordinate(this, place.getLatLng().latitude + "," + place.getLatLng().longitude);
                Fragment fragment = SearchFragment.newInstance();
                getFragmentManager().beginTransaction().add(R.id.lnr_data_container, fragment, SearchFragment.class.getSimpleName()).addToBackStack(null).commit();
            }
        }
    }

    public void init(){

    }

    public boolean checkPermission() {
        if(Build.VERSION.SDK_INT >= 23) {
            int hasInternetPermission = checkSelfPermission(Manifest.permission.INTERNET);
            int hasNetworkStatePermission = checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE);
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int hasReadPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasWritePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();
            if (hasInternetPermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.INTERNET);
            }

            if(hasNetworkStatePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
            }

            if(hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission_group.LOCATION},LOCATION_PERMISSION_CODE);
                listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }

            if(hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission_group.LOCATION},LOCATION_PERMISSION_CODE);
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if(hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission_group.LOCATION},LOCATION_PERMISSION_CODE);
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty())
            {
                requestPermissions(listPermissionsNeeded.toArray
                        (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
                return false;
            }

            return true;

        } else {
            return true;
        }
    }


    void startLocationpicker(){
        try {

            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);

        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

}
