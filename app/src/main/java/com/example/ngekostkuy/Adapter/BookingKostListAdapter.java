package com.example.ngekostkuy.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngekostkuy.Activity.BookingKostListActivity;
import com.example.ngekostkuy.Database.DatabaseHelper;
import com.example.ngekostkuy.Database.DatabaseTransaction;
import com.example.ngekostkuy.Model.BookingTransaction;
import com.example.ngekostkuy.Model.Kost;
import com.example.ngekostkuy.Model.User;
import com.example.ngekostkuy.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.ngekostkuy.Storage.StorageBookingTransaction.bookingTransactions;
import static com.example.ngekostkuy.Storage.StorageKost.kosts;

public class BookingKostListAdapter extends RecyclerView.Adapter<BookingKostListAdapter.ViewHolder> {

    private Context context;
    private DatabaseTransaction databaseTransaction;
    private String BookId;
    private BookingKostListActivity bookingKostListActivity;
    private ArrayList<BookingTransaction> bookingTransactionList = new ArrayList<>();

    public BookingKostListAdapter(BookingKostListActivity bookingKostListActivity, String userId) {
        this.bookingKostListActivity = bookingKostListActivity;
        this.context = bookingKostListActivity.getApplicationContext();
        databaseTransaction = new DatabaseTransaction(context);
        bookingTransactionList.addAll(databaseTransaction.filterListBooking(userId));
    }

    @NonNull
    @Override
    public BookingKostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.single_booking_kost_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingKostListAdapter.ViewHolder holder, final int position) {
     //   final BookingTransaction bookingTransaction = bookingTransactions.get(position);

        final BookingTransaction booking = bookingTransactionList.get(position);

        holder.tvIdBookingKost.setText(booking.getBookingId());
        holder.tvNamaKost.setText(booking.getKostName());
        holder.tvFasilitasKost.setText(booking.getKostFacility());
        holder.tvTglBookingKost.setText(booking.getBookingDate());

        holder.relativeLayoutBookingKost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(v.getRootView().getContext());
                alert.setTitle("Want to cancel this transaction?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show();
                                databaseTransaction.deleteBooking(booking.getBookingId());
                                bookingKostListActivity.refresh();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              //  Toast.makeText(context, "YES", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                        });

                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("count", "getItemCount: " + bookingTransactionList.size());
        return bookingTransactionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTglBookingKost;
        TextView tvIdBookingKost;
        TextView tvNamaKost;
        TextView tvFasilitasKost;
        RelativeLayout relativeLayoutBookingKost;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTglBookingKost = itemView.findViewById(R.id.tvTglBookingKost);
            tvIdBookingKost = itemView.findViewById(R.id.tvIDBookingKost);
            tvNamaKost = itemView.findViewById(R.id.tvNamaBookingKost);
            tvFasilitasKost = itemView.findViewById(R.id.tvFasilitasBookingKost);
            relativeLayoutBookingKost = itemView.findViewById(R.id.relativeLayoutBookingKost);
        }
    }
}
