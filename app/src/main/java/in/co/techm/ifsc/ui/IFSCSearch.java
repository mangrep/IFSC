package in.co.techm.ifsc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.MyApplication;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.task.TaskIFSCSearch;

/**
 * Created by turing on 27/7/16.
 */
public class IFSCSearch extends Fragment implements View.OnClickListener, BankDetailsLoadedListener {
    private EditText mIfscInput;
    private Button mSearch;
    private LinearLayout mSearchFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_ifsc_micr, container, false);
        mIfscInput = (EditText) view.findViewById(R.id.micr_ifsc_code);
        mSearchFragment = (LinearLayout) view.findViewById(R.id.ifsc_micr_search_fragment);
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.ifsc_holder);
        textInputLayout.setHint(getString(R.string.enter_ifsc_code));
        mSearch = (Button) view.findViewById(R.id.search_btn);
        mIfscInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        //hide soft keyboard
        mSearchFragment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard();
                return false;
            }
        });
        mIfscInput.requestFocus();
        mSearch.setOnClickListener(this);

        AdView mAdView = (AdView) view.findViewById(R.id.adView_top);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                if (mIfscInput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), R.string.invalid_ifsc_msg, Toast.LENGTH_LONG).show();
                } else {
                    callSearch();
                }
                break;
        }
    }

    private void callSearch() {
        String searchString = mIfscInput.getText().toString();
        if (searchString != null && searchString.length() == Constants.IFSC_LENGTH) {
            new TaskIFSCSearch(this, getContext()).execute(searchString);
        } else {
            Toast.makeText(MyApplication.getAppContext(), "Please enter valid IFSC code", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails) {
        Intent intent = new Intent(getContext(), BankDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BANK_DETAILS, bankDetails);
        intent.putExtras(bundle);
        getContext().startActivity(intent);
    }

    @Override
    public void onFailureBankDetailsLoaded(String message) {
        Toast.makeText(MyApplication.getAppContext(), message, Toast.LENGTH_SHORT).show();
        Snackbar.make(mSearchFragment, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideSoftKeyboard();
    }
}
