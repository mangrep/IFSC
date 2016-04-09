package in.co.techm.ifsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import in.co.techm.ifsc.bean.BankList;

public class MainActivity extends AppCompatActivity implements CallBackBankList, View.OnClickListener {
    private final String TAG = "MainActivity";
    AutoCompleteTextView mBankName;
    AutoCompleteTextView mBranchName;
    Button mGetDetails;
    private NetworkReceiver mReceiver;
    NetWorkUtil mNetWorkUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBankName = (AutoCompleteTextView) findViewById(R.id.auto_complete_bank_name);
        mBranchName = (AutoCompleteTextView) findViewById(R.id.auto_complete_branch_name);
        mGetDetails = (Button) findViewById(R.id.get_bank_details);
        mGetDetails.setOnClickListener(this);
        mReceiver = new NetworkReceiver();

        // Register BroadcastReceiver to track connection changes.
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        mReceiver = new NetworkReceiver();
//        this.registerReceiver(mReceiver, filter);

        mNetWorkUtil = new NetWorkUtil();
        mNetWorkUtil.setmContext(this);
        mNetWorkUtil.setmUrl(Constants.BASE_API_URL + Constants.API_BANK_LIST); //REST url
        mNetWorkUtil.setmRequestType(Constants.METHOD_POST);
        Type type = new TypeToken<BankList>() {
        }.getType();
        mNetWorkUtil.setmReturnObject(type);
        mNetWorkUtil.registerCallBack(this);
        mNetWorkUtil.execute();//get bank list
    }

    @Override
    public void onSuccessBankList(BankList bankList) {
        Log.d(TAG, "ON Success");
        Log.d(TAG, bankList.toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, bankList.getData());
        mBankName.setAdapter(adapter);
    }

    @Override
    public void onFailureBankList(String message) {
        Log.d(TAG, "ON Failure");
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onclick called");
//        mNetWorkUtil.cancel(true);
        mNetWorkUtil.execute();
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
}
