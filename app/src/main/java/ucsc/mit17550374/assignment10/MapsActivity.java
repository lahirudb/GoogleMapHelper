package ucsc.mit17550374.assignment10;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText lat, lot;
    private Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lat = findViewById(R.id.lat);
        lot = findViewById(R.id.lot);
        go = findViewById(R.id.go);

        checkAccessPermissions();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (lat.getText() != null && lot.getText() != null && lat.getText().toString() != "" && lot.getText().toString() != "") {
                        // Add a marker in Sydney and move the camera

                        double v1 = Double.parseDouble(lat.getText().toString());
                        double v2 = Double.parseDouble(lot.getText().toString());
                        LatLng sydney = new LatLng(v1, v2);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in your destination"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    } else {
                        LatLng sydney = new LatLng(-34, 151);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




    }







    public boolean checkAccessPermissions() {
        try {
            if (android.os.Build.VERSION.SDK_INT >= 23) {
                return requestPermissionForAll(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean requestPermissionForAll(MapsActivity activity) {

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

        if (!hasPermissions(this, PERMISSIONS)) {

            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            return true;
        }else{
            return false;
        }

    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


}
