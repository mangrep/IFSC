package in.co.techm.ifsc.ui;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.DrawerAdapter;
import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankDetailsRes;
import in.co.techm.ifsc.bean.BankList;
import in.co.techm.ifsc.callback.BankDetailsLoadedListener;
import in.co.techm.ifsc.callback.BankListLoadedListener;
import in.co.techm.ifsc.callback.BranchListLoadedListener;
import in.co.techm.ifsc.task.TaskGetBankDetails;
import in.co.techm.ifsc.task.TaskLoadBranchList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BankListLoadedListener, BranchListLoadedListener, BankDetailsLoadedListener, AdapterView.OnItemClickListener {
    private final String TAG = "MainActivity";
    private final int BANK_BRANCH_POSITION = 0;
    private final int IFSC_SEARCH_POSITION = 1;
    private final int MICR_SEARCH_POSITION = 2;
    private Button mGetDetails;
    private Context mContext;
    private NetworkReceiver mNetworkReceiver;
    private TextView mSelectBank;
    private TextView mSelectBranch;
    private RelativeLayout mBankTextInputLayout;
    private RelativeLayout mBranchTextInputLayout;
    private RoundedImageView mAxisBank;
    private RoundedImageView mHdfcBank;
    private RoundedImageView mIcicBank;
    private RoundedImageView mKotakBank;
    private RoundedImageView mYesBank;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ActionBarDrawerToggle mDrawerToggle;
    private HashMap<String, String[]> mBankBranch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mNetworkReceiver = new NetworkReceiver();
        registerReceiver(mNetworkReceiver, new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION));
        mSelectBank = (TextView) findViewById(R.id.select_bank_list);
        mSelectBranch = (TextView) findViewById(R.id.select_branch_list);
        mBankTextInputLayout = (RelativeLayout) findViewById(R.id.select_bank_layout);
        mBranchTextInputLayout = (RelativeLayout) findViewById(R.id.select_branch_layout);
        mAxisBank = (RoundedImageView) findViewById(R.id.bank_axis);
        mHdfcBank = (RoundedImageView) findViewById(R.id.bank_hdfc);
        mIcicBank = (RoundedImageView) findViewById(R.id.bank_icici);
        mKotakBank = (RoundedImageView) findViewById(R.id.bank_kotak);
        mYesBank = (RoundedImageView) findViewById(R.id.bank_yes);
        mGetDetails = (AppCompatButton) findViewById(R.id.get_bank_details);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
        mDrawerListView.setAdapter(new DrawerAdapter(this, 0, getResources().getStringArray(R.array.drawer_menu_name)));
        mSelectBank.setOnClickListener(this);
        mSelectBranch.setOnClickListener(this);
        mAxisBank.setOnClickListener(this);
        mHdfcBank.setOnClickListener(this);
        mIcicBank.setOnClickListener(this);
        mKotakBank.setOnClickListener(this);
        mYesBank.setOnClickListener(this);
        mGetDetails.setOnClickListener(this);
        mBankTextInputLayout.setOnClickListener(this);
        mBranchTextInputLayout.setOnClickListener(this);
        mBankBranch = new HashMap<>();
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        AdView mAdView2 = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest2);
        setupDrawerToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setupDrawerToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_select_bank_branch);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.app_name,  /* "open drawer" description for accessibility */
                R.string.app_name /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
            }

            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.bringToFront();

                // Check if no view has focus: hide keyboard if visible
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerListView.setOnItemClickListener(this);
        mDrawerToggle.syncState();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "clicked" + item.getItemId());
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_bank_details:  //Get bank details
                if (!mSelectBank.getText().toString().trim().isEmpty() && !mSelectBranch.getText().toString().trim().isEmpty()) {
                    new TaskGetBankDetails(this, this).execute(mSelectBank.getText().toString(), mSelectBranch.getText().toString());
                } else {
                    showToast(getString(R.string.bank_or_branch_not_selected));
                }
                break;
            case R.id.select_bank_layout:
            case R.id.select_bank_list:
                showBankPopUp(Constants.BANK_LIST.STORED_BANK_LIST);
                break;
            case R.id.select_branch_layout:
            case R.id.select_branch_list:
                if (mSelectBank.getText().toString().trim().isEmpty()) {
                    mSelectBranch.setText("");
                    showToast(getString(R.string.bank_not_seleted));
                } else {
                    loadBranchList();
                }
                break;
            case R.id.bank_axis:
                mSelectBank.setText(Constants.BANK_LIST.AXIS_BANK);
                mSelectBranch.setText("");
                loadBranchList();
                break;
            case R.id.bank_hdfc:
                mSelectBank.setText(Constants.BANK_LIST.HDFC_BANK);
                mSelectBranch.setText("");
                loadBranchList();
                break;
            case R.id.bank_icici:
                mSelectBank.setText(Constants.BANK_LIST.ICICI_BANK);
                mSelectBranch.setText("");
                loadBranchList();
                break;
            case R.id.bank_kotak:
                mSelectBank.setText(Constants.BANK_LIST.KOTAK_BANK);
                mSelectBranch.setText("");
                loadBranchList();
                break;
            case R.id.bank_yes:
                mSelectBank.setText(Constants.BANK_LIST.YES_BANK);
                mSelectBranch.setText("");
                loadBranchList();
                break;
        }
    }

    void showBankPopUp(final String[] list) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);

        TextView title = new TextView(this);
        title.setText(getString(R.string.select_bank_title));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(getColor(R.color.colorAccent));
        }
        title.setTextSize(20);
        dialogBuilder.setCustomTitle(title);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText searchText = (EditText) dialogView.findViewById(R.id.search_txt);
        searchText.setHint("Bank name");
        final CustomAdapter customAdapter = new CustomAdapter(mContext, new ArrayList<String>(Arrays.asList(list)));
        customAdapter.setmOriginalList(new ArrayList<String>(Arrays.asList(list)));
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customAdapter.getFilter().filter(s);
            }
        });
        //To hide edit text
        searchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
