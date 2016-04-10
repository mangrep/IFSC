package in.co.techm.ifsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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
        //get view ref
        mBankNameRes = (TextView) findViewById(R.id.bank_name);
        mBankAddressRes = (TextView) findViewById(R.id.bank_address);
        mBankIFSCRes = (TextView) findViewById(R.id.bank_ifsc);
        mBankMICRRes = (TextView) findViewById(R.id.bank_micr);
        mBankNameReq = (AutoCompleteTextView) findViewById(R.id.auto_complete_bank_name);
        mBankNameReq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mBranchNameAjaxHelper != null) {
                    //update with latest data
                    mBranchNameAjaxHelper.setmUrl(Constants.BASE_API_URL + Constants.API_BRANCH_LIST + "/" + mBankNameReq.getText().toString());
                    BranchNameAsyncTask branchNameAsyncTask = new BranchNameAsyncTask();
                    branchNameAsyncTask.execute(mBranchNameAjaxHelper);
                } else {
                    mBranchNameAjaxHelper = new AjaxHelper();
                    Type type = new TypeToken<BankList>() {
                    }.getType();
                    mBranchNameAjaxHelper.setmUrl(Constants.BASE_API_URL + Constants.API_BRANCH_LIST + "/" + mBankNameReq.getText().toString()); //REST url
                    mBranchNameAjaxHelper.setmReturnObject(type);

                    BranchNameAsyncTask branchNameAsyncTask = new BranchNameAsyncTask();
                    branchNameAsyncTask.execute(mBranchNameAjaxHelper);
                }
            }
        });
        mBankNameReq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //To show not foung message
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    if (!mBankNameReq.isPopupShowing()) {
                        Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        mBranchNameReq = (AutoCompleteTextView) findViewById(R.id.auto_complete_branch_name);
        mGetDetails = (Button) findViewById(R.id.get_bank_details);
        mGetDetails.setOnClickListener(this);
        mReceiver = new NetworkReceiver();

        // Register BroadcastReceiver to track connection changes.
        // IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        // mReceiver = new NetworkReceiver();
        // this.registerReceiver(mReceiver, filter);
        //create ajax helper for bank list
        mBankNameAjaxHelper = new AjaxHelper();
        mBankNameAjaxHelper.setmUrl(Constants.BASE_API_URL + Constants.API_BANK_LIST); //REST url
        Type type = new TypeToken<BankList>() {
        }.getType();
        mBankNameAjaxHelper.setmReturnObject(type);
        //get bank list
        BankNameAsyncTask bankNameAsyncTask = new BankNameAsyncTask();
        bankNameAsyncTask.execute(mBankNameAjaxHelper);

        //Create helper for branch list
        mBranchNameAjaxHelper = new AjaxHelper();
        mBranchNameAjaxHelper.setmUrl(Constants.BASE_API_URL + Constants.API_BRANCH_LIST + "/" + mBankNameReq.getText().toString()); //REST url
        mBranchNameAjaxHelper.setmReturnObject(type);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onclick called");
        switch (v.getId()) {
            case R.id.get_bank_details:
                Log.d(TAG, "get bank details");
                AjaxHelper ajaxHelper = new AjaxHelper();
                ajaxHelper.setmUrl(Constants.BASE_API_URL + "getbank/" + mBankNameReq.getText().toString() + "/" + mBranchNameReq.getText().toString());
                Type type = new TypeToken<BankDetailsRes>() {
                }.getType();
                ajaxHelper.setmReturnObject(type);
                BankDetailsAsyncTask bankDetailsAsyncTask = new BankDetailsAsyncTask();
                bankDetailsAsyncTask.execute(ajaxHelper);
                break;
        }
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

    @Override
    protected void onStop() {
        super.onStop();
//        unregisterReceiver(mReceiver);
    }

    class BankNameAsyncTask extends AsyncTask<AjaxHelper, Void, BankList> {
        private static final String TAG = "BankNameAsyncTask";
        AjaxHelper mAjaxHelper;

        @Override
        protected BankList doInBackground(AjaxHelper... ajaxHelper) {
            mAjaxHelper = ajaxHelper[0];
            return (BankList) mAjaxHelper.ajax();
        }

        @Override
        protected void onPostExecute(BankList bankList) {
            super.onPostExecute(bankList);
            Log.d(TAG, bankList.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_dropdown_item_1line, bankList.getData());
            mBankNameReq.setAdapter(adapter);
        }
    }


    class BranchNameAsyncTask extends AsyncTask<AjaxHelper, Void, BankList> {
        private static final String TAG = "BranchNameAsyncTask";
        AjaxHelper mAjaxHelper;

        @Override
        protected BankList doInBackground(AjaxHelper... ajaxHelper) {
            mAjaxHelper = ajaxHelper[0];
            return (BankList) mAjaxHelper.ajax();
        }

        @Override
        protected void onPostExecute(BankList bankList) {
            super.onPostExecute(bankList);
            Log.d(TAG, bankList.toString());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                    android.R.layout.simple_dropdown_item_1line, bankList.getData());
            mBranchNameReq.setAdapter(adapter);
        }
    }

    class BankDetailsAsyncTask extends AsyncTask<AjaxHelper, Void, BankDetailsRes> {
        private static final String TAG = "BranchNameAsyncTask";
        AjaxHelper mAjaxHelper;

        @Override
        protected BankDetailsRes doInBackground(AjaxHelper... ajaxHelper) {
            mAjaxHelper = ajaxHelper[0];
            return (BankDetailsRes) mAjaxHelper.ajax();
        }

        @Override
        protected void onPostExecute(BankDetailsRes bankDetailsRes) {
            super.onPostExecute(bankDetailsRes);
            if (bankDetailsRes != null) {
                mBankNameRes.setText(bankDetailsRes.getData().getBANK());
                mBankAddressRes.setText(bankDetailsRes.getData().getADDRESS());
                mBankIFSCRes.setText(bankDetailsRes.getData().getIFSC());
                mBankMICRRes.setText(bankDetailsRes.getData().getMICRCODE());
            } else {
                Log.d(TAG,"details not found");
            }

        }
    }
}

