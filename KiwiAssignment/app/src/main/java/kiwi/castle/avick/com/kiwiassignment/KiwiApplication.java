package kiwi.castle.avick.com.kiwiassignment;

import android.app.Application;
import io.realm.Realm;

/**
 * Created by avick on 10/28/16.
 */

public class KiwiApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}