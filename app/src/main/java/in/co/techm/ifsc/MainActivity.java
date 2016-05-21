package in.co.techm.ifsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.callback.BranchListLoadedListener;
import in.co.techm.ifsc.task.TaskLoadBankList;
import in.co.techm.ifsc.task.TaskLoadBranchList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BankListLoadedListener, BranchListLoadedListener {
    private final String TAG = "MainActivity";
    AutoCompleteTextView mBankNameReq;
    AutoCompleteTextView mBranchNameReq;
    Button mGetDetails;
    Context mContext;
    AjaxHelper mBankNameAjaxHelper;
    AjaxHelper mBranchNameAjaxHelper;
    private NetworkReceiver mReceiver;
    TextView mBankNameRes;
    TextView mBankAddressRes;
    TextView mBankIFSCRes;
    TextView mBankMICRRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
//        mBranchNameAjaxHelper = new AjaxHelper(this);
        //get view ref
        mBankNameRes = (TextView) findViewById(R.id.bank_name);
        mBankAddressRes = (TextView) findViewById(R.id.bank_address);
        mBankIFSCRes = (TextView) findViewById(R.id.bank_ifsc);
        mBankMICRRes = (TextView) findViewById(R.id.bank_micr);
        mBankNameReq = (AutoCompleteTextView) findViewById(R.id.auto_complete_bank_name);
        mBankNameReq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TaskLoadBranchList taskLoadBranchList = new TaskLoadBranchList(MainActivity.this);
                taskLoadBranchList.execute(mBankNameReq.getText().toString());
            }
        });
        mBankNameReq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //To show not found message
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    if (!mBankNameReq.isPopupShowing()) {
                        Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        mBranchNameReq = (AutoCompleteTextView) findViewById(R.id.auto_complete_branch_name);
        mGetDetails = (Button) findViewById(R.id.get_bank_details);
        mGetDetails.setOnClickListener(this);
        mReceiver = new NetworkReceiver();

        //get bank list
        TaskLoadBankList taskLoadBankList = new TaskLoadBankList(this);
        taskLoadBankList.execute();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onclick called");
        switch (v.getId()) {
            case R.id.get_bank_details:
                Log.d(TAG, "get bank details");

//                AjaxHelper ajaxHelper = new AjaxHelper(this);
////                mBranchNameAjaxHelper.setmUrl(Constants.BASE_API_URL + "getbank/" + mBankNameReq.getText().toString() + "/" + mBranchNameReq.getText().toString());
//                Type type = new TypeToken<BankDetailsRes>() {
//                }.getType();
//                mBranchNameAjaxHelper.setmReturnObject(type);
//                BankDetailsAsyncTask bankDetailsAsyncTask = new BankDetailsAsyncTask();
//                bankDetailsAsyncTask.execute(mBranchNameAjaxHelper);
                break;
        }
    }

    @Override
    public void onSuccessBankListLoaded(BankList bankList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_dropdown_item_1line, bankList.getData());
        mBankNameReq.setAdapter(adapter);
    }

    @Override
    public void onFailureBankListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBranchListLoaded(BankList bankList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_dropdown_item_1line, bankList.getData());
        mBranchNameReq.setAdapter(adapter);
    }

    @Override
    public void onFailureBranchListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null) {
                Toast.makeText(context, R.string.msg_internet, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, R.string.msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }


//    class BranchNameAsyncTask extends AsyncTask<AjaxHelper, Void, BankList> {
//        private static final String TAG = "BranchNameAsyncTask";
//        AjaxHelper mAjaxHelper;
//
//        @Override
//        protected BankList doInBackground(AjaxHelper... ajaxHelper) {
//            mAjaxHelper = ajaxHelper[0];
//            return (BankList) mAjaxHelper.ajax();
//        }
//
//        @Override
//        protected void onPostExecute(BankList bankList) {
//            super.onPostExecute(bankList);
//            Log.d(TAG, bankList.toString());
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//                    android.R.layout.simple_dropdown_item_1line, bankList.getData());
//            mBranchNameReq.setAdapter(adapter);
//        }
//    }
//
//    class BankDetailsAsyncTask extends AsyncTask<AjaxHelper, Void, BankDetailsRes> {
//        private static final String TAG = "BranchNameAsyncTask";
//        AjaxHelper mAjaxHelper;
//
//        @Override
//        protected BankDetailsRes doInBackground(AjaxHelper... ajaxHelper) {
//            mAjaxHelper = ajaxHelper[0];
//            return (BankDetailsRes) mAjaxHelper.ajax();
//        }
//
//        @Override
//        protected void onPostExecute(BankDetailsRes bankDetailsRes) {
//            super.onPostExecute(bankDetailsRes);
//            CardView detailsView = (CardView) findViewById(R.id.details_view);
//            if (bankDetailsRes != null) {
//                detailsView.setVisibility(View.VISIBLE);
//                mBankNameRes.setText(bankDetailsRes.getData().getBANK());
//                mBankAddressRes.setText(bankDetailsRes.getData().getADDRESS());
//                mBankIFSCRes.setText(bankDetailsRes.getData().getIFSC());
//                mBankMICRRes.setText(bankDetailsRes.getData().getMICRCODE());
//            } else {
//                detailsView.setVisibility(View.GONE);
//                Toast.makeText(getApplicationContext(), "No details found", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
}

