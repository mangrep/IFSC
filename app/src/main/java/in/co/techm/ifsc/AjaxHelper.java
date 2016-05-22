package in.co.techm.ifsc;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by turing on 6/4/16.
 */
public class AjaxHelper {
    private static final String TAG = "AjaxHelper";
    //Make netwok call 
    public static JSONObject request(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.e(TAG, e + "");
        } catch (ExecutionException e) {
            Log.e(TAG, e + "");
        } catch (TimeoutException e) {
            Log.e(TAG, e + "");
        }
        return response;
    }
}
