package com.example.jack.webstocks;

/**
 * Created by Jack on 5/10/2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "stocks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + StockEntry.TABLE_STOCK + " (" +
                    StockEntry.COLUMN_ID + " integer primary key autoincrement, " +
                    StockEntry.COLUMN_SYMBOL  + " text not null, " +
                    StockEntry.COLUMN_PERCENTCHANGE + " text not null, " +
                    StockEntry.COLUMN_ASKPRICE + " text not null, " +
                    StockEntry.COLUMN_BIDPRICE + " text not null, " +
                    StockEntry.COLUMN_DAYLOW + " text not null, " +
                    StockEntry.COLUMN_DAYHIGH + " text not null, " +
                    StockEntry.COLUMN_AVGDAILYVOLUME + " text not null, " +
                    StockEntry.COLUMN_OPEN + " text not null, " +
                    StockEntry.COLUMN_PREVCLOSE + " text not null, " +
                    StockEntry.COLUMN_BOOKVAL + " text not null, " +
                    StockEntry.COLUMN_DIVSHARE + " text not null, " +
                    StockEntry.COLUMN_EPSESTIMATECURRENTYR + " text not null, " +
                    StockEntry.COLUMN_EPSESTIMATENEXTYR + " text not null, " +
                    StockEntry.COLUMN_YEARLOW + " text not null, " +
                    StockEntry.COLUMN_YEARHIGH + " text not null)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + StockEntry.TABLE_STOCK);
        onCreate(db);
    }

    public static abstract class StockEntry implements BaseColumns {
        public static final String COLUMN_ID = "_id";
        public static final String TABLE_STOCK = "stock";
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_PERCENTCHANGE = "percentchange";
        public static final String COLUMN_ASKPRICE = "askprice";
        public static final String COLUMN_BIDPRICE = "bidprice";
        public static final String COLUMN_DAYLOW = "daylow";
        public static final String COLUMN_DAYHIGH = "dayhigh";
        public static final String COLUMN_AVGDAILYVOLUME = "avgdailyvolume";
        public static final String COLUMN_OPEN = "open";
        public static final String COLUMN_PREVCLOSE = "prevclose";
        public static final String COLUMN_BOOKVAL = "bookval";
        public static final String COLUMN_DIVSHARE = "divshare";
        public static final String COLUMN_EPSESTIMATECURRENTYR = "epsestimatecurrentyr";
        public static final String COLUMN_EPSESTIMATENEXTYR = "epsestimatenextyr";
        public static final String COLUMN_YEARLOW = "yearlow";
        public static final String COLUMN_YEARHIGH = "yearhigh";
    }

}
