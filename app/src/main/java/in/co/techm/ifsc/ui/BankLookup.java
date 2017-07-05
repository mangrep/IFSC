package in.co.techm.ifsc.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.adapter.AdapterFuzzySearch;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.bean.FuzzySearchRequest;
import in.co.techm.ifsc.bean.SearchType;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.task.TaskFuzzyBankList;

/**
 * Created by livquik on 25/06/17.
 */

public class BankLookup extends AppCompatActivity implements BankListLoadedListener {
    private Toolbar mToolbar;
    private Bundle mBundle;
    private SearchType mSearchType;
    private RecyclerView mListView;
    private AdapterFuzzySearch mAdapterFuzzySearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_lookup);
        mBundle = getIntent().getExtras();
        mSearchType = (SearchType) mBundle.getSerializable(Constants.SEARCH_TYPE);
        createUIObjects();
        setToolBar();
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
        mAdapterFuzzySearch.updateList(bankList);
    }

    @Override
    public void onFailureBankListLoaded(String message) {

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
        fuzzySearchRequest.setBankName(searchStr);
//        fuzzySearchRequest.setBranchName(searchStr);
        TaskFuzzyBankList taskFuzzyBankList = new TaskFuzzyBankList(this, this, fuzzySearchRequest);
        taskFuzzyBankList.execute();
    }
}
