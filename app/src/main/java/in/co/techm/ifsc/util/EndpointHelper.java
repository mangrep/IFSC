package in.co.techm.ifsc.util;

import in.co.techm.ifsc.Constants;

/**
 * Created by turing on 21/5/16.
 */
public class EndpointHelper {
    public static String getBankListUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.BASE_API_URL);
        stringBuilder.append(Constants.REST_ENDPOINTS.API_BANK_LIST);
        return stringBuilder.toString();
    }
}
