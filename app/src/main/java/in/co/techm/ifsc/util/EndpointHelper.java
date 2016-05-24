package in.co.techm.ifsc.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.co.techm.ifsc.Constants;

/**
 * Created by turing on 21/5/16.
 */
public class EndpointHelper {
    private static final String TAG = "EndpointHelper";

    public static String getBankListUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.BASE_API_URL);
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BANK_LIST);
        return stringBuilder.toString();
    }

    public static String getBranchListUrl(String bankName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.BASE_API_URL);
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
        stringBuilder.append(Constants.BASE_API_URL);
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BANK_DETAILS);
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(bankName));
        stringBuilder.append("/");
        stringBuilder.append(encodeURIComponent(branchName));
        return stringBuilder.toString();
    }
}