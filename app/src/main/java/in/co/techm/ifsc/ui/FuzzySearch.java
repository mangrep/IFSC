package in.co.techm.ifsc.ui;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.adapter.AdapterFuzzySearch;
import in.co.techm.ifsc.adapter.RecyclerItemClickListener;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.bean.FuzzySearchRequest;
import in.co.techm.ifsc.bean.SearchType;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.task.TaskFuzzySearch;

/**
 * Created by livquik on 25/06/17.
 */

public class FuzzySearch extends AppCompatActivity implements BankListLoadedListener {
    private Toolbar mToolbar;
    private Bundle mBundle;
    private SearchType mSearchType;
    private RecyclerView mListView;
    private TextView mDefaultMessage;
    private AdapterFuzzySearch mAdapterFuzzySearch;
    private String mFuzzySearchBankName;
    private static long backPressed;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_lookup);
        mBundle = getIntent().getExtras();
        mSearchType = (SearchType) mBundle.getSerializable(Constants.SEARCH_TYPE);
        mFuzzySearchBankName = mBundle.getString(Constants.FUZZY_SEARCH_BANK_NAME);
        createUIObjects();
        setToolBar();
        setAdapter();
        mListView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(Constants.FUZZY_SEARCH_RESPONSE, mAdapterFuzzySearch.getItem(position));
                        resultIntent.putExtra(Constants.SEARCH_TYPE, mSearchType);
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
        );
    }

    private void setAdapter() {
        mAdapterFuzzySearch = new AdapterFuzzySearch(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(llm);
        mListView.setAdapter(mAdapterFuzzySearch);
    }

    private void setToolBar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    void createUIObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (RecyclerView) findViewById(R.id.list);
        mDefaultMessage = (TextView) findViewById(R.id.default_text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bank_lookup, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
        searchView.requestFocus();

        if (mSearchType == SearchType.BANK) {
            searchView.setQueryHint(getString(R.string.enter_bank_name));
        } else {
            searchView.setQueryHint(getString(R.string.enter_branch_name));
        }
        searchView.setOnQueryTextListener(new CustomQueryListener());
        return true;
    }

    @Override
    public void onSuccessBankListLoaded(BankList bankList) {
        mDefaultMessage.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
        mAdapterFuzzySearch.updateList(bankList);
    }

    @Override
    public void onFailureBankListLoaded(String message) {
        mDefaultMessage.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mDefaultMessage.setText(message);
    }

    private class CustomQueryListener implements SearchView.OnQueryTextListener {
        private int lastSearchLength = 0;

        @Override
        public boolean onQueryTextSubmit(String query) {
            fuzzySearch(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            int strLen = newText.length();
            if (strLen > 2 && strLen > lastSearchLength) {
                fuzzySearch(newText);
            } else {
                lastSearchLength = strLen;
            }
            return false;
        }
    }

    void fuzzySearch(String searchStr) {
        FuzzySearchRequest fuzzySearchRequest = new FuzzySearchRequest();
        if (mSearchType == SearchType.BANK) {
            fuzzySearchRequest.setBankName(searchStr);
        } else {
            fuzzySearchRequest.setBankName(mFuzzySearchBankName);
            fuzzySearchRequest.setBranchName(searchStr);
        }
        TaskFuzzySearch taskFuzzySearch = new TaskFuzzySearch(this, this, fuzzySearchRequest, mSearchType);
        taskFuzzySearch.execute();
    }

    @Override
    public void onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else if (mListView.getVisibility() == View.VISIBLE) {
            Toast.makeText(this, "Please select "+ mSearchType.toString() +" from list \n or \n Press once again to go back", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Please enter valid " + mSearchType.toString() + " name to search \n or \n Press once again to go back", Toast.LENGTH_LONG).show();
        }
        backPressed = System.currentTimeMillis();
    }
}
