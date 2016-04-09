package in.co.techm.ifsc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import in.co.techm.ifsc.bean.BankList;

/**
 * Created by turing on 6/4/16.
 */
public class NetWorkUtil extends AsyncTask<Void, Void, Object> {
    private final static String TAG = "NetWorkUtil";
    private Context mContext;
    private String mUrl;
    private String mRequestType;
    private Type mReturnObject;
    private Object mCallBack;

    NetWorkUtil() {
    }

    NetWorkUtil(Context context, String url, String type, Type returnObject) {
        this.mContext = context;
        mUrl = url;
        mRequestType = type;
        mReturnObject = returnObject;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmRequestType() {
        return mRequestType;
    }

    public void setmRequestType(String mRequestType) {
        this.mRequestType = mRequestType;
    }

    public Type getmReturnObject() {
        return mReturnObject;
    }

    public void setmReturnObject(Type mReturnObject) {
        this.mReturnObject = mReturnObject;
    }

    public Object getmCallBack() {
        return mCallBack;
    }

    public void registerCallBack(Object callback) {
        this.mCallBack = callback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        Log.d(TAG, "do in background called");
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

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);
        if (mCallBack instanceof CallBackBankList) {
            ((CallBackBankList) mCallBack).onSuccessBankList((BankList) object);
        } else {
            Log.wtf(TAG, "else condition");
        }

    }
}
