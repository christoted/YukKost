package com.example.ngekostkuy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ngekostkuy.Adapter.KostListAdapter;
import com.example.ngekostkuy.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment supportMapFragment;
    String name;
    private double lat;
    private double lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        Intent intent = getIntent();
        name = intent.getStringExtra(KostListAdapter.KEY_NAME);
        lat = Double.parseDouble(intent.getStringExtra(KostListAdapter.KEY_LAT));
        lng = Double.parseDouble(intent.getStringExtra(KostListAdapter.KEY_LNG));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoomLvl = 16.0f;
        LatLng latLng = new LatLng(lat, lng);

        map.addMarker(new MarkerOptions().position(latLng).title(name));
        map.setMinZoomPreference(zoomLvl);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
