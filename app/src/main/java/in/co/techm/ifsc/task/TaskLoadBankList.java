package in.co.techm.ifsc.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.network.VolleySingleton;
import in.co.techm.ifsc.util.BankUtil;

/**
 * Created by turing on 21/5/16.
 */
public class TaskLoadBankList extends AsyncTask<Void, Void, BankList> {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BankListLoadedListener mBankListLoadedListener;

    public TaskLoadBankList(BankListLoadedListener bankListLoadedListener) {
        mBankListLoadedListener = bankListLoadedListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected BankList doInBackground(Void... params) {
        return BankUtil.getBankList(mRequestQueue);
    }

    @Override
    protected void onPostExecute(BankList bankList) {
        super.onPostExecute(bankList);
        if (bankList == null) {
            mBankListLoadedListener.onFailureBankListLoaded(Constants.ERROR_MESSAGE.UNABLE_TO_LOAD_BANK_LIST);
        } else if ("success".equals(bankList.getStatus())) {
            mBankListLoadedListener.onSuccessBankListLoaded(bankList);
        } else if ("failure".equals(bankList.getStatus())) {
            mBankListLoadedListener.onFailureBankListLoaded(bankList.getMessage());
        } else {
            mBankListLoadedListener.onFailureBankListLoaded(Constants.ERROR_MESSAGE.SOMETHING_WENT_WRONG);
        }
    }
}
