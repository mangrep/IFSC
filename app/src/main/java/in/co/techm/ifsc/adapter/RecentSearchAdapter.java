package in.co.techm.ifsc.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetails;
import in.co.techm.ifsc.callback.DeleteSavedEntry;
import in.co.techm.ifsc.callback.ListViewItemClickListener;

/**
 * Created by turing on 19/8/16.
 */
public class RecentSearchAdapter extends ArrayAdapter<BankDetails> {
    private static final String TAG = "RecentSearchAdapter";
    private List<BankDetails> mBankList;
    private EditText mBankName;
    private EditText mBranchName;
    private EditText mIFSCCode;
    private EditText mMICRCode;
    private ImageView mDeleteEntry;
    private DeleteSavedEntry mDeleteListener;
    private ListViewItemClickListener mListViewItemClickListener;
    private FirebaseAnalytics mFirebaseAnalytics;

    public RecentSearchAdapter(Context context, int resource, List<BankDetails> objects, DeleteSavedEntry deleteListener, ListViewItemClickListener listViewItemClickListener) {
        super(context, resource, objects);
        mBankList = objects;
        mDeleteListener = deleteListener;
        mListViewItemClickListener = listViewItemClickListener;
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
        mDeleteEntry = (ImageView) rowView.findViewById(R.id.delete_entry);

        final BankDetails bankDetails = getItem(position);
        mBankName.setText(bankDetails.getBANK());
        mBranchName.setText(bankDetails.getBRANCH());
        mIFSCCode.setText(bankDetails.getIFSC());
        mMICRCode.setText(bankDetails.getMICRCODE());
        setClickListeners(bankDetails.get_id());
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        return rowView;
    }

    void setClickListeners(final String id) {
        mIFSCCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewItemClickListener.onClickCopyClipBoard(getContext().getString(R.string.bank_ifsc), mIFSCCode.getText().toString());
            }
        });
        mMICRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewItemClickListener.onClickCopyClipBoard(getContext().getString(R.string.micr), mMICRCode.getText().toString());
            }
        });
        mBankName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewItemClickListener.onClickCopyClipBoard(getContext().getString(R.string.bank_name), mBankName.getText().toString());
            }
        });
        mBranchName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewItemClickListener.onClickCopyClipBoard(getContext().getString(R.string.branch_name), mBranchName.getText().toString());
            }
        });
        mDeleteEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.DELETE_SQLITE_CLICKED, null);
                mDeleteListener.onDeleteClicked(id);
            }
        });
    }

    @Override
    public int getCount() {
        if (mBankList != null) {
            return mBankList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults result = new FilterResults();
                if (constraint != null) {
                    for (BankDetails bankDetails : mBankList) {
                        if (constraint.toString().equals(bankDetails.get_id())) {
                            mBankList.remove(bankDetails);
                            break;
                        }
                    }
                }
                //Clone list
                List<BankDetails> cloneList = new ArrayList<>();
                try {
                    for (BankDetails bankDetails : mBankList) {
                        BankDetails bankDetails1 = (BankDetails) bankDetails.clone();
                        cloneList.add(bankDetails1);
                        result.values = cloneList;
                        result.count = cloneList.size();
                    }
                } catch (CloneNotSupportedException e) {
                    Log.d(TAG, e + "");
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results != null && results.values != null) {
                    for (BankDetails bankDetails : (ArrayList<BankDetails>) results.values) {
                        add(bankDetails);
                    }
                }
                notifyDataSetChanged();
            }
        };
    }
}

