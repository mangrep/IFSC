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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;

import in.co.techm.ifsc.Constants;
import in.co.techm.ifsc.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = "MainActivity";
    private Context mContext;
    private NetworkReceiver mNetworkReceiver;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ScrollView mScrollView;
    private NavigationView mNavigationView;
    private HashMap<String, String[]> mBankBranch;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        bindResources();
        setupToolbar();
        createFragment();
        setClickListeners();
        setupNetworkReceiver();

        mBankBranch = new HashMap<>();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
    }

    private void createFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Class fragmentClass = null;
        Fragment fragment = null;
        fragmentClass = SearchByBankBranchFrgment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            getSupportActionBar().setTitle(R.string.title_select_bank_branch);
            fragmentManager.beginTransaction().replace(R.id.main_activity_content_frame, fragment).addToBackStack(fragmentClass.getName()).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupNetworkReceiver() {
        mNetworkReceiver = new NetworkReceiver();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void setClickListeners() {
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void bindResources() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void setupToolbar() {
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_select_bank_branch);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                hideSoftKeyboard(mDrawerLayout);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //clear previous fragment
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {
            case R.id.search_by_bank_branch:
//                if (fragmentManager.getBackStackEntryCount() > 0) {
//                    fragmentManager.popBackStack();
//                }
                fragmentClass = SearchByBankBranchFrgment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.title_select_bank_branch);
                    fragmentManager.beginTransaction().replace(R.id.main_activity_content_frame, fragment).addToBackStack(fragmentClass.getName()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.DRAWER_SEARCH_BB, null);
                break;
            case R.id.search_by_ifsc:
                fragmentClass = IFSCSearch.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.title_search_by_ifsc);
                    mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.DRAWER_SEARCH_IFSC, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.main_activity_content_frame, fragment).addToBackStack(fragmentClass.getName()).commit();
                break;
            case R.id.search_by_micr:
                fragmentClass = MICRSearch.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.title_search_by_micr);
                    mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.DRAWER_SEARCH_MICR, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.main_activity_content_frame, fragment).addToBackStack(fragmentClass.getName()).commit();
                break;
            case R.id.recent_search:
                fragmentClass = RecentSearchFragment.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                    getSupportActionBar().setTitle(R.string.recent_search);
                    mFirebaseAnalytics.logEvent(Constants.FIREBASE_EVENTS.DRAWER_RECENT_SEARCH, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.main_activity_content_frame, fragment).addToBackStack(fragmentClass.getName()).commit();
                break;
        }
        return true;
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
            Snackbar.make(mScrollView, msg, Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Log.e(TAG, "unable to show toast, Invalid msg");
        }
    }

    void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


