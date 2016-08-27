package in.co.techm.ifsc;

import android.app.Application;
import android.content.Context;


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
    }
}
