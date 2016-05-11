package com.example.jack.webstocks;

/**
 * Created by Jack on 5/10/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jack.webstocks.adt.WebStock;

import java.util.ArrayList;

public class StockDataSource {
    public SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    public String[] allColumns = {
            MySQLiteHelper.StockEntry.COLUMN_ID,
            MySQLiteHelper.StockEntry.COLUMN_SYMBOL,
            MySQLiteHelper.StockEntry.COLUMN_PERCENTCHANGE,
            MySQLiteHelper.StockEntry.COLUMN_ASKPRICE,
            MySQLiteHelper.StockEntry.COLUMN_BIDPRICE,
            MySQLiteHelper.StockEntry.COLUMN_DAYLOW,
            MySQLiteHelper.StockEntry.COLUMN_DAYHIGH,
            MySQLiteHelper.StockEntry.COLUMN_AVGDAILYVOLUME,
            MySQLiteHelper.StockEntry.COLUMN_OPEN,
            MySQLiteHelper.StockEntry.COLUMN_PREVCLOSE,
            MySQLiteHelper.StockEntry.COLUMN_BOOKVAL,
            MySQLiteHelper.StockEntry.COLUMN_DIVSHARE,
            MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATECURRENTYR,
            MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATENEXTYR,
            MySQLiteHelper.StockEntry.COLUMN_YEARLOW,
            MySQLiteHelper.StockEntry.COLUMN_YEARHIGH
    };
    public StockDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void delete() {
        database.execSQL("DROP TABLE IF EXISTS " + MySQLiteHelper.StockEntry.TABLE_STOCK);
    }

    public void create() {
        database.execSQL("create table if not exists " + MySQLiteHelper.StockEntry.TABLE_STOCK + " (" +
                MySQLiteHelper.StockEntry.COLUMN_ID + " integer primary key autoincrement, " +
                MySQLiteHelper.StockEntry.COLUMN_SYMBOL  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_PERCENTCHANGE  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_ASKPRICE  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_BIDPRICE  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_DAYLOW  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_DAYHIGH  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_AVGDAILYVOLUME  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_OPEN  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_PREVCLOSE  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_BOOKVAL  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_DIVSHARE  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATECURRENTYR  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATENEXTYR  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_YEARLOW  + " text not null, " +
                MySQLiteHelper.StockEntry.COLUMN_YEARHIGH + " text not null)");
    }

    public long createStock(String symbol, String percentChange, String askPrice, String bidPrice, String dayLow, String dayHigh, String avgDailyVolume,
                            String open, String prevClose, String bookVal, String divShare, String epsEstimateCurrentYr, String epsEstimateNextYr,
                            String yearLow, String yearHigh) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.StockEntry.COLUMN_SYMBOL, symbol);
        values.put(MySQLiteHelper.StockEntry.COLUMN_PERCENTCHANGE, percentChange);
        values.put(MySQLiteHelper.StockEntry.COLUMN_ASKPRICE, askPrice);
        values.put(MySQLiteHelper.StockEntry.COLUMN_BIDPRICE, bidPrice);
        values.put(MySQLiteHelper.StockEntry.COLUMN_DAYLOW, dayLow);
        values.put(MySQLiteHelper.StockEntry.COLUMN_DAYHIGH, dayHigh);
        values.put(MySQLiteHelper.StockEntry.COLUMN_AVGDAILYVOLUME, avgDailyVolume);
        values.put(MySQLiteHelper.StockEntry.COLUMN_OPEN, open);
        values.put(MySQLiteHelper.StockEntry.COLUMN_PREVCLOSE, prevClose);
        values.put(MySQLiteHelper.StockEntry.COLUMN_BOOKVAL, bookVal);
        values.put(MySQLiteHelper.StockEntry.COLUMN_DIVSHARE, divShare);
        values.put(MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATECURRENTYR, epsEstimateCurrentYr);
        values.put(MySQLiteHelper.StockEntry.COLUMN_EPSESTIMATENEXTYR, epsEstimateNextYr);
        values.put(MySQLiteHelper.StockEntry.COLUMN_YEARLOW, yearLow);
        values.put(MySQLiteHelper.StockEntry.COLUMN_YEARHIGH, yearHigh);
        return database.insert(MySQLiteHelper.StockEntry.TABLE_STOCK, null, values);
    }

    public void deleteItem(WebStock item) {
        long id = item.getId();
        //System.out.println("Item deleted with id: " + id);
        database.delete(MySQLiteHelper.StockEntry.TABLE_STOCK, MySQLiteHelper.StockEntry.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<WebStock> getAllItems() {
        ArrayList<WebStock> items = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.StockEntry.TABLE_STOCK,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WebStock detail = cursorToItem(cursor);
            items.add(detail);
            cursor.moveToNext();
        }
        // closing
        cursor.close();
        return items;
    }

    public WebStock cursorToItem(Cursor cursor) {
        long id = cursor.getLong(0);
        String symbol = cursor.getString(1);
        String percentChange = cursor.getString(2);
        double askPrice = Double.parseDouble(cursor.getString(3));
        double bidPrice = Double.parseDouble(cursor.getString(4));
        double dayLow = Double.parseDouble(cursor.getString(5));
        double dayHigh = Double.parseDouble(cursor.getString(6));
        int avgDailyVolume = Integer.parseInt(cursor.getString(7));
        double open = Double.parseDouble(cursor.getString(8));
        double prevClose = Double.parseDouble(cursor.getString(9));
        double bookVal = Double.parseDouble(cursor.getString(10));
        double divShare = Double.parseDouble(cursor.getString(11));
        double epsEstimateCurrentYr = Double.parseDouble(cursor.getString(12));
        double epsEstimateNextYr = Double.parseDouble(cursor.getString(13));
        double yearLow = Double.parseDouble(cursor.getString(14));
        double yearHigh = Double.parseDouble(cursor.getString(15));

        return new WebStock(id, symbol, percentChange, askPrice, bidPrice, dayLow, dayHigh, avgDailyVolume,
                open, prevClose, bookVal, divShare, epsEstimateCurrentYr, epsEstimateNextYr, yearLow, yearHigh);
    }

}
