package in.co.techm.ifsc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView mBankName;
    AutoCompleteTextView mBranchName;
    Button mGetDetails;
    private NetworkReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mBankName = (AutoCompleteTextView) findViewById(R.id.auto_complete_bank_name);
        mBranchName = (AutoCompleteTextView) findViewById(R.id.auto_complete_branch_name);
        mGetDetails = (Button) findViewById(R.id.get_bank_details);
        receiver = new NetworkReceiver();

        // Register BroadcastReceiver to track connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkReceiver();
        this.registerReceiver(receiver, filter);

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
}
