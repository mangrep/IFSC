package in.co.techm.ifsc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.co.techm.ifsc.R;

/**
 * Created by turing on 27/7/16.
 */
public class IFSCSearch extends Fragment implements View.OnClickListener {
    private EditText mIfscInput;
    private Button mSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchy_ifsc, container, false);
        mIfscInput = (EditText) view.findViewById(R.id.ifsc_code);
        mSearch = (Button) view.findViewById(R.id.search_btn);
        mSearch.setOnClickListener(this);
        return view;
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
        
    }
}
