package com.example.ngekostkuy.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.ngekostkuy.Adapter.KostListAdapter;
import com.example.ngekostkuy.Model.Kost;
import com.example.ngekostkuy.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.ngekostkuy.Storage.StorageKost.kosts;

public class KostListActivity extends AppCompatActivity {
    KostListAdapter kostListAdapter;
    RecyclerView recyclerView;
    private SharedPreferences preferences;
    private ArrayList<Kost> kosDataList;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == R.id.bookingList) {
            showBookingList();
        } else if ( item.getItemId() == R.id.signout) {
            preferences = getSharedPreferences("login",MODE_PRIVATE);
            preferences.edit().putBoolean("logged",false).apply();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showBookingList() {
        Intent intent = new Intent(KostListActivity.this,BookingKostListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

      //  kosts = new ArrayList<>();
        kosDataList = new ArrayList<>();

        String url = "https://bit.ly/2zd4uhX";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for ( int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Kost kost = new Kost();

                        int idKost = jsonObject.getInt("id");
                        String namaKost = jsonObject.getString("name");
                        String addressKost = jsonObject.getString("address");
                        String fasilitasKost = jsonObject.getString("facilities");
                        String imageURL = jsonObject.getString("image");
                        String latitude = jsonObject.getString("LAT");
                        String longitude = jsonObject.getString("LNG");
                        String hargaKost = jsonObject.getString("price");

                        kost.setId(idKost);
                        kost.setKosName(namaKost);
                        kost.setAddress(addressKost);
                        kost.setKosFacility(fasilitasKost);
                        kost.setPhoto_url(imageURL);
                        kost.setKosLatitude(latitude);
                        kost.setKosLongitude(longitude);
                        kost.setKosPrice(hargaKost);

                        kosDataList.add(kost);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                kostListAdapter = new KostListAdapter(kosDataList,getApplicationContext());
                recyclerView.setAdapter(kostListAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
