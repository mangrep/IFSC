package in.co.techm.ifsc.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.co.techm.ifsc.bean.BankDetails;

public class BankDataSource {

    private static final String TAG = "BankDataSource";
    // Database fields
    private SQLiteDatabase mDatabase;
    private BankDetailsSqlite mDBHelper;
    private String[] mAllColumns = {
            BankDetailsSqlite.COLUMN_ID,
            BankDetailsSqlite.COLUMN_STATE,
            BankDetailsSqlite.COLUMN_DISTRICT,
            BankDetailsSqlite.COLUMN_CITY,
            BankDetailsSqlite.COLUMN_ADDRESS,
            BankDetailsSqlite.COLUMN_BANK,
            BankDetailsSqlite.COLUMN_BRANCH,
            BankDetailsSqlite.COLUMN_CONTACT,
            BankDetailsSqlite.COLUMN_MICR_CODE,
            BankDetailsSqlite.COLUMN_IFSC
    };

    public BankDataSource(Context context) {
        mDBHelper = new BankDetailsSqlite(context);
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public boolean addBankToDB(BankDetails bankDetails) {
        if (bankDetails != null) {
            try {
                //Check if entry already exists
                Cursor cursor = mDatabase.query(BankDetailsSqlite.TABLE_NAME, mAllColumns,
                        BankDetailsSqlite.COLUMN_ID + " =? or " + BankDetailsSqlite.COLUMN_IFSC + " =?", new String[]{bankDetails.get_id(), bankDetails.getIFSC()}, null, null, null);
                if (cursor.getCount() == 0) {
                    ContentValues values = new ContentValues();

                    values.put(BankDetailsSqlite.COLUMN_IFSC, bankDetails.getIFSC());
                    values.put(BankDetailsSqlite.COLUMN_BANK, bankDetails.getBANK());
                    values.put(BankDetailsSqlite.COLUMN_ADDRESS, bankDetails.getADDRESS());
                    values.put(BankDetailsSqlite.COLUMN_BRANCH, bankDetails.getBRANCH());
                    values.put(BankDetailsSqlite.COLUMN_CITY, bankDetails.getCITY());
                    values.put(BankDetailsSqlite.COLUMN_STATE, bankDetails.getSTATE());
                    values.put(BankDetailsSqlite.COLUMN_DISTRICT, bankDetails.getDISTRICT());
                    values.put(BankDetailsSqlite.COLUMN_MICR_CODE, bankDetails.getMICRCODE());
                    values.put(BankDetailsSqlite.COLUMN_CONTACT, bankDetails.getCONTACT());
                    values.put(BankDetailsSqlite.COLUMN_ID, bankDetails.get_id());

                    long insertId = mDatabase.insert(BankDetailsSqlite.TABLE_NAME, null, values);
                    if (insertId > 0) {
                        Log.d(TAG, "Added " + bankDetails.getBANK());
                        return true;
                    }
                } else {
                    Log.d(TAG, "skipping already exists");
                }
            } catch (SQLiteException e) {
                Log.d(TAG, e + "");
            }
        }
        return false;
    }

    public void deleteBankDetails(String id) {
        Log.d(TAG, "Deleting:" + id);
        mDatabase.delete(BankDetailsSqlite.TABLE_NAME, BankDetailsSqlite.COLUMN_ID
                + " = '" + id + "'", null);
    }

    public List<BankDetails> getAllBankDetails() {
        List<BankDetails> bankList = new ArrayList<BankDetails>();
        try {
            Cursor cursor = mDatabase.query(BankDetailsSqlite.TABLE_NAME,
                    mAllColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BankDetails comment = cursorToBankBean(cursor);
                bankList.add(comment);
                cursor.moveToNext();
            }
            // make sure to close the cursor
            cursor.close();
        } catch (SQLiteException e) {
            Log.d(TAG, e + "");
        }
        return bankList;
    }

    public BankDetails getBankDetailsByBankBranchName(String bankName, String branchName) {
        try {
            Cursor cursor = mDatabase.query(BankDetailsSqlite.TABLE_NAME, mAllColumns,
                    BankDetailsSqlite.COLUMN_BANK + "=? and " + BankDetailsSqlite.COLUMN_BRANCH + "=?", new String[]{bankName, branchName}, null, null, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                BankDetails bankDetails = cursorToBankBean(cursor);
                cursor.close();
                return bankDetails;
            }
        } catch (SQLiteException e) {
            Log.d(TAG, e + "");
        }
        return null;
    }

    public BankDetails getBankDetailsByIFSC(String ifsc) {
        try {
            Cursor cursor = mDatabase.query(BankDetailsSqlite.TABLE_NAME, mAllColumns,
                    BankDetailsSqlite.COLUMN_IFSC + "=? ", new String[]{ifsc}, null, null, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                BankDetails bankDetails = cursorToBankBean(cursor);
                cursor.close();
                return bankDetails;
            }
        } catch (SQLiteException e) {
            Log.d(TAG, e + "");
        }
        return null;
    }

    public BankDetails getBankDetailsByMICR(String micr) {
        try {
            Cursor cursor = mDatabase.query(BankDetailsSqlite.TABLE_NAME, mAllColumns,
                    BankDetailsSqlite.COLUMN_MICR_CODE + "=? ", new String[]{micr}, null, null, null);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                BankDetails bankDetails = cursorToBankBean(cursor);
                cursor.close();
                return bankDetails;
            }
        } catch (SQLiteException e) {
            Log.d(TAG, e + "");
        }
        return null;
    }

    private BankDetails cursorToBankBean(Cursor cursor) {
        BankDetails details = new BankDetails();
        details.set_id(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_ID)));
        details.setBANK(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_BANK)));
        details.setBRANCH(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_BRANCH)));
        details.setSTATE(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_STATE)));
        details.setMICRCODE(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_MICR_CODE)));
        details.setCONTACT(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_CONTACT)));
        details.setADDRESS(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_ADDRESS)));
        details.setCITY(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_CITY)));
        details.setDISTRICT(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_DISTRICT)));
        details.setIFSC(cursor.getString(cursor.getColumnIndex(BankDetailsSqlite.COLUMN_IFSC)));
        return details;
    }
}
