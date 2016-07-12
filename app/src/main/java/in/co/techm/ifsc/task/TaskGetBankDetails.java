package in.co.techm.ifsc.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.MyApplication;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.network.VolleySingleton;
import in.co.techm.ifsc.util.BankUtil;

/**
 * Created by turing on 21/5/16.
 */
public class TaskGetBankDetails extends AsyncTask<String, Void, BankDetailsRes> {
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;
    private BankDetailsLoadedListener mBankDetailsLoadedListener;
    private Context mContext;
    private ProgressDialog mDialog;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog = new ProgressDialog(mContext);
        this.mDialog.setMessage("Loading details \n\n Please wait...");
        this.mDialog.show();
    }

    public TaskGetBankDetails(BankDetailsLoadedListener bankDetailsLoadedListener, Context context) {
        this.mBankDetailsLoadedListener = bankDetailsLoadedListener;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
        mContext = context;
    }

    @Override
    protected BankDetailsRes doInBackground(String... params) {
        return BankUtil.getBankDetails(mRequestQueue, params[0], params[1]);
    }

    @Override
    protected void onPostExecute(BankDetailsRes bankDetailsRes) {
        super.onPostExecute(bankDetailsRes);
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (bankDetailsRes == null) {
            mBankDetailsLoadedListener.onFailureBankDetailsLoaded(Constants.ERROR_MESSAGE.UNABLE_TO_LOAD_BANK_LIST);
        } else if ("success".equals(bankDetailsRes.getStatus())) {
            mBankDetailsLoadedListener.onSuccessBankDetailsLoaded(bankDetailsRes);
        } else if ("failure".equals(bankDetailsRes.getStatus())) {
            mBankDetailsLoadedListener.onFailureBankDetailsLoaded(bankDetailsRes.getMessage());
        } else {
            mBankDetailsLoadedListener.onFailureBankDetailsLoaded(Constants.ERROR_MESSAGE.SOMETHING_WENT_WRONG);
        }
    }
}
