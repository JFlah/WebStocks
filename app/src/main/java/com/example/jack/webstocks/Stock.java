package com.example.jack.webstocks;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jack.webstocks.adt.WebStock;
import com.example.jack.webstocks.http.StockHttp;
import com.example.jack.webstocks.list.StockAdapter;
import com.example.jack.webstocks.list.StockList;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class Stock extends ListActivity {

    private ListView lv;
    private StockDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        try {
            List<WebStock> values = refreshStocks();

            dataSource = new StockDataSource(Stock.this);
            dataSource.open();

            if (dataSource.getAllItems().size() == 0) {
                dataSource.create();
            }

            lv = getListView();
            StockAdapter adapter = new StockAdapter(this,values);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    boolean add = true;

                    WebStock selectedStock = (WebStock) lv.getItemAtPosition(position);
                    selectedStock.setId(position);
                    String symbol = selectedStock.getSymbol();
                    String percentChange = selectedStock.getPercentChange();
                    String askPrice = Double.toString(selectedStock.getAskPrice());
                    String bidPrice = Double.toString(selectedStock.getBidPrice());
                    String dayLow = Double.toString(selectedStock.getDayLow());
                    String dayHigh = Double.toString(selectedStock.getDayHigh());
                    String avgDailyVolume = Integer.toString(selectedStock.getAvgDailyVolume());
                    String open = Double.toString(selectedStock.getOpen());
                    String prevClose = Double.toString(selectedStock.getPrevClose());
                    String bookVal = Double.toString(selectedStock.getBookVal());
                    String divShare = Double.toString(selectedStock.getDivShare());
                    String epsEstimateCurrentYr = Double.toString(selectedStock.getEpsEstimateCurrentYr());
                    String epsEstimateNextYr = Double.toString(selectedStock.getEpsEstimateNextYr());
                    String yearLow = Double.toString(selectedStock.getYearLow());
                    String yearHigh = Double.toString(selectedStock.getYearHigh());

                    List<WebStock> testList = dataSource.getAllItems();

                    for (WebStock st : testList) {
                        if (symbol.equals(st.getSymbol())) {
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        long rowId = dataSource.createStock(symbol, percentChange, askPrice, bidPrice,
                                dayLow, dayHigh, avgDailyVolume, open, prevClose, bookVal, divShare,
                                epsEstimateCurrentYr, epsEstimateNextYr, yearLow, yearHigh);
                        Toast.makeText(getApplicationContext(), "Added stock " + rowId + ": " + symbol, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<WebStock> refreshStocks() throws InterruptedException, ExecutionException {
        List<String> stockSymbols = new StockList().execute().get();
        List<WebStock> webStocks = new StockHttp(stockSymbols).execute().get();
        return webStocks;
    }
}
