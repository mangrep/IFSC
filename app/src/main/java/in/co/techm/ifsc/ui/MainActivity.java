package in.co.techm.ifsc.ui;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;

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

    private TextView mSelectBank;
    private TextView mSelectBranch;
    private RoundedImageView mAxisBank;
    private RoundedImageView mHdfcBank;
    private RoundedImageView mIcicBank;
    private RoundedImageView mKotakBank;
    private RoundedImageView mYesBank;

    private HashMap<String, String[]> mBankBranch;


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
        mSelectBank = (TextView) findViewById(R.id.select_bank_list);
        mSelectBranch = (TextView) findViewById(R.id.select_branch_list);
        mAxisBank = (RoundedImageView) findViewById(R.id.bank_axis);
        mHdfcBank = (RoundedImageView) findViewById(R.id.bank_hdfc);
        mIcicBank = (RoundedImageView) findViewById(R.id.bank_icici);
        mKotakBank = (RoundedImageView) findViewById(R.id.bank_kotak);
        mYesBank = (RoundedImageView) findViewById(R.id.bank_yes);
        mGetDetails = (AppCompatButton) findViewById(R.id.get_bank_details);
        mSelectBank.setOnClickListener(this);
        mAxisBank.setOnClickListener(this);
        mHdfcBank.setOnClickListener(this);
        mIcicBank.setOnClickListener(this);
        mKotakBank.setOnClickListener(this);
        mYesBank.setOnClickListener(this);
        mGetDetails.setOnClickListener(this);
        mBankBranch = new HashMap<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_bank_details:  //Get bank details
                new TaskGetBankDetails(this, this).execute(mSelectBank.getText().toString(), mSelectBranch.getText().toString());
                break;
            case R.id.select_bank_list:
                showBankPopUp(Constants.BANK_LIST.STORED_BANK_LIST, mSelectBank);
                break;
            case R.id.bank_axis:
                mSelectBank.setText(Constants.BANK_LIST.AXIS_BANK);
                loadBranchList();
                break;
            case R.id.bank_hdfc:
                mSelectBank.setText(Constants.BANK_LIST.HDFC_BANK);
                loadBranchList();
                break;
            case R.id.bank_icici:
                mSelectBank.setText(Constants.BANK_LIST.ICICI_BANK);
                loadBranchList();
                break;
            case R.id.bank_kotak:
                mSelectBank.setText(Constants.BANK_LIST.KOTAK_BANK);
                loadBranchList();
                break;
            case R.id.bank_yes:
                mSelectBank.setText(Constants.BANK_LIST.YES_BANK);
                loadBranchList();
                break;
        }
    }

    void showBankPopUp(final String[] list, final TextView textView) {
        AlertDialog.Builder othersBank = new AlertDialog.Builder(mContext);
        othersBank.setAdapter(new BanksAdapter(mContext, list),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        textView.setText(list[position]);
                        if (textView.getId() == R.id.select_bank_list) {
                            loadBranchList();
                        }
                    }
                });
        othersBank.show();
    }

    void loadBranchList() {
        //if map does not have then load
        if (!mBankBranch.containsKey(mSelectBank.getText().toString())) {
            new TaskLoadBranchList(this, this).execute(mSelectBank.getText().toString());
        } else {
            mSelectBranch.setVisibility(View.VISIBLE);
            showBankPopUp(mBankBranch.get(mSelectBank.getText().toString()), mSelectBranch);
        }
    }

    @Override
    public void onSuccessBankListLoaded(BankList bankList) {

    }

    @Override
    public void onFailureBankListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBranchListLoaded(BankList bankList) {
        mBankBranch.put(mSelectBank.getText().toString(), bankList.getData());
        mSelectBranch.setVisibility(View.VISIBLE);
        showBankPopUp(bankList.getData(), mSelectBranch);
    }

    @Override
    public void onFailureBranchListLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails) {
        Intent intent = new Intent(this, BankDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BANK_DETAILS, bankDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFailureBankDetailsLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
//        CardView detailsView = (CardView) findViewById(R.id.details_view);
//        detailsView.setVisibility(View.GONE);
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
                    TaskLoadBankList taskLoadBankList = new TaskLoadBankList(MainActivity.this, MainActivity.this);
                    taskLoadBankList.execute();
                }
            } else {
                //No internet connection available
                Toast.makeText(context, R.string.msg_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver = null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e + "");
        }
    }


    private class BanksAdapter extends ArrayAdapter<String> {
        String[] mBankList;

        public BanksAdapter(Context context, String[] bankList) {
            super(context, 0, bankList);
            this.mBankList = bankList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bank, parent, false);
            }
            TextView bankName = (TextView) convertView.findViewById(R.id.bankName);
            bankName.setText(getItem(position));
            return convertView;
        }

        @Override
        public String getItem(int position) {
            return mBankList[position];
        }

        @Override
        public int getCount() {
            if (mBankList == null || mBankList.length == 0) {
                return 0;
            } else {
                return mBankList.length;
            }
        }
    }
}

