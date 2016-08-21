package in.co.techm.ifsc.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import in.co.techm.ifsc.bean.BankDetails;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.bean.UpdatePushReq;

/**
 * Created by turing on 21/5/16.
 */
public class BankUtil {
    private static final String TAG = "BankUtil";

    public static BankList getBankList(RequestQueue requestQueue) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBankListUrl());
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            BankList bankList = gson.fromJson(jsonObject.toString(), BankList.class);
            return bankList;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static BankList getBranchList(RequestQueue requestQueue, String branchName) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBranchListUrl(branchName));
        if (jsonObject == null) {
            return null;
        }
        Log.d("json", jsonObject.toString());
        Gson gson = new Gson();
        try {
            BankList branchList = gson.fromJson(jsonObject.toString(), BankList.class);
            return branchList;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static BankDetailsRes getBankDetails(Context context, RequestQueue requestQueue, String bankName, String branchName) {
        //check persistent storage
        BankDataSource bankDataSource = new BankDataSource(context);
        bankDataSource.open();
        BankDetails detailsFromLocal = bankDataSource.getBankDetailsByBankBranchName(bankName, branchName);
        bankDataSource.close();
        //Does not exist in sqlite. Get from server and store locally
        if (detailsFromLocal == null) {
            JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBankDetailsUrl(bankName, branchName));
            if (jsonObject == null) {
                return null;
            }
            Gson gson = new Gson();
            try {
                BankDetailsRes bankDetails = gson.fromJson(jsonObject.toString(), BankDetailsRes.class);
                //Add to sqlite
                bankDataSource.open();
                bankDataSource.addBankToDB(bankDetails.getData());
                bankDataSource.close();

                return bankDetails;
            } catch (JsonSyntaxException e) {
                return null;
            }
        } else {
            //Create response bean
            BankDetailsRes bankDetailsRes = new BankDetailsRes();
            bankDetailsRes.setData(detailsFromLocal);
            bankDetailsRes.setStatus("success");
            return bankDetailsRes;
        }
    }


    public static BankDetailsRes getBankDetailsByIFSC(RequestQueue requestQueue, String ifscCode) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getIFSCSearchUrl(ifscCode));
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            BankDetailsRes bankDetails = gson.fromJson(jsonObject.toString(), BankDetailsRes.class);
            return bankDetails;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static BankDetailsRes getBankDetailsByMICR(RequestQueue requestQueue, String micrCode) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getMICRSearchUrl(micrCode));
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
        try {
            BankDetailsRes bankDetails = gson.fromJson(jsonObject.toString(), BankDetailsRes.class);
            return bankDetails;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }


    public static boolean pushDeviceDetails(RequestQueue requestQueue, UpdatePushReq updatePushReq) {
        Gson gson = new Gson();
        Type type = new TypeToken<UpdatePushReq>() {
        }.getType();
        String json = gson.toJson(updatePushReq, type);
        try {
            JSONObject jsonRequest = new JSONObject(json);
            JSONObject jsonObject = AjaxHelper.requestPost(requestQueue, EndpointHelper.updatePush(), jsonRequest);
            Log.d(TAG, "response:" + jsonObject);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
