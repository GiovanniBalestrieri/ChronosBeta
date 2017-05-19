package box.chronos.userk.chronos.activities;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import box.chronos.userk.chronos.Manifest;
import box.chronos.userk.chronos.R;

/**
 * Created by userk on 09/05/17.
 */

public class MapOffer extends AppCompatActivity implements OnMapReadyCallback {
    // Google Map
    private GoogleMap googleMap;
    LatLng shop;
    String mess;
    private static final LatLng SYDNEY = new LatLng(-33.88,151.21);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_offer);

        String lat = getIntent().getStringExtra("latitude");
        String lon =  getIntent().getStringExtra("longitude");
        String name = getIntent().getStringExtra("busname");
        String add = getIntent().getStringExtra("address");
        mess = name + "\n"+ add;

        shop = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
        try {
            // Loading map

            // Get the SupportMapFragment and request notification
            // when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_aubergine_maps));

            if (!success) {
                Log.e("Maps", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("Nap", "Can't find style. Error: ", e);
        }


        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                shop, 17);

        //googleMap.animateCamera(location);



        // Move the camera instantly to Sydney with a zoom of 15.
        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, 15));

// Zoom in, animating the camera.
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());

// Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

// Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(shop)      // Sets the center of the map to Mountain View
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        googleMap.addMarker(new MarkerOptions().position(shop).title(mess));

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            Toast.makeText(getApplicationContext(), "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
            }
        }


        //googleMap.setMyLocationEnabled(true);


/*
        if (ContextCompat.checkSelfPermission(this,)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
