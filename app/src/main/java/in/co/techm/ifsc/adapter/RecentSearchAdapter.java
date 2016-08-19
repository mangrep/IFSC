package in.co.techm.ifsc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetails;

/**
 * Created by turing on 19/8/16.
 */
public class RecentSearchAdapter extends ArrayAdapter<BankDetails> {
    private List<BankDetails> mBankList;
    private EditText mBankName;
    private EditText mBranchName;
    private EditText mIFSCCode;
    private EditText mMICRCode;
    private EditText mAddress;

    public RecentSearchAdapter(Context context, int resource, List<BankDetails> objects) {
        super(context, resource, objects);
        mBankList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_recent_search, parent, false);
        mBankName = (EditText) rowView.findViewById(R.id.bank_name);
        mBranchName = (EditText) rowView.findViewById(R.id.branch_name);
        mIFSCCode = (EditText) rowView.findViewById(R.id.ifsc_code);
        mMICRCode = (EditText) rowView.findViewById(R.id.micr_code);
        mAddress = (EditText) rowView.findViewById(R.id.address);
        BankDetails bankDetails = getItem(position);
        mBankName.setText(bankDetails.getBANK());
        mBranchName.setText(bankDetails.getBRANCH());
        mIFSCCode.setText(bankDetails.getIFSC());
        mMICRCode.setText(bankDetails.getMICRCODE());
        mAddress.setText(bankDetails.getMICRCODE());
        return rowView;
    }

    @Override
    public int getCount() {
        if (mBankList != null) {
            return mBankList.size();
        } else {
            return 0;
        }
    }
}
