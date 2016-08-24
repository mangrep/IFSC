package in.co.techm.ifsc.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    private static final String TAG = "TaskLoadBranchList";
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BranchListLoadedListener mBranchListLoadedListener;
    private Context mContext;
    private ProgressDialog mDialog;

    public TaskLoadBranchList(BranchListLoadedListener branchListLoadedListener, Context context) {
        mBranchListLoadedListener = branchListLoadedListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        mDialog.setCancelable(false);
        this.mDialog.setMessage("Loading branch list \n\n Please wait...");
        this.mDialog.show();
    }

    @Override
    protected BankList doInBackground(String... params) {
        //check sqlite if not exists then get from network
        BankList bankList = BankUtil.getBranchListSQLITE(mContext, params[0]);
        if (bankList == null) {
            bankList = BankUtil.getBranchListNW(mRequestQueue, params[0]);
            Log.d(TAG, "got from NW");
            //add to sqlite
            if (bankList != null) {
                BankUtil.addBranchListSQLITE(mContext, params[0], bankList.getData());
            }
        }
        Log.d(TAG, "got from Local");
        return bankList;
    }

    @Override
    protected void onPostExecute(BankList bankList) {
        super.onPostExecute(bankList);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
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
