package in.co.techm.ifsc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;

/**
 * Created by turing on 28/4/16.
 */
public class BankDetailsActivity extends Fragment {
    private static final String TAG = "BankDetailsActivity";
    private TextView mBankNameRes;
    private TextView mBankAddressRes;
    private TextView mBankIFSCRes;
    private TextView mBankMICRRes;
    private TextView mBankCityName;
    private TextView mBankStateName;
    private TextView mBankContactNumber;
    private TextView mBranchName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.activity_bank_details);
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            BankDetailsRes bankDetails = bundle.getParcelable(Constants.BANK_DETAILS);
            if (bankDetails == null) {
                getActivity().finish();
            }
            mBankNameRes = (TextView) getView().findViewById(R.id.bank_name);
            mBankAddressRes = (TextView) getView().findViewById(R.id.bank_address);
            mBankIFSCRes = (TextView) getView().findViewById(R.id.bank_ifsc);
            mBankMICRRes = (TextView) getView().findViewById(R.id.bank_micr);
            mBranchName = (TextView) getView().findViewById(R.id.branch_name);
            mBankCityName = (TextView) getView().findViewById(R.id.city_name);
            mBankStateName = (TextView) getView().findViewById(R.id.state_name);
            mBankContactNumber = (TextView) getView().findViewById(R.id.contact_number);

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bank_details, container, false);
        return view;
    }
}
