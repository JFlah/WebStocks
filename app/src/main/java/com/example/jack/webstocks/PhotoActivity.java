package com.example.jack.webstocks;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
public class PhotoActivity extends Activity {

    //CREATE A REQUEST CODE TO REQUEST A PHOTOGRAPH
    private static final int
            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    //OBJECTS USED BY THE APP
    private Bitmap photoCaptured;
    private Button cameraButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        //REFERENCE THE APPLICATION BUTTONS AND THE PROGRESS BAR
        cameraButton = (Button) findViewById(R.id.button1);
    }

    //ON CLICK EVENT HANDLER TO LAUNCH THE CAMERA
    public void toCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    // CALLED ONCE THE CAMERA HAS LAUNCHED AND
    // A PHOTOGRAPH HAS BEEN TAKEN
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //REFERENCE THE IMAGEVIEW - LOCATED ON THE LAYOUT
        ImageView photoPreview = (ImageView)
                findViewById(R.id.imageView1);


        //VERIFY A PHOTO WAS TAKEN
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //COLLECT THE PHOTOGRAPH TAKEN BY THE CAMERA
                // AND PLACE IT IN THE IMAGE VIEW
                Bundle extras = data.getExtras();
                photoCaptured = (Bitmap) extras.get("data");
                photoPreview.setImageBitmap(photoCaptured);
                // store photo internally
                FileOutputStream fos = null;
                try {
                    String path = Environment.getExternalStorageDirectory().toString();
                    File file = new File(path, "ProfilePhoto.jpg");
                    fos = new FileOutputStream(file);
                    photoCaptured.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    Toast.makeText(PhotoActivity.this, "Photo saved!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) {
                            fos.close();
                            Intent returnIntent = new Intent(PhotoActivity.this, MainActivity.class);
                            startActivity(returnIntent);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "result canceled",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "image capture failed", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    public static Bitmap loadBitmapFromView(Context context, View view) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        view.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(),
                view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        view.draw(c);
        return returnedBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // INFLATE THE MENU
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
