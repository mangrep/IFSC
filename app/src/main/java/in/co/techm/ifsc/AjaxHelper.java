package in.co.techm.ifsc;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * Created by turing on 6/4/16.
 */
public class AjaxHelper {
    private final static String TAG = "AjaxHelper";
    private String mUrl;
    private Type mReturnObject;

    AjaxHelper() {
    }

    AjaxHelper(Context context, String url, Type returnObject) {
        mUrl = url;
        mReturnObject = returnObject;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public Type getmReturnObject() {
        return mReturnObject;
    }

    public void setmReturnObject(Type mReturnObject) {
        this.mReturnObject = mReturnObject;
    }

    public Object ajax() {
        mUrl = mUrl.replaceAll(" ", "%20");
        Log.d(TAG, "do in background called. " + mUrl);
        int serverResponseStatus = 000;
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(600000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(600000, TimeUnit.MILLISECONDS);
        Request request = new Request.Builder().url(mUrl).build();
        Response responsehttp = null;
        String response = null;
        Object returnObj = null;
        Gson gson = new Gson();
        try {
            responsehttp = client.newCall(request).execute();
            response = responsehttp.body().string();
            serverResponseStatus = responsehttp.code();
            Log.d(TAG, "Response is : " + response + " serverResponseStatus " + serverResponseStatus);
            returnObj = gson.fromJson(response, mReturnObject);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e + "");
        } catch (IOException e) {
            Log.e(TAG, e + "");
        }

        return returnObj;
    }

}
