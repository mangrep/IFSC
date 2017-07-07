package in.co.techm.ifsc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.firebase.analytics.FirebaseAnalytics;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;

/**
 * Created by turing on 28/4/16.
 */
public class BankDetailsActivity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {
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
    private CoordinatorLayout mParentLayout;
    private Toolbar mToolbar;
    private FirebaseAnalytics mFirebaseAnalytics;
    private RewardedVideoAd mAd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mBankDetails = bundle.getParcelable(Constants.BANK_DETAILS);
            if (mBankDetails == null) {
                Toast.makeText(this, R.string.somthing_went_wrong, Toast.LENGTH_LONG).show();
                finish();
            }
            bindResources();
            setClickListner();
            setupToolbar();
            initTextBoxes();

            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupAd();
    }

    private void setupAd() {
        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        mAd.loadAd("ca-app-pub-7365734765830037/3879128708", new AdRequest.Builder().build());
//        mAd.loadAd("ca-app-pub-7365734765830037/3879128708", new AdRequest.Builder().addTestDevice("4FC0443BE7967D5BC8AA4A617F8DF7E8").build());
    }

    private void setClickListner() {
        mBankNameRes.setOnClickListener(this);
        mBankAddressRes.setOnClickListener(this);
        mBankIFSCRes.setOnClickListener(this);
        mBankMICRRes.setOnClickListener(this);
        mBranchName.setOnClickListener(this);
        mBankCityName.setOnClickListener(this);
        mBankStateName.setOnClickListener(this);
        mBankContactNumber.setOnClickListener(this);
    }

    private void bindResources() {
        mBankNameRes = (EditText) findViewById(R.id.bank_name);
        mBankAddressRes = (EditText) findViewById(R.id.bank_address);
        mBankIFSCRes = (EditText) findViewById(R.id.bank_ifsc);
        mBankMICRRes = (EditText) findViewById(R.id.bank_micr);
        mBranchName = (EditText) findViewById(R.id.branch_name);
        mBankCityName = (EditText) findViewById(R.id.city_name);
        mBankStateName = (EditText) findViewById(R.id.state_name);
        mBankContactNumber = (EditText) findViewById(R.id.contact_number);
        mParentLayout = (CoordinatorLayout) findViewById(R.id.bank_details_parent);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
    }


    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.title_bank_details);
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
                copyToClipboard(getString(R.string.bank_name), mBankDetails.getData().getBANK());
                break;
            case R.id.bank_address:
                copyToClipboard(getString(R.string.bank_address), mBankDetails.getData().getADDRESS());
                break;
            case R.id.bank_micr:
                copyToClipboard(getString(R.string.micr), mBankDetails.getData().getMICRCODE());
                break;
            case R.id.bank_ifsc:
                copyToClipboard(getString(R.string.bank_ifsc), mBankDetails.getData().getIFSC());
                break;
            case R.id.branch_name:
                copyToClipboard(getString(R.string.branch_name), mBankDetails.getData().getBRANCH());
                break;
            case R.id.city_name:
                copyToClipboard(getString(R.string.city_name), mBankDetails.getData().getBRANCH());
                break;
            case R.id.state_name:
                copyToClipboard(getString(R.string.state_name), mBankDetails.getData().getSTATE());
                break;
            case R.id.contact_number:
                copyToClipboard(getString(R.string.contact_number), mBankDetails.getData().getCONTACT());
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
    public boolean copyToClipboard(String parent, String text) {
        Bundle bundle = new Bundle();
        bundle.putString("copied_field_name", parent);
        mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.COPY_TO_CILIP_BOARD, bundle);
        try {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext
                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext
                        .getSystemService(mContext.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(mContext.getResources().getString(
                                R.string.copy_clip_message), text);
                clipboard.setPrimaryClip(clip);
                showSnackBar(parent + " " + getString(R.string.text_copied_clip));
            }
            return true;
        } catch (Exception e) {
            showSnackBar(getString(R.string.text_copied_clip_failed) + " " + parent.toLowerCase());
            return false;
        }
    }

    void showSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(mParentLayout, msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    @Override
    public void onResume() {
        mAd.resume(this);
        if (mAd.isLoaded()) {
            mAd.show();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mAd.destroy(this);
        super.onDestroy();
    }

    @Override
    public void onRewarded(RewardItem reward) {
//        Toast.makeText(this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//                reward.getAmount(), Toast.LENGTH_SHORT).show();
        // Reward the user.
    }

    // The following listener methods are optional.
    @Override
    public void onRewardedVideoAdLeftApplication() {
//        Toast.makeText(this, "onRewardedVideoAdLeftApplication",
//                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdClosed() {
//        Toast.makeText(this, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
//        Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdLoaded() {
//        Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
//        Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoStarted() {
//        Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
    }
}