//                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    hideSoftKeyboard(v);
                    return true;
                }
                return false;
            }
        });
        ListView listView = (ListView) dialogView.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectBank.setText(customAdapter.getItem(position));
                mSelectBranch.setText("");
                hideSoftKeyboard(view);
                alertDialog.dismiss();
                loadBranchList();
            }
        });

        alertDialog.show();
    }

    void showBranchPopUp(final String[] list, String branchName) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.alert_dialog_layout, null);

        TextView title = new TextView(this);
        title.setText(getString(R.string.select_branch_title));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextColor(getColor(R.color.colorAccent));
        }
        title.setTextSize(20);
        dialogBuilder.setCustomTitle(title);

        dialogBuilder.setView(dialogView);
        final AlertDialog alertDialog = dialogBuilder.create();
        final EditText searchText = (EditText) dialogView.findViewById(R.id.search_txt);
        searchText.setHint("Search Branch Name");
        final CustomAdapter customAdapter = new CustomAdapter(mContext, new ArrayList<String>(Arrays.asList(list)));
        customAdapter.setmOriginalList(new ArrayList<String>(Arrays.asList(list)));
        //To call filter
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customAdapter.getFilter().filter(s);
            }
        });
        //TO hide edit text
        searchText.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // do something, e.g. set your TextView here via .setText()
                    hideSoftKeyboard(v);
                    return true;
                }
                return false;
            }
        });
        searchText.setText(branchName);
        ListView listView = (ListView) dialogView.findViewById(R.id.list);
        listView.setAdapter(customAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectBranch.setText(customAdapter.getItem(position));
                hideSoftKeyboard(view);
                alertDialog.dismiss();
            }
        });
//        alertDialog.setTitle(getString(R.string.select_branch_title));
        alertDialog.show();
    }

    void loadBranchList() {
        //if map does not have then load
        if (!mBankBranch.containsKey(mSelectBank.getText().toString())) {
            new TaskLoadBranchList(this, this).execute(mSelectBank.getText().toString());
        } else {
            showBranchPopUp(mBankBranch.get(mSelectBank.getText().toString()), mSelectBranch.getText().toString());
        }
    }

    @Override
    public void onSuccessBankListLoaded(BankList bankList) {

    }

    @Override
    public void onFailureBankListLoaded(String message) {
        showToast(message);
    }

    @Override
    public void onSuccessBranchListLoaded(BankList bankList) {
        mBankBranch.put(mSelectBank.getText().toString(), bankList.getData());
        mSelectBranch.setVisibility(View.VISIBLE);
//        showBranchPopUp(bankList.getData());
    }

    @Override
    public void onFailureBranchListLoaded(String message) {
        showToast(message);
    }

    @Override
    public void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails) {
        Intent intent = new Intent(this, BankDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BANK_DETAILS, bankDetails);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onFailureBankDetailsLoaded(String message) {
        showToast(message);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "clicked" + position + "id:" + id);
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //clear previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }

        switch (position) {
            case BANK_BRANCH_POSITION:
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                getSupportActionBar().setTitle(R.string.title_select_bank_branch);
                break;
            case IFSC_SEARCH_POSITION:
                fragmentClass = IFSCSearch.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.title_search_by_ifsc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(fragmentClass.getName()).commit();
                break;
            case MICR_SEARCH_POSITION:
                fragmentClass = MICRSearch.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.title_search_by_micr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(fragmentClass.getName()).commit();
                break;
        }


        // Set action bar title
//        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();


    }

    void noNetworkPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(true);
        builder.setMessage(R.string.no_connection_message);
        builder.setTitle(R.string.no_connection_title);
        builder.setPositiveButton(R.string.wifi, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mContext.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.mobile_internet, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(
                        "com.android.settings",
                        "com.android.settings.Settings$DataUsageSummaryActivity"));
                mContext.startActivity(intent);
                return;
            }
        });
        builder.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                return;
            }
        });

        builder.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
        try {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            unregisterReceiver(mNetworkReceiver);
            mNetworkReceiver = null;
        } catch (IllegalArgumentException e) {
            Log.e(TAG, e + "");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                getSupportActionBar().setTitle(R.string.title_select_bank_branch);
                super.onBackPressed();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public class NetworkReceiver extends BroadcastReceiver {
        //Empty arg constructor is needed
        public NetworkReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connMgr =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo == null) {
                noNetworkPopup();
            }
        }
    }

    void showToast(String msg) {
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "unable to show toast, Invalid msg");
        }
    }

    void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


