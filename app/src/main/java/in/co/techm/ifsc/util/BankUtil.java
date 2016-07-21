package in.co.techm.ifsc.util;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;

/**
 * Created by turing on 21/5/16.
 */
public class BankUtil {
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

    public static BankDetailsRes getBankDetails(RequestQueue requestQueue, String bankName, String branchName) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBankDetailsUrl(bankName, branchName));
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
}
