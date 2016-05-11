package com.example.jack.webstocks;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.io.IOException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MapActivity extends Activity
        implements  OnMapReadyCallback,GoogleMap.OnMapLoadedCallback {
    double lat = 42.6556, lng = -70.6208;
    private LatLng myLocation;
    StringBuffer locationName = new StringBuffer();
    EditText loc;
    Geocoder geocoder = null;
    List<Address> addressList = null;
    private GoogleMap theMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        geocoder = new Geocoder(this);
    }
    @Override
    public void onMapLoaded() throws SecurityException {
        // code to run when the map has loaded

        // read user's current location, if possible
        // try to get location three ways: GPS, cell/wifi network, and 'passive' mode
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5));
        theMap.setMyLocationEnabled(true);

        if (loc == null) {
            // fall back to network if GPS is not available
            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        if (loc == null) {
            // fall back to "passive" location if GPS and network are not available
            loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        if (loc == null) {
            myLocation = new LatLng(lat, lng);
            Toast.makeText(this, "Unable to access your location. Consider enabling Location in your device's Settings.", Toast.LENGTH_LONG).show();
        } else {
            double myLat = loc.getLatitude();
            double myLng = loc.getLongitude();
            myLocation = new LatLng(myLat, myLng);
        }
        //theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5));
        //theMap.setMyLocationEnabled(true);

    }

    @Override
    public void onMapReady(GoogleMap map) {    // map is loaded but not laid out yet
        this.theMap = map;
        theMap.setOnMapLoadedCallback(this);      // calls onMapLoaded when layout done
        //new stuff
        UiSettings mapSettings;
        mapSettings = theMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        theMap.setMyLocationEnabled(true);
    }

    public void doClick(View arg0) {

        // Retrieves the EditText
        loc = (EditText) findViewById(R.id.loc);
        // Retrieves the EditText's text assigns to StringBuffer
        locationName.replace(0, locationName.length(), loc.getText().toString()+" headquarters");

        // Gets one location based on text specified
        try {
            addressList = geocoder.getFromLocationName(locationName.toString(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if there is an address get long/lat
        if (addressList != null && addressList.size() > 0) {
            lat = (double) (addressList.get(0).getLatitude());
            lng = (double) (addressList.get(0).getLongitude());
        }
        if (theMap == null) {
            theMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.the_map)).getMap();
        }
        latLng = new LatLng(lat, lng);
        // puts waterfall icon at location
        theMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(loc.getText().toString()+" headquarters")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.pin)));
        theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14),
                3000, null);
    }


    public void myClickHandler(View target) {
        switch (target.getId()) {
            case R.id.terrain:
                theMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.hybrid:
                theMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.sat:
                theMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.normal:
                theMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }
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
}
