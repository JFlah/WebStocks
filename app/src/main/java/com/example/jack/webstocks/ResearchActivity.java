package com.example.jack.webstocks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jack.webstocks.adt.WebStock;

import java.io.InputStream;

public class ResearchActivity extends Activity {

    ImageView chart;
    TextView stockInfo;

    String baseUrl = "http://chart.finance.yahoo.com/t?s=";
    String symbol;
    String percentChange;
    double askPrice = 0.0d;
    double bidPrice = 0.0d;
    double dayLow;
    double dayHigh;
    int avgDailyVolume;
    double open;
    double prevClose;
    double bookVal;
    double divShare;
    double epsEstimateCurrentYr;
    double epsEstimateNextYr;
    double yearLow;
    double yearHigh;
    String endUrl = "&lang=en-US&region=US&width=300&height=180";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        Intent intent = getIntent();
        WebStock item = (WebStock) intent.getSerializableExtra("Item");
        symbol = item.getSymbol();
        percentChange = item.getPercentChange();
        askPrice = item.getAskPrice();
        bidPrice = item.getBidPrice();
        dayLow = item.getDayLow();
        dayHigh = item.getDayHigh();
        avgDailyVolume = item.getAvgDailyVolume();
        open = item.getOpen();
        prevClose = item.getPrevClose();
        bookVal = item.getBookVal();
        divShare = item.getDivShare();
        epsEstimateCurrentYr = item.getEpsEstimateCurrentYr();
        epsEstimateNextYr = item.getEpsEstimateNextYr();
        yearLow = item.getYearLow();
        yearHigh = item.getYearHigh();
        chart = (ImageView) findViewById(R.id.chartView);
        stockInfo = (TextView) findViewById(R.id.stockInfoTV);

        url = baseUrl+symbol+endUrl;

        stockInfo.setText("Symbol: " + symbol + "\n" +
                "Percent Change: " + percentChange + "\n" +
                "Ask Price: " + askPrice + "\n" +
                "Bid Price: " + bidPrice + "\n" +
                "Day Low: " + dayLow + "\nDay High: " + dayHigh + "\n" +
                "Avg Daily Volume: " + avgDailyVolume + "\n" +
                "Open Price: " + open + "\n" +
                "Previous Close: " + prevClose + "\nBook Value: " + bookVal + "\n" +
                "Div Share: " + divShare + "\n" +
                "EPS Estimate Next Year: " + epsEstimateNextYr + "\n" +
                "EPS Estimate Current Year: " + epsEstimateCurrentYr + "\n" +
                "Year Low: " + yearLow + "\n" +
                "Year High: " + yearHigh + "\n");

        new DownloadImageTask(chart).execute(url);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
