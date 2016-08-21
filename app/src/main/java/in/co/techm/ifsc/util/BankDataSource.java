package in.co.techm.ifsc.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.co.techm.ifsc.bean.BankDetails;

public class BankDataSource {

    private static final String TAG = "BankDataSource";
    // Database fields
    private SQLiteDatabase mDatabase;
    private MySQLiteHelper mDBHelper;
    private String[] mAllColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_STATE,
            MySQLiteHelper.COLUMN_DISTRICT,
            MySQLiteHelper.COLUMN_CITY,
            MySQLiteHelper.COLUMN_ADDRESS,
            MySQLiteHelper.COLUMN_BANK,
            MySQLiteHelper.COLUMN_BRANCH,
            MySQLiteHelper.COLUMN_CONTACT,
            MySQLiteHelper.COLUMN_MICR_CODE,
            MySQLiteHelper.COLUMN_IFSC
    };

    public BankDataSource(Context context) {
        mDBHelper = new MySQLiteHelper(context);
    }

    public void open() {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public boolean addBankToDB(BankDetails bankDetails) {
        if (bankDetails != null) {
            Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_NAME, mAllColumns,
                    MySQLiteHelper.COLUMN_ID + "=?", new String[]{bankDetails.get_id()}, null, null, null);
            if (cursor.getCount() == 0) {
                ContentValues values = new ContentValues();

                values.put(MySQLiteHelper.COLUMN_IFSC, bankDetails.getIFSC());
                values.put(MySQLiteHelper.COLUMN_BANK, bankDetails.getBANK());
                values.put(MySQLiteHelper.COLUMN_ADDRESS, bankDetails.getADDRESS());
                values.put(MySQLiteHelper.COLUMN_BRANCH, bankDetails.getBRANCH());
                values.put(MySQLiteHelper.COLUMN_CITY, bankDetails.getCITY());
                values.put(MySQLiteHelper.COLUMN_STATE, bankDetails.getSTATE());
                values.put(MySQLiteHelper.COLUMN_DISTRICT, bankDetails.getDISTRICT());
                values.put(MySQLiteHelper.COLUMN_MICR_CODE, bankDetails.getMICRCODE());
                values.put(MySQLiteHelper.COLUMN_CONTACT, bankDetails.getCONTACT());
                values.put(MySQLiteHelper.COLUMN_ID, bankDetails.get_id());

                long insertId = mDatabase.insert(MySQLiteHelper.TABLE_NAME, null, values);
                if (insertId > 0) {
                    Log.d(TAG, "Added " + bankDetails.getBANK());
                    return true;
                }
            } else {
                Log.d(TAG, "skipping already exists");
            }

        }
        return false;
    }

    public void deleteBankDetails(String id) {
        Log.d(TAG, "Deleting:" + id);
        mDatabase.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
                + " = '" + id + "'", null);
    }

    public List<BankDetails> getAllBankDetails() {
        List<BankDetails> bankList = new ArrayList<BankDetails>();

        Cursor cursor = mDatabase.query(MySQLiteHelper.TABLE_NAME,
                mAllColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            BankDetails comment = cursorToBankBean(cursor);
            bankList.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return bankList;
    }

    private BankDetails cursorToBankBean(Cursor cursor) {
        BankDetails details = new BankDetails();
        details.set_id(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ID)));
        details.setBANK(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_BANK)));
        details.setBRANCH(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_BRANCH)));
        details.setSTATE(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_STATE)));
        details.setMICRCODE(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_MICR_CODE)));
        details.setCONTACT(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_CONTACT)));
        details.setADDRESS(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ADDRESS)));
        details.setCITY(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_CITY)));
        details.setDISTRICT(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_DISTRICT)));
        details.setIFSC(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_IFSC)));
        return details;
    }
}
