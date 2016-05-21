package in.co.techm.ifsc;

/**
 * Created by turing on 9/4/16.
 */
public interface Constants {
    String BASE_API_URL = "http://techm.co.in:3000/api/";

    interface REST_ENDPOINTS {
        String API_BANK_LIST = "listbanks";
        String API_BRANCH_LIST = "bank";
        String API_BANK_DETAILS = "getbank";
    }

    interface ERROR_MESSAGE {
        String SOMETHING_WENT_WRONG = "Something went wrong";
        String UNABLE_TO_LOAD_BANK_LIST = "Unable to load bank list";
    }


}
