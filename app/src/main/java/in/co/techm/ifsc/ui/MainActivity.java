package in.co.techm.ifsc.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.MyApplication;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.callback.BranchListLoadedListener;
import in.co.techm.ifsc.task.TaskGetBankDetails;
import in.co.techm.ifsc.task.TaskLoadBankList;
import in.co.techm.ifsc.task.TaskLoadBranchList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BankListLoadedListener, BranchListLoadedListener, BankDetailsLoadedListener {
    private final String TAG = "MainActivity";
    private AutoCompleteTextView mBankNameReq;
    private AutoCompleteTextView mBranchNameReq;
    private Button mGetDetails;
    private Context mContext;
    private TextView mBankNameRes;
    private TextView mBankAddressRes;
    private TextView mBankIFSCRes;
    private TextView mBankMICRRes;
    private boolean mIsBankListLoaded;
    private NetworkReceiver mNetworkReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mContext = this;
        mIsBankListLoaded = false;//Bank list is not yet loaded
        mNetworkReceiver = new NetworkReceiver();
        registerReceiver(mNetworkReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
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
                //Wait till 2 char is typed
                if (s.length() > 2) {
                    if (!mBankNameReq.isPopupShowing()) {
                        Toast.makeText(getApplicationContext(), R.string.no_bank_found, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        mBranchNameReq = (AutoCompleteTextView) findViewById(R.id.auto_complete_branch_name);
        mGetDetails = (Button) findViewById(R.id.get_bank_details);
        mGetDetails.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_bank_details:  //Get bank details
                TaskGetBankDetails taskGetBankDetails = new TaskGetBankDetails(this);
                taskGetBankDetails.execute(mBankNameReq.getText().toString(), mBranchNameReq.getText().toString());
                break;
        }
    }

    @Override
    public void onSuccessBankListLoaded(BankList bankList) {
        mIsBankListLoaded = true;//Bank list is loaded
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_dropdown_item_1line, bankList.getData());
        mBankNameReq.setAdapter(adapter); //set adapter with bank list
    }

    @Override
    public void onFailureBankListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBranchListLoaded(BankList bankList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_dropdown_item_1line, bankList.getData());
        mBranchNameReq.setAdapter(adapter); //set adapter with branch list
    }

    @Override
    public void onFailureBranchListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails) {
        CardView detailsView = (CardView) findViewById(R.id.details_view);
        detailsView.setVisibility(View.VISIBLE);
        mBankNameRes.setText(bankDetails.getData().getBANK());
        mBankAddressRes.setText(bankDetails.getData().getADDRESS());
        mBankIFSCRes.setText(bankDetails.getData().getIFSC());
        mBankMICRRes.setText(bankDetails.getData().getMICRCODE());

        Intent intent = new Intent(this, BankDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BANK_DETAILS, bankDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFailureBankDetailsLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
        CardView detailsView = (CardView) findViewById(R.id.details_view);
        detailsView.setVisibility(View.GONE);
    }

    public class NetworkReceiver extends BroadcastReceiver {
        //Empty arg constructor is needed
        public NetworkReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null) {
                //If bank list is not loaded, load it
                if (!mIsBankListLoaded) {
                    TaskLoadBankList taskLoadBankList = new TaskLoadBankList(MainActivity.this);
                    taskLoadBankList.execute();
                }
            } else {
                //No internet connection available
                Toast.makeText(context, R.string.msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }
}

