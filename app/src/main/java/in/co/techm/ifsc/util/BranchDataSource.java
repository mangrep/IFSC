package in.co.techm.ifsc.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by turing on 22/8/16.
 */
public class BranchDataSource {
    private static final String TAG = "BranchDataSource";
    private SQLiteDatabase mDatabase;
    private BranchListSqliteHelper mDBHelper;
    private String[] mAllColumns = {
            BranchListSqliteHelper.COLUMN_ID,
            BranchListSqliteHelper.COLUMN_BANK,
            BranchListSqliteHelper.COLUMN_BRANCH_LIST,
    };

    public BranchDataSource(Context context) {
        mDBHelper = new BranchListSqliteHelper(context);
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public boolean addBranchListToDB(String bankName, String branchList) {
        if (bankName != null && branchList != null) {
            Cursor cursor = null;
            //Check if entry already exists
            try {
                cursor = mDatabase.query(BranchListSqliteHelper.TABLE_NAME, mAllColumns,
                        BranchListSqliteHelper.COLUMN_BANK + " =? ", new String[]{bankName}, null, null, null);
            } catch (SQLiteException e) {
                Log.d(TAG, e + "");
            }
            if (cursor == null || cursor.getCount() == 0) {
                ContentValues values = new ContentValues();
                values.put(BranchListSqliteHelper.COLUMN_BANK, bankName);
                values.put(BranchListSqliteHelper.COLUMN_BRANCH_LIST, branchList);

                long insertId = mDatabase.insert(BranchListSqliteHelper.TABLE_NAME, null, values);
                if (insertId > 0) {
                    Log.d(TAG, "Added branch list :" + bankName);
                    return true;
                }
            } else {
                Log.d(TAG, "skipping branch list already exists");
            }
        }

        return false;
    }

    public Map<String, String> getAllBranchList() {
        Map<String, String> map = new HashMap<>();

        Cursor cursor = mDatabase.query(BranchListSqliteHelper.TABLE_NAME,
                mAllColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String bank = cursor.getString(cursor.getColumnIndex(BranchListSqliteHelper.COLUMN_BANK));
            String branchList = cursor.getString(cursor.getColumnIndex(BranchListSqliteHelper.COLUMN_BRANCH_LIST));
            map.put(bank, branchList);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return map;
    }

    public String getBranchListByBank(String bank) {
        try {
            Cursor cursor = mDatabase.query(BranchListSqliteHelper.TABLE_NAME, mAllColumns,
                    BranchListSqliteHelper.COLUMN_BANK + "=? ", new String[]{bank}, null, null, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                String branchList = cursor.getString(cursor.getColumnIndex(BranchListSqliteHelper.COLUMN_BRANCH_LIST));
                cursor.close();
                return branchList;
            }
        } catch (SQLiteException e) {
            Log.d(TAG, e + "");
        }
        return null;
    }
}
