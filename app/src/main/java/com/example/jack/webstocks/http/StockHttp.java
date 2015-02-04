package com.example.jack.webstocks.http;

import android.os.AsyncTask;
import android.util.Log;

import com.example.jack.webstocks.adt.WebStock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jack on 1/7/2015.
 */
public class StockHttp extends AsyncTask<Void, Void, List<WebStock>> {

    public static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?q=";
    public static final String COLUMNS = "symbol, Change_PercentChange, symbol, Ask, Bid, Change_PercentChange, DaysLow, DaysHigh, AverageDailyVolume, Open, PreviousClose, BookValue, DividendShare, EPSEstimateCurrentYear, EPSEstimateNextYear, YearLow, YearHigh";
    public static final String DATA_SOURCE = "&env=http%3A%2F%2Fdatatables.org%2Falltables.env";

    private List<String> stockSymbols;

    public StockHttp(List<String> stockSymbols) {
        this.stockSymbols = stockSymbols;
    }

    public static JSONObject queryYahoo(List<String> stocks) throws IOException, JSONException {
        String sqlQuery = " SELECT " + COLUMNS + " FROM yahoo.finance.quotes WHERE symbol IN(" + queryString(stocks) + ") ";
        String fullUrlStr = BASE_URL + URLEncoder.encode(sqlQuery, "UTF-8") + DATA_SOURCE + "&format=json";

        URL fullUrl = new URL(fullUrlStr);
        InputStream is = fullUrl.openStream();
//        JSONTokener tok = new JSONTokener(is);
        String tok = convertStreamToString(is);
        JSONObject result = new JSONObject(tok);
        Log.d("SHttp-qY", "Result: " + result.toString());
        return result;
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String queryString(List<String> stocks){
        if (stocks.size() < 1)
            return "";
        StringBuilder sb = new StringBuilder();
        for (String stock : stocks) {
            //This checks if the first char is a paren, if it is, like (GOOG) it strips and returns GOOG
            //  If it isnt, it just returns the stock. This allows it to be used when the list format is just [GOOG,APPL]
            //  Potentially useful for scraping other websites in the future.
            stock = stock.charAt(0)=='(' ? stock.substring(1,stock.length()-1) : stock;
            sb.append("\"" + stock  + "\"");
            sb.append(",");
        }
        return sb.substring(0,sb.toString().length()-1);
    }

    public static List<WebStock> processJSON(JSONObject json) throws JSONException {
        JSONObject query = json.getJSONObject("query"); //Lets ignore it if count is 1.
        JSONObject results = query.getJSONObject("results");
        int count = query.getInt("count"); // That is how we get from JSON back to a Java object. Also getString(String key);
        // if count is 1 or less, just return. Who cares about 1 or 0 result anyway.
        if (count <= 1) {
            return new ArrayList<WebStock>();
        }
        //Count is more than 1 - now what? get the quote
        JSONArray quote = results.getJSONArray("quote");

        //JSONArray is not itearable with forEach.. so for loop, each element is JSONObject in this case.
        //Lets simply print the stock symbol and change_percentchange. Get change as a string since its formatted weird.
        // JSONObject has getInt(key); getDouble(key); getString(key); getBoolean(key) where key is String identifier
        List<WebStock> stockList = new ArrayList<WebStock>();

        for(int i=0; i<quote.length(); i++){ // JSONArrays have getJSONObject(int index) function
            JSONObject stock = quote.getJSONObject(i); // get index not string since dealing with array now good.
            String symbol = stock.getString("symbol");
            String pChange = stock.getString("Change_PercentChange"); //good, copy paste the key from below to avoid spelling errors
            double askPrice = 0.0d; double bidPrice = 0.0d; double dayLow = 0.0d; double dayHigh = 0.0d;
            double open = 0.0d; double prevClose = 0.0d; double bookVal = 0.0d; double divShare = 0.0d;
            double epsEstimateCurrentYr = 0.0d; double epsEstimateNextYr = 0.0d; double yearLow = 0.0d;
            double yearHigh = 0.0d;
            if (!stock.isNull("Ask"))
                askPrice = stock.getDouble("Ask");
            if (!stock.isNull("Bid"))
                bidPrice = stock.getDouble("Bid");
            if (!stock.isNull("DaysLow"))
                dayLow = stock.getDouble("DaysLow");
            if (!stock.isNull("DaysHigh"))
                dayHigh = stock.getDouble("DaysHigh");
            int avgDailyVolume = stock.getInt("AverageDailyVolume");

            if (!stock.isNull("Open"))
                open = stock.getDouble("Open");
            if (!stock.isNull("PreviousClose"))
                prevClose = stock.getDouble("PreviousClose");
            if (!stock.isNull("BookValue"))
                bookVal = stock.getDouble("BookValue");
            if (!stock.isNull("DividendShare"))
                divShare = stock.getDouble("DividendShare");
            if (!stock.isNull("EPSEstimateCurrentYear"))
                epsEstimateCurrentYr = stock.getDouble("EPSEstimateCurrentYear");
            if (!stock.isNull("EPSEstimateNextYear"))
                epsEstimateNextYr = stock.getDouble("EPSEstimateNextYear");
            if (!stock.isNull("YearLow"))
                yearLow = stock.getDouble("YearLow");
            if (!stock.isNull("YearHigh"))
                yearHigh = stock.getDouble("YearHigh");
            WebStock webStock = new WebStock(
                    symbol,
                    pChange,
                    askPrice,
                    bidPrice,
                    dayLow,
                    dayHigh,
                    avgDailyVolume,
                    open,
                    prevClose,
                    bookVal,
                    divShare,
                    epsEstimateCurrentYr,
                    epsEstimateNextYr,
                    yearLow,
                    yearHigh
            );
            Log.d("SH-pj", "Webstock no." + i + " " + webStock.toString());
            stockList.add(webStock);
        }
        return stockList;
    }

    @Override
    protected List<WebStock> doInBackground(Void... params) {
        try {
            JSONObject stockJSON = queryYahoo(this.stockSymbols);
            Log.d("SH-dib","JSON: " + stockJSON.toString());
            return processJSON(stockJSON);
        } catch (IOException io) {
            io.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return new ArrayList<WebStock>();
    }
}
