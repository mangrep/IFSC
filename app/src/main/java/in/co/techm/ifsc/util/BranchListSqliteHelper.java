package in.co.techm.ifsc.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by turing on 22/8/16.
 */
public class BranchListSqliteHelper extends SQLiteOpenHelper {
    private static final String TAG = "BranchListSqliteHelper";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BANK = "bank_name";
    public static final String COLUMN_BRANCH_LIST = "branch_list";

    public static final String TABLE_NAME = "branch_list";
    private static final String DATABASE_NAME = "ifsc_bank_list";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_BANK + " text, " +
            COLUMN_BRANCH_LIST + " text "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, " onCreate query:" + DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public BranchListSqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
