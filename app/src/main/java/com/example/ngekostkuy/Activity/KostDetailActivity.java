package com.example.ngekostkuy.Activity;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ngekostkuy.Adapter.KostListAdapter;
import com.example.ngekostkuy.Database.DatabaseTransaction;
import com.example.ngekostkuy.Model.BookingTransaction;
import com.example.ngekostkuy.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class KostDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private String title = "Detail Kos";

    private ImageView imageView;
    private TextView namaKos;
    private TextView fasilitasKos;
    private TextView hargaKos;
    private TextView descKos;
    private TextView latKos;
    private TextView lngKos;



    SQLiteDatabase sqLiteDatabase;
    private DatabaseTransaction databaseTransaction;

    private Button viewLocation;
    private Button bookKos;

    private String userId;
    private String name;
    private String facility;
    private String address;
    private String price;
    private String lat;
    private String lng;
    private String image;

    private Calendar calendar;
    private int year,month,day;
    private SimpleDateFormat simpleDateFormat;
    private SharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kost_detail);

        databaseTransaction = new DatabaseTransaction(getApplicationContext());

        imageView = findViewById(R.id.imgdetail);
        namaKos = findViewById(R.id.tvNamaKostDetail);
        fasilitasKos = findViewById(R.id.tvfasilitaskosdetail);
        hargaKos = findViewById(R.id.tvHargaKostDetail);
        descKos = findViewById(R.id.tvDescKostDetail);
        latKos = findViewById(R.id.tvlatitudekosdetail);
        lngKos = findViewById(R.id.tvlongtitudekosdetail);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        viewLocation = findViewById(R.id.btnlocation);
        bookKos = findViewById(R.id.btnBookingKost);

        viewLocation.setOnClickListener(this);
        bookKos.setOnClickListener(this);

        final Intent intent = getIntent();

        image = intent.getStringExtra(KostListAdapter.KEY_IMAGE);
        name = intent.getStringExtra(KostListAdapter.KEY_NAME);
        facility = intent.getStringExtra(KostListAdapter.KEY_FAC);
        address = intent.getStringExtra(KostListAdapter.KEY_DESC);
        price = intent.getStringExtra(KostListAdapter.KEY_PRICE);
        lat = intent.getStringExtra(KostListAdapter.KEY_LAT);
        lng = intent.getStringExtra(KostListAdapter.KEY_LNG);

        mySharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        userId = mySharedPreferences.getString("userId", "");

        Picasso.with(this)
                .load(image)
                .into(imageView);
        namaKos.setText(name);
        fasilitasKos.setText(facility);
        hargaKos.setText(price);
        descKos.setText(address);
        latKos.setText(lat);
        lngKos.setText(lng);
    }

    @Override
    public void onClick(View v) {
        if ( v == viewLocation) {
            Intent intent = new Intent(KostDetailActivity.this,MapActivity.class);
            intent.putExtra(KostListAdapter.KEY_LAT,lat);
            intent.putExtra(KostListAdapter.KEY_LNG,lng);
            intent.putExtra(KostListAdapter.KEY_NAME,name);
            startActivity(intent);
        } else if ( v == bookKos) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    boolean canBook = true;
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year,month,dayOfMonth);
                    String bookDate = simpleDateFormat.format(newDate.getTime());

                    if ( databaseTransaction.checkBook(userId,name,bookDate) == true) {
                        Toast toast = Toast.makeText(getApplicationContext(),"This kost has been booked",Toast.LENGTH_SHORT);
                        toast.show();
                        canBook = false;
                    }

                    if ( canBook) {
                        String BookId = "";
                     //   int size = databaseTransaction.getAllBooking().size();
                        int size = mySharedPreferences.getInt("currentidx",0);
                        if ( size < 10) {
                            BookId = "BK00"+ size;
                        } else if ( size >= 10 && size <= 99) {
                            BookId = "BK0" + size;
                        } else{
                            BookId = "BK" + size;
                        }
                        mySharedPreferences.edit().putInt("currentidx",size+1).apply();

                        BookingTransaction bookingTransaction = new BookingTransaction();
                        bookingTransaction.setBookingId(BookId);
                        bookingTransaction.setUserId(userId);
                        bookingTransaction.setKostName(name);
                        bookingTransaction.setKostFacility(facility);
                        bookingTransaction.setKostPrice(price);
                        bookingTransaction.setKostDesc(address);
                        bookingTransaction.setKostLongitude(lat);
                        bookingTransaction.setKostLongitude(lng);
                        bookingTransaction.setBookingDate(bookDate);

                        databaseTransaction.insertBooking(bookingTransaction);

                        Toast toast = Toast.makeText(getApplicationContext(),"User Id " + userId,Toast.LENGTH_SHORT);
                        Log.d("KostDetailActivity", "user id :  " + userId + "Booking id : " + BookId + " Book Date : " + bookDate
                        + "Get Kost Name " + bookingTransaction.getKostName() + "Get Kost id" + bookingTransaction.getBookingId());
                        toast.show();
                    }
                }
            },year,month,day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
    }
}
