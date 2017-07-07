package in.co.techm.ifsc.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.adapter.CustomAdapter;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.bean.SearchType;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.callback.BranchListLoadedListener;
import in.co.techm.ifsc.task.TaskGetBankDetails;
import in.co.techm.ifsc.task.TaskLoadBranchList;

/**
 * Created by turing on 27/8/16.
 */
public class SearchByBankBranchFrgment extends Fragment implements View.OnClickListener, BranchListLoadedListener, BankDetailsLoadedListener {
    private static final String TAG = "SearchByBankBranchFrgment";
    private static final int LOOKUP_ACTIVITY_REQUEST_CODE = 1234;
    private Button mGetDetails;
    private Context mContext;
    private TextView mSelectBank;
    private TextView mSelectBranch;
    private RelativeLayout mBankTextInputLayout;
    private RelativeLayout mBranchTextInputLayout;
    private ScrollView mScrollView;
    private HashMap<String, String[]> mBankBranch;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        bindResources(view);
        setClickListeners();
        setupAd();
        mBankBranch = new HashMap<>();
        mContext = getContext();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        return view;
    }

    void setupAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void bindResources(View view) {
        mSelectBank = (TextView) view.findViewById(R.id.select_bank_list);
        mSelectBranch = (TextView) view.findViewById(R.id.select_branch_list);
        mBankTextInputLayout = (RelativeLayout) view.findViewById(R.id.select_bank_layout);
        mBranchTextInputLayout = (RelativeLayout) view.findViewById(R.id.select_branch_layout);
        mAdView = (AdView) view.findViewById(R.id.adView);
//        mAxisBank = (RoundedImageView) view.findViewById(R.id.bank_axis);
//        mHdfcBank = (RoundedImageView) view.findViewById(R.id.bank_hdfc);
//        mIcicBank = (RoundedImageView) view.findViewById(R.id.bank_icici);
//        mKotakBank = (RoundedImageView) view.findViewById(R.id.bank_kotak);
//        mYesBank = (RoundedImageView) view.findViewById(R.id.bank_yes);
        mGetDetails = (AppCompatButton) view.findViewById(R.id.get_bank_details);
        mScrollView = (ScrollView) view.findViewById(R.id.root_main);
    }

    private void setClickListeners() {
        mSelectBank.setOnClickListener(this);
        mSelectBranch.setOnClickListener(this);
        mGetDetails.setOnClickListener(this);
        mBankTextInputLayout.setOnClickListener(this);
        mBranchTextInputLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_bank_details:  //Get bank details
                if (!mSelectBank.getText().toString().trim().isEmpty() && !mSelectBranch.getText().toString().trim().isEmpty()) {
                    new TaskGetBankDetails(this, mContext).execute(mSelectBank.getText().toString(), mSelectBranch.getText().toString());
                } else {
                    showToast(getString(R.string.bank_or_branch_not_selected));
                }
                mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.MAIN_GET_DETAILS, null);
                break;
            case R.id.select_bank_layout:
            case R.id.select_bank_list:
                loadLookUpActivity(SearchType.BANK);
                break;
            case R.id.select_branch_layout:
            case R.id.select_branch_list:
                if (mSelectBank.getText().toString().trim().isEmpty()) {
                    mSelectBranch.setText("");
                    showToast(getString(R.string.bank_not_seleted));
                } else {
                    loadLookUpActivity(SearchType.BRANCH);
                }
                break;
        }
    }

    void loadLookUpActivity(SearchType searchType) {
        Intent intent = new Intent(mContext, FuzzySearch.class);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.FUZZY_SEARCH_BANK_NAME, mSelectBank.getText().toString());
        bundle.putSerializable(Constants.SEARCH_TYPE, searchType);
        intent.putExtras(bundle);
        startActivityForResult(intent, LOOKUP_ACTIVITY_REQUEST_CODE);
    }

    void showToast(String msg) {
        if (msg != null) {
            Snackbar.make(mScrollView, msg, Snackbar.LENGTH_LONG).show();
//            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "Unable to show toast. Invalid msg");
        }
    }

    void loadBranchList() {
        //if map does not have then load
        if (!mBankBranch.containsKey(mSelectBank.getText().toString())) {
            new TaskLoadBranchList(this, mContext).execute(mSelectBank.getText().toString());
        } else {
            showBranchPopUp(mBankBranch.get(mSelectBank.getText().toString()), mSelectBranch.getText().toString());
        }
    }

    void showBankPopUp(final String[] list) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);

        TextView title = new TextView(mContext);
        title.setText(getString(R.string.select_bank_title));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(mContext.getColor(R.color.colorAccent));
        }
        title.setTextSize(20);
        dialogBuilder.setCustomTitle(title);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText searchText = (EditText) dialogView.findViewById(R.id.search_txt);
        searchText.setHint("Bank name");
        final CustomAdapter customAdapter = new CustomAdapter(mContext, new ArrayList<String>(Arrays.asList(list)));
        customAdapter.setmOriginalList(new ArrayList<String>(Arrays.asList(list)));
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customAdapter.getFilter().filter(s);
            }
        });
        //To hide edit text
        searchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
//                    InputMethodManager imm = (InputMethodManager) v.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    hideSoftKeyboard(v);
                    return true;
                }
                return false;
            }
        });
        ListView listView = (ListView) dialogView.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectBank.setText(customAdapter.getItem(position));
                mSelectBranch.setText("");
                hideSoftKeyboard(view);
                alertDialog.dismiss();
                loadBranchList();
            }
        });

        alertDialog.show();
    }

    void showBranchPopUp(final String[] list, String branchName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);

        TextView title = new TextView(mContext);
        title.setText(getString(R.string.select_branch_title));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(mContext.getColor(R.color.colorAccent));
        }
        title.setTextSize(20);
        dialogBuilder.setCustomTitle(title);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText searchText = (EditText) dialogView.findViewById(R.id.search_txt);
        searchText.setHint("Search Branch Name");
        final CustomAdapter customAdapter = new CustomAdapter(mContext, new ArrayList<String>(Arrays.asList(list)));
        customAdapter.setmOriginalList(new ArrayList<String>(Arrays.asList(list)));
        //To call filter
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customAdapter.getFilter().filter(s);
            }
        });
        //TO hide edit text
        searchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    hideSoftKeyboard(v);
                    return true;
                }
                return false;
            }
        });
//        searchText.setText(branchName);
        ListView listView = (ListView) dialogView.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectBranch.setText(customAdapter.getItem(position));
                hideSoftKeyboard(view);
                alertDialog.dismiss();
            }
        });
//        alertDialog.setTitle(getString(R.string.select_branch_title));
        alertDialog.show();
    }

    @Override
    public void onSuccessBranchListLoaded(BankList bankList) {
        mBankBranch.put(mSelectBank.getText().toString(), bankList.getData());
        mSelectBranch.setVisibility(View.VISIBLE);
//        showBranchPopUp(bankList.getData());
    }

    @Override
    public void onFailureBranchListLoaded(String message) {
        showToast(message);
    }

    void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails) {
        Intent intent = new Intent(mContext, BankDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BANK_DETAILS, bankDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFailureBankDetailsLoaded(String message) {
        showToast(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOOKUP_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getExtras().get(Constants.SEARCH_TYPE) == SearchType.BANK) {
                    mSelectBank.setText(data.getExtras().getString(Constants.FUZZY_SEARCH_RESPONSE));
                    mSelectBranch.setText("");
                } else {
                    mSelectBranch.setText(data.getExtras().getString(Constants.FUZZY_SEARCH_RESPONSE));
                }
            }

        }
    }

}
