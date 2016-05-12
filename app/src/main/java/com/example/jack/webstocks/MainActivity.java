package com.example.jack.webstocks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {

    Button favButton;
    Button mapsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView photoPreview = (ImageView)
                findViewById(R.id.mainPicView);
        photoPreview.setVisibility(View.INVISIBLE);

        // Set photo if exists

        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, "ProfilePhoto.jpg");

        try {
            FileInputStream fis = new FileInputStream(file);
            Bitmap b = BitmapFactory.decodeStream(fis);
            b = Bitmap.createScaledBitmap(b, 400, 400, false);
            photoPreview.setVisibility(View.VISIBLE);
            photoPreview.setImageBitmap(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        favButton = (Button) findViewById(R.id.viewButton);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                startActivity(intent);
            }
        });
        mapsButton = (Button) findViewById(R.id.mapButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void getStocks(View view) {
        Intent intent = new Intent(this, Stock.class);
        startActivity(intent);
    }
}
