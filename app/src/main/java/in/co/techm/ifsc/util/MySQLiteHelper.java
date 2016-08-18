package in.co.techm.ifsc.util;

/**
 * Created by turing on 18/8/16.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BANK = "BANK";
    public static final String COLUMN_BRANCH = "BRANCH";
    public static final String COLUMN_IFSC = "IFSC";
    public static final String COLUMN_MICR_CODE = "MICRCODE";
    public static final String COLUMN_CONTACT = "CONTACT";
    public static final String COLUMN_ADDRESS = "ADDRESS";
    public static final String COLUMN_CITY = "CITY";
    public static final String COLUMN_DISTRICT = "DISTRICT";
    public static final String COLUMN_STATE = "STATE";

    public static final String TABLE_NAME = "bank_details";
    private static final String DATABASE_NAME = "ifscdtl";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " +
            COLUMN_ID + " text primary key, " +
            COLUMN_BANK + " text, " +
            COLUMN_BRANCH + " text, " +
            COLUMN_IFSC + " text, " +
            COLUMN_MICR_CODE + " text, " +
            COLUMN_CONTACT + " text, " +
            COLUMN_ADDRESS + " text, " +
            COLUMN_CITY + " text, " +
            COLUMN_DISTRICT + " text, " +
            COLUMN_STATE + " text"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.d(TABLE_NAME, "query:" + DATABASE_CREATE);
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
