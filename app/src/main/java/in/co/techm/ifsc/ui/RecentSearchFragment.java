package in.co.techm.ifsc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetails;
import in.co.techm.ifsc.util.BankDataSource;

/**
 * Created by turing on 19/8/16.
 */
public class RecentSearchFragment extends Fragment {

    private static final String TAG = "RecentSearchFragment";
    private AppCompatTextView mNoSearchMsg;
    private ListView mRecentSearchList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_search, container, false);
        mNoSearchMsg = (AppCompatTextView) view.findViewById(R.id.no_recent_search_msg);
        mRecentSearchList = (ListView) view.findViewById(R.id.recent_search_item);
        mNoSearchMsg.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BankDataSource bankDataSource = new BankDataSource(getContext());
        bankDataSource.open();
        List<BankDetails> savedSearchList = bankDataSource.getAllBankDetails();
        bankDataSource.close();
        if (savedSearchList != null && savedSearchList.size() > 0) {
            Log.d(TAG, savedSearchList.toString() + "");
            mNoSearchMsg.setVisibility(View.GONE);
        } else {
            mNoSearchMsg.setVisibility(View.VISIBLE);
        }
    }
}
