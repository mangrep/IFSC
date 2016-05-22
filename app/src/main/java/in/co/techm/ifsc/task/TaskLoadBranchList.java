package in.co.techm.ifsc.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.callback.BranchListLoadedListener;
import in.co.techm.ifsc.network.VolleySingleton;
import in.co.techm.ifsc.util.BankUtil;

/**
 * Created by turing on 21/5/16.
 */
public class TaskLoadBranchList extends AsyncTask<String, Void, BankList> {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BranchListLoadedListener mBranchListLoadedListener;

    public TaskLoadBranchList(BranchListLoadedListener  branchListLoadedListener) {
        mBranchListLoadedListener = branchListLoadedListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected BankList doInBackground(String... params) {
        return BankUtil.getBranchList(mRequestQueue, params[0]);
    }

    @Override
    protected void onPostExecute(BankList bankList) {
        super.onPostExecute(bankList);
        if (bankList == null) {
            mBranchListLoadedListener.onFailureBranchListLoaded(Constants.ERROR_MESSAGE.UNABLE_TO_LOAD_BANK_LIST);
        } else if ("success".equals(bankList.getStatus())) {
            mBranchListLoadedListener.onSuccessBranchListLoaded(bankList);
        } else if ("failure".equals(bankList.getStatus())) {
            mBranchListLoadedListener.onFailureBranchListLoaded(bankList.getMessage());
        } else {
            mBranchListLoadedListener.onFailureBranchListLoaded(Constants.ERROR_MESSAGE.SOMETHING_WENT_WRONG);
        }
    }
}
