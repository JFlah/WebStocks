package com.example.jack.webstocks.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jack.webstocks.R;
import com.example.jack.webstocks.adt.WebStock;

import java.util.List;

/**
 * Created by Jack on 2/3/2015.
 */
public class StockAdapter extends ArrayAdapter<WebStock> {
    private final Context context;
    private final List<WebStock> values;

    public StockAdapter(Context context, List<WebStock> values) {
        super(context, R.layout.stock_row_layout, android.R.id.text1, values);
        this.context=context;
        this.values=values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stock_row_layout, parent, false);
        WebStock stock = getItem(position);
        TextView stockSymbol = (TextView) rowView.findViewById(R.id.stock_symbol);
        stockSymbol.setText(stock.getSymbol());
        TextView stockPct = (TextView) rowView.findViewById(R.id.stock_pc);
        stockPct.setText(stock.getPercentChange());

        double askPrice = stock.getAskPrice();
        TextView stockAsk = (TextView) rowView.findViewById(R.id.stock_ask);
        stockAsk.setText(askPrice==0.0 ? "Day Low: " + stock.getDayLow() : "Ask: " + askPrice);

        double bidPrice = stock.getBidPrice();
        TextView stockBid = (TextView) rowView.findViewById(R.id.stock_bid);
        stockBid.setText(bidPrice==0.0 ? "Day High: " + stock.getDayHigh() : "Bid: " + bidPrice);

        return rowView;
    }
}
