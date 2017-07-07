package in.co.techm.ifsc;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;


/**
 * Created by turing on 20/5/16.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MobileAds.initialize(this, "ca-app-pub-7365734765830037~5982668701");
    }
}
