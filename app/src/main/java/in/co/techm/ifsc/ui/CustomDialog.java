package in.co.techm.ifsc.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import in.co.techm.ifsc.R;

/**
 * Created by turing on 26/7/16.
 */
public class CustomDialog extends Dialog {
    private EditText mFilterText;
    private ListView mListView;
    private TextView mTitle;
    private CustomAdapter mCustomAdapter;

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener, String[] list, String title) {
        super(context, cancelable, cancelListener);
        setContentView(R.layout.alert_dialog_layout);
        mFilterText = (EditText) findViewById(R.id.search_txt);
        mFilterText.addTextChangedListener(filterTextWatcher);
        mCustomAdapter = new CustomAdapter(context, new ArrayList<String>(Arrays.asList(list)));
        mCustomAdapter.setmOriginalList(new ArrayList<String>(Arrays.asList(list)));
        mListView = (ListView) findViewById(R.id.list);
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(title);
        mListView.setAdapter(mCustomAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            mCustomAdapter.getFilter().filter(s);
        }
    };

    @Override
    public void onStop() {
        mFilterText.removeTextChangedListener(filterTextWatcher);
    }
}
