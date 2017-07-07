package in.co.techm.ifsc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.bean.SearchType;

/**
 * Created by turing on 21/5/16.
 */
public class EndpointHelper {
    private static final String TAG = "EndpointHelper";

    public static String getBankListUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BANK_LIST);
        return stringBuilder.toString();
    }

    public static String getBranchListUrl(String bankName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BRANCH_LIST);
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(bankName));
        return stringBuilder.toString();
    }

    public static String encodeURIComponent(String s) {
        String result;
        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = s;
        }
        return result;
    }

    public static String getBankDetailsUrl(String bankName, String branchName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BANK_DETAILS);
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(bankName));
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(branchName));
        return stringBuilder.toString();
    }

    public static String getIFSCSearchUrl(String ifscCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        stringBuilder.append("v1/");
        stringBuilder.append(Constants.REST_ENDPOINTS.API_IFSC_SEARCH);
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(ifscCode));
        return stringBuilder.toString();
    }

    public static String getMICRSearchUrl(String micrCode) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        stringBuilder.append("v1/");
        stringBuilder.append(Constants.REST_ENDPOINTS.API_MICR_SEARCH);
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(micrCode));
        return stringBuilder.toString();
    }

    public static String updatePush() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseUserURL());
        stringBuilder.append(encodeURIComponent(Constants.REST_ENDPOINTS.UPDATE_PUSH));
        return stringBuilder.toString();
    }

    public static String fuzzyBank(SearchType mSearchType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getBaseURL());
        if(mSearchType == SearchType.BANK){
            stringBuilder.append(encodeURIComponent(Constants.REST_ENDPOINTS.FUZZY_BANK));
        }else{
            stringBuilder.append(encodeURIComponent(Constants.REST_ENDPOINTS.FUZZY_BRANCH));
        }
        return stringBuilder.toString();
    }

    public static String getBaseURL() {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.IS_LIVE) {
            stringBuilder.append(Constants.LIVE_CONFIG.BASE_API_URL);
        } else {
            stringBuilder.append(Constants.TEST_CONFIG.BASE_API_URL);
        }
        return stringBuilder.toString();
    }

    public static String getBaseUserURL() {
        StringBuilder stringBuilder = new StringBuilder();
        if (Constants.IS_LIVE) {
            stringBuilder.append(Constants.LIVE_CONFIG.BASE_USER_API_URL);
        } else {
            stringBuilder.append(Constants.TEST_CONFIG.BASE_USER_API_URL);
        }
        return stringBuilder.toString();
    }
}
