package in.co.techm.ifsc;

/**
 * Created by turing on 9/4/16.
 */
public interface Constants {
    boolean IS_LIVE = true;//TODO make it true for live

    interface LIVE_CONFIG {
        String BASE_API_URL = "http://api.techm.co.in/api/";
        String BASE_USER_API_URL = "http://user.techm.co.in/api/";
    }

    interface TEST_CONFIG {
        String BASE_API_URL = "http://api.techm.co.in/api/";
        String BASE_USER_API_URL = "http://192.168.0.8:3000/api/";
    }

    String BANK_DETAILS = "bank_details";
    int IFSC_LENGTH = 11;
    String SEARCH_TYPE = "SEARCH_TYPE";
    String FUZZY_SEARCH_BANK_NAME = "FUZZY_SEARCH_BANK_NAME";
    String FUZZY_SEARCH_RESPONSE = "FUZZY_SEARCH_RESPONSE";
    String STATUS_FAILURE = "failed";
    String STATUS_SUCCESS = "success";

    interface REST_ENDPOINTS {
        String API_BANK_LIST = "listbanks";
        String API_BRANCH_LIST = "listbranches";
        String API_BANK_DETAILS = "getbank";
        String API_IFSC_SEARCH = "ifsc";
        String API_MICR_SEARCH = "micr";
        String UPDATE_PUSH = "updatepush";
        String FUZZY_BANK = "fuzzySearchBank";
        String FUZZY_BRANCH = "fuzzySearchBranch";
    }

    interface ERROR_MESSAGE {
        String SOMETHING_WENT_WRONG = "Something went wrong";
        String UNABLE_TO_LOAD_BANK_LIST = "Unable to load bank list";
        String UNABLE_TO_LOAD_BANK_DETAILS = "Unable to load bank details";
        String UNABLE_TO_LOAD_BRANCH_LIST = "Unable to load branch list";
    }

    interface BANK_LIST {
        String AXIS_BANK = "AXIS BANK";
        String HDFC_BANK = "HDFC BANK";
        String ICICI_BANK = "ICICI BANK LIMITED";
        String KOTAK_BANK = "KOTAK MAHINDRA BANK LIMITED";
        String YES_BANK = "YES BANK";
        String BANK_LIST = "bank_list";
    }

    interface FIREBASE_EVENTS {
        String AXIS_IMAGE_CLICKED = "axis_image_clicked";
        String HDFC_IMAGE_CLICKED = "hdfc_image_clicked";
        String ICIC_IMAGE_CLICKED = "icici_image_clicked";
        String KOTAK_IMAGE_CLICKED = "kotak_image_clicked";
        String YES_IMAGE_CLICKED = "yes_image_clicked";
        String MAIN_GET_DETAILS = "main_get_details";
        String DRAWER_SEARCH_BB = "drawer_search_bank_branch";
        String DRAWER_SEARCH_IFSC = "drawer_search_ifsc";
        String DRAWER_SEARCH_MICR = "drawer_search_micr";
        String DRAWER_RECENT_SEARCH = "drawer_recent_search";
        String SEARCH_BY_IFSC_CLICKED = "search_by_ifsc_clicked";
        String SEARCH_BY_MICR_CLICKED = "search_by_micr_clicked";
        String DELETE_SQLITE_CLICKED = "delete_sqlite_clicked";
        String COPY_TO_CILIP_BOARD = "copy_to_clip_board";
        String DRAWER_SHARE = "drawer_share";
        String PRIVACY_POLICY = "privacy_policy";


    }
}
