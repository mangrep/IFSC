package in.co.techm.ifsc;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;

/**
 * Created by turing on 20/5/16.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, "ca-app-pub-7365734765830037~5982668701");
        mInstance = this;
    }

    public static MyApplication getmInstance() {
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
