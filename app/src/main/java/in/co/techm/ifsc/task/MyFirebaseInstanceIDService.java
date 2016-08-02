package in.co.techm.ifsc.task;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.android.volley.RequestQueue;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import in.co.techm.ifsc.bean.UpdatePushReq;
import in.co.techm.ifsc.network.VolleySingleton;
import in.co.techm.ifsc.util.BankUtil;

/**
 * Created by turing on 1/8/16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private static final String TAG = "MyFirebaseInstanceIDService";


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
        String IMEI, IMSI;
        IMEI = m_telephonyManager.getDeviceId();
        IMSI = m_telephonyManager.getSubscriberId();

        UpdatePushReq updatePushReq = new UpdatePushReq();
        updatePushReq.setAPI_LEVEL(Build.VERSION.SDK_INT);
        updatePushReq.setDevice(Build.DEVICE);
        updatePushReq.setModel(Build.MODEL);
        updatePushReq.setProduct(Build.PRODUCT);
        updatePushReq.setIMSI(IMSI);
        updatePushReq.setIMEI(IMEI);
        updatePushReq.setPushId(refreshedToken);
        BankUtil.pushDeviceDetails(mRequestQueue, updatePushReq);
    }

}
