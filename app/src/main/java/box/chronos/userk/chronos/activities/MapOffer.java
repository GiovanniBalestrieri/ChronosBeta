package box.chronos.userk.chronos.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import box.chronos.userk.chronos.R;

/**
 * Created by userk on 09/05/17.
 */

public class MapOffer extends AppCompatActivity implements OnMapReadyCallback {
    // Google Map
    private GoogleMap googleMap;
    LatLng shop;
    String mess;

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
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                shop, 17);
        googleMap.addMarker(new MarkerOptions().position(shop).title(mess));
        googleMap.animateCamera(location);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
