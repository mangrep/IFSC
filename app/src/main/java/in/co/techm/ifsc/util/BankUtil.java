package in.co.techm.ifsc.util;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import org.json.JSONObject;

import in.co.techm.ifsc.AjaxHelper;
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
        BankList bankList = gson.fromJson(jsonObject.toString(), BankList.class);
        return bankList;
    }

    public static BankList getBranchList(RequestQueue requestQueue, String branchName) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBranchListUrl(branchName));
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
        BankList bankList = gson.fromJson(jsonObject.toString(), BankList.class);
        return bankList;
    }

    public static BankDetailsRes getBankDetails(RequestQueue requestQueue, String bankName, String branchName) {
        JSONObject jsonObject = AjaxHelper.request(requestQueue, EndpointHelper.getBankDetailsUrl(bankName, branchName));
        if (jsonObject == null) {
            return null;
        }
        Gson gson = new Gson();
        BankDetailsRes bankDetails = gson.fromJson(jsonObject.toString(), BankDetailsRes.class);
        return bankDetails;
    }
}
