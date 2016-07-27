package in.co.techm.ifsc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.MyApplication;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.task.TaskMICRSearch;

/**
 * Created by turing on 27/7/16.
 */
public class MICRSearch extends Fragment implements View.OnClickListener, BankDetailsLoadedListener {

    private EditText mMicrInput;
    private Button mSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_ifsc_micr, container, false);
        mMicrInput = (EditText) view.findViewById(R.id.micr_ifsc_code);
        TextInputLayout textInputLayout = (TextInputLayout) view.findViewById(R.id.ifsc_holder);
        textInputLayout.setHint(getString(R.string.enter_micr_code));
        mSearch = (Button) view.findViewById(R.id.search_btn);
        mSearch.requestFocus();
        mSearch.setOnClickListener(this);
        mMicrInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        });
        mMicrInput.requestFocus();
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                if (mMicrInput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getContext(), R.string.invalid_micr_msg, Toast.LENGTH_LONG).show();
                } else {
                    callSearch();
                }
                break;
        }
    }

    private void callSearch() {
        String searchString = mMicrInput.getText().toString();
        if (searchString == null || searchString.trim().isEmpty()) {
            Toast.makeText(MyApplication.getAppContext(), "Please enter valid MICR code", Toast.LENGTH_SHORT).show();
        } else {
            new TaskMICRSearch(this, getContext()).execute(searchString);
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
    }
}