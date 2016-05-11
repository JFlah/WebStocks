package com.example.jack.webstocks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jack.webstocks.adt.WebStock;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends Activity {
    private StockDataSource dataSource;
    private List<WebStock> favList;
    Button delAll;
    Button delSelected;
    Button research;
    ListView favLV;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        favLV = (ListView) findViewById(R.id.favLV);
        favLV.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        delAll = (Button) findViewById(R.id.delAllButton);
        delSelected = (Button) findViewById(R.id.delSelectedButton);
        research = (Button) findViewById(R.id.researchButton);
        favList = new ArrayList<>();
        error = (TextView) findViewById(R.id.error);

        dataSource = new StockDataSource(ViewActivity.this);
        dataSource.open();
        favList = dataSource.getAllItems();

        ArrayAdapter<WebStock> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, favList);
        favLV.setAdapter(adapter);

        delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataSource.delete();
                dataSource.create();
                favList = dataSource.getAllItems();
                for (WebStock s : favList)
                    System.out.println(s.toString());
                ArrayAdapter<WebStock> adapter = new ArrayAdapter<>(ViewActivity.this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, favList);
                favLV.setAdapter(adapter);
            }
        });
        delSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int len = favLV.getCount();
                SparseBooleanArray checked = favLV.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)){
                        WebStock item = favList.get(i);
                        dataSource.deleteItem(item);
                    }
                }
                favList = dataSource.getAllItems();
                ArrayAdapter<WebStock> adapter = new ArrayAdapter<>(ViewActivity.this, android.R.layout.simple_list_item_multiple_choice, android.R.id.text1, favList);
                favLV.setAdapter(adapter);
            }
        });
        research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error.setText("");
                int len = favLV.getCount();
                int numChecked = 0;
                WebStock item = null;
                SparseBooleanArray checked = favLV.getCheckedItemPositions();
                for (int i = 0; i < len; i++) {
                    if (checked.get(i)) {
                        numChecked++;
                        item = favList.get(i);
                    }
                }
                if (numChecked < 2 && item != null) {
                    Intent researchIntent = new Intent(ViewActivity.this, ResearchActivity.class);
                    //researchIntent.putExtra("Symbol", item.getSymbol());
                    researchIntent.putExtra("Item", item);
                    startActivity(researchIntent);
                } else {
                    error.setText("Please select a single stock to research");
                }
            }
        });
    }
}
