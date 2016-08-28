package in.co.techm.ifsc.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.adapter.RecentSearchAdapter;
import in.co.techm.ifsc.bean.BankDetails;
import in.co.techm.ifsc.callback.DeleteSavedEntry;
import in.co.techm.ifsc.callback.ListViewItemClickListener;
import in.co.techm.ifsc.persistence.BankDataSource;

/**
 * Created by turing on 19/8/16.
 */
public class RecentSearchFragment extends Fragment implements DeleteSavedEntry, AdapterView.OnItemClickListener, ListViewItemClickListener {

    private static final String TAG = "RecentSearchFragment";
    private AppCompatTextView mNoSearchMsg;
    private ListView mRecentSearchList;
    private RecentSearchAdapter mAdapter;
    private BankDataSource mPersistentDB;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_search, container, false);
        mNoSearchMsg = (AppCompatTextView) view.findViewById(R.id.no_recent_search_msg);
        mRecentSearchList = (ListView) view.findViewById(R.id.recent_search_item);
        mNoSearchMsg.setVisibility(View.VISIBLE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPersistentDB = new BankDataSource(getContext());
        mPersistentDB.open();
        List<BankDetails> savedSearchList = mPersistentDB.getAllBankDetails();
        mPersistentDB.close();

        if (savedSearchList != null && savedSearchList.size() > 0) {
            Log.d(TAG, savedSearchList.toString() + "");
            mNoSearchMsg.setVisibility(View.GONE);
            mRecentSearchList.setVisibility(View.VISIBLE);
            mAdapter = new RecentSearchAdapter(getContext(), 0, savedSearchList, this, this);
            mRecentSearchList.setAdapter(mAdapter);
            mRecentSearchList.setOnItemClickListener(this);
        } else {
            mNoSearchMsg.setVisibility(View.VISIBLE);
            mRecentSearchList.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDeleteClicked(String id) {
        //Delete from sqlite
        mPersistentDB.open();
        mPersistentDB.deleteBankDetails(id);
        mPersistentDB.close();
        //refresh list
        mAdapter.getFilter().filter(id);
        if (mAdapter.getCount() == 1) {
            mNoSearchMsg.setVisibility(View.VISIBLE);
            mRecentSearchList.setVisibility(View.GONE);
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
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext()
                        .getSystemService(getContext().CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext()
                        .getSystemService(getContext().CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText(getContext().getResources().getString(
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
        Snackbar snackbar = Snackbar.make(mRecentSearchList, msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView txtv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        txtv.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.getItem(position);
    }

    @Override
    public void onClickCopyClipBoard(String name, String value) {
        copyToClipboard(name, value);
    }
}
