package in.co.techm.ifsc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;

/**
 * Created by turing on 28/4/16.
 */
public class BankDetailsActivity extends AppCompatActivity {
    private static final String TAG = "BankDetailsActivity";
    private EditText mBankNameRes;
    private EditText mBankAddressRes;
    private EditText mBankIFSCRes;
    private EditText mBankMICRRes;
    private EditText mBankCityName;
    private EditText mBankStateName;
    private EditText mBankContactNumber;
    private EditText mBranchName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            BankDetailsRes bankDetails = bundle.getParcelable(Constants.BANK_DETAILS);
            if (bankDetails == null) {
                finish();
            }
            mBankNameRes = (EditText) findViewById(R.id.bank_name);
            mBankAddressRes = (EditText) findViewById(R.id.bank_address);
            mBankIFSCRes = (EditText) findViewById(R.id.bank_ifsc);
            mBankMICRRes = (EditText) findViewById(R.id.bank_micr);
            mBranchName = (EditText) findViewById(R.id.branch_name);
            mBankCityName = (EditText) findViewById(R.id.city_name);
            mBankStateName = (EditText) findViewById(R.id.state_name);
            mBankContactNumber = (EditText) findViewById(R.id.contact_number);

            mBankAddressRes.setText(bankDetails.getData().getADDRESS());
            mBankMICRRes.setText(bankDetails.getData().getMICRCODE());
            mBankIFSCRes.setText(bankDetails.getData().getIFSC());
            mBankNameRes.setText(bankDetails.getData().getBANK());
            mBankContactNumber.setText(bankDetails.getData().getCONTACT());
            mBankStateName.setText(bankDetails.getData().getSTATE());
            mBankCityName.setText(bankDetails.getData().getCITY());
            mBranchName.setText(bankDetails.getData().getBRANCH());
            Log.d(TAG, bankDetails.toString());
        }
    }

}
