package in.co.techm.ifsc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;

/**
 * Created by turing on 28/4/16.
 */
public class BankDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BankDetailsActivity";
    private EditText mBankNameRes;
    private EditText mBankAddressRes;
    private EditText mBankIFSCRes;
    private EditText mBankMICRRes;
    private EditText mBankCityName;
    private EditText mBankStateName;
    private EditText mBankContactNumber;
    private EditText mBranchName;
    private Context mContext;
    private BankDetailsRes mBankDetails;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mBankDetails = bundle.getParcelable(Constants.BANK_DETAILS);
            if (mBankDetails == null) {
                Toast.makeText(this, R.string.somthing_went_wrong, Toast.LENGTH_LONG).show();
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

            mBankNameRes.setOnClickListener(this);
            mBankAddressRes.setOnClickListener(this);
            mBankIFSCRes.setOnClickListener(this);
            mBankMICRRes.setOnClickListener(this);
            mBranchName.setOnClickListener(this);
            mBankCityName.setOnClickListener(this);
            mBankStateName.setOnClickListener(this);
            mBankContactNumber.setOnClickListener(this);

            initTextBoxes();
        }
    }

    private void initTextBoxes() {
        if (isNotNullEmpty(mBankDetails.getData().getADDRESS())) {
            mBankAddressRes.setText(mBankDetails.getData().getADDRESS());
        } else {
            mBankAddressRes.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getMICRCODE())) {
            mBankMICRRes.setText(mBankDetails.getData().getMICRCODE());
        } else {
            mBankMICRRes.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getIFSC())) {
            mBankIFSCRes.setText(mBankDetails.getData().getIFSC());
        } else {
            mBankIFSCRes.setText("Not Available");
        }
        if (isNotNullEmpty(mBankDetails.getData().getBANK())) {
            mBankNameRes.setText(mBankDetails.getData().getBANK());
        } else {
            mBankNameRes.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getCONTACT())) {
            mBankContactNumber.setText(mBankDetails.getData().getCONTACT());
        } else {
            mBankContactNumber.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getSTATE())) {
            mBankStateName.setText(mBankDetails.getData().getSTATE());
        } else {
            mBankStateName.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getCITY())) {
            mBankCityName.setText(mBankDetails.getData().getCITY());
        } else {
            mBankCityName.setText("Not Available");
        }

        if (isNotNullEmpty(mBankDetails.getData().getBRANCH())) {
            mBranchName.setText(mBankDetails.getData().getBRANCH());
        } else {
            mBranchName.setText("Not Available");
        }
    }

    boolean isNotNullEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bank_name:
                copyToClipboard(mBankDetails.getData().getBANK());
                break;
            case R.id.bank_address:
                copyToClipboard(mBankDetails.getData().getADDRESS());
                break;
            case R.id.bank_micr:
                copyToClipboard(mBankDetails.getData().getMICRCODE());
                break;
            case R.id.bank_ifsc:
                copyToClipboard(mBankDetails.getData().getIFSC());
                break;
            case R.id.branch_name:
                copyToClipboard(mBankDetails.getData().getBRANCH());
                break;
            case R.id.city_name:
                copyToClipboard(mBankDetails.getData().getBRANCH());
                break;
            case R.id.state_name:
                copyToClipboard(mBankDetails.getData().getSTATE());
                break;
            case R.id.contact_number:
                copyToClipboard(mBankDetails.getData().getCONTACT());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public boolean copyToClipboard(String text) {
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext
                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(
                                mContext.getResources().getString(
                                        R.string.copy_clip_message), text);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, R.string.text_copied_clip, Toast.LENGTH_SHORT).show();
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(mContext, R.string.text_copied_clip_failed, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
