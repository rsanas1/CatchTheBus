package com.example.rishi.catchthebus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    double latitude;
    double longitude;
    private Marker marker;
    private LatLng busLatLng;
    FirebaseDatabase database;
    DatabaseReference reference;
    String value=null;
    String BUS_NO;
    SupportMapFragment mapFragment;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

       // mapFragment.getMapAsync(this);

        BUS_NO = getIntent().getExtras().getString("BUS_NO");
        database=FirebaseDatabase.getInstance();

        reference=database.getReference(BUS_NO);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value=dataSnapshot.getValue(String.class);
                String[]temp=value.split(",");
                latitude=Double.parseDouble(temp[0].trim());
                longitude=Double.parseDouble(temp[1].trim());

                setOnMapReady();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mapFragment.getMapAsync(this);
    }

    public void setOnMapReady(){
        mapFragment.getMapAsync(this);
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

        // Add a marker in Sydney and move the camera
        setMarker(latitude,longitude);

    }

    public void setMarker(Double latitude,Double longitude){

        busLatLng = new LatLng(latitude, longitude);
        mMap.clear();
        marker =mMap.addMarker( new MarkerOptions().position(busLatLng).title("Marker")
                .icon(BitmapDescriptorFactory.fromBitmap( resizeMapIcons("circle",30,30))));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busLatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

    }



    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
}
