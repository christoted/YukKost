package com.example.ngekostkuy.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ngekostkuy.Adapter.BookingKostListAdapter;
import com.example.ngekostkuy.R;

public class BookingKostListActivity extends AppCompatActivity {

    private String title = "Booking Transaction";
    private String userId;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;

    private BookingKostListAdapter bookingKostListAdapter;
    private TextView noBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_booking_kost_list);

        sharedPreferences = this.getSharedPreferences("login", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", "");

        noBook = findViewById(R.id.nobook);
        bookingKostListAdapter = new BookingKostListAdapter(this, userId);


        recyclerView = findViewById(R.id.recyclerViewBookingKostList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(bookingKostListAdapter);
        bookingKostListAdapter.notifyDataSetChanged();

        if (bookingKostListAdapter.getItemCount() > 0) {
            noBook.setVisibility(View.GONE);
        }
    }

    public void refresh(){
        onStart();
    }

}
