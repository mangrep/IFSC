package in.co.techm.ifsc.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.bean.FuzzySearchRequest;
import in.co.techm.ifsc.bean.SearchType;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.network.VolleySingleton;
import in.co.techm.ifsc.util.BankUtil;

/**
 * Created by turing on 21/5/16.
 */
public class TaskFuzzySearch extends AsyncTask<Void, Void, BankList> {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BankListLoadedListener mBankListLoadedListener;
    private Context mContext;
    private FuzzySearchRequest mFuzzySearchRequest;
    private SearchType mSearchType;

    public TaskFuzzySearch(BankListLoadedListener bankListLoadedListener, Context context, FuzzySearchRequest fuzzySearchRequest, SearchType searchType) {
        mBankListLoadedListener = bankListLoadedListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        mContext = context;
        mFuzzySearchRequest = fuzzySearchRequest;
        mSearchType = searchType;
    }

    @Override
    protected BankList doInBackground(Void... params) {
        return BankUtil.fuzzySearch(mRequestQueue, mFuzzySearchRequest, mSearchType);
    }

    @Override
    protected void onPostExecute(BankList bankList) {
        super.onPostExecute(bankList);
        if (bankList == null) {
            mBankListLoadedListener.onFailureBankListLoaded(Constants.ERROR_MESSAGE.UNABLE_TO_LOAD_BANK_LIST);
        } else if (Constants.STATUS_SUCCESS.equals(bankList.getStatus())) {
            mBankListLoadedListener.onSuccessBankListLoaded(bankList);
        } else if (Constants.STATUS_FAILURE.equals(bankList.getStatus())) {
            mBankListLoadedListener.onFailureBankListLoaded(bankList.getMessage());
        } else {
            mBankListLoadedListener.onFailureBankListLoaded(Constants.ERROR_MESSAGE.SOMETHING_WENT_WRONG);
        }
    }
}
