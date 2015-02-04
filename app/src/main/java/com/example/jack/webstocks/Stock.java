package com.example.jack.webstocks;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jack.webstocks.adt.WebStock;
import com.example.jack.webstocks.http.StockHttp;
import com.example.jack.webstocks.list.StockAdapter;
import com.example.jack.webstocks.list.StockList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Stock extends ListActivity {

    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        try {
            List<WebStock> values = refreshStocks();

            lv = getListView();
            StockAdapter adapter = new StockAdapter(this,values);
            lv.setAdapter(adapter);


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
        Log.d("Activity2", "Webstocks: " + webStocks.toString());
        return webStocks;
    }
}
