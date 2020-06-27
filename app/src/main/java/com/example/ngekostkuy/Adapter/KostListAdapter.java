package com.example.ngekostkuy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ngekostkuy.Activity.KostDetailActivity;
import com.example.ngekostkuy.Model.Kost;
import com.example.ngekostkuy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.ngekostkuy.Storage.StorageKost.kosts;

public class KostListAdapter extends RecyclerView.Adapter<KostListAdapter.ViewHolder> {

    Context mContext;
    public static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";
    public static final String KEY_FAC = "facility";
    public static final String KEY_DESC = "address";
    public static final String KEY_LAT = "lat";
    public static final String KEY_LNG = "lng";

    public KostListAdapter(ArrayList<Kost> kostDatas, Context mContext) {
        kosts = kostDatas;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public KostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.single_kost_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KostListAdapter.ViewHolder holder, final int position) {
        final Kost kost = kosts.get(position);

        holder.namaKos.setText(kost.getKosName());
        holder.hargaKos.setText(kost.getKosPrice());
        holder.fasilitasKos.setText(kost.getKosFacility());
        Picasso.with(mContext)
                .load(kost.getPhoto_url())
                .into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),KostDetailActivity.class);

                intent.putExtra(KEY_ID,kost.getId());
                intent.putExtra(KEY_NAME,kost.getKosName());
                intent.putExtra(KEY_PRICE,kost.getKosPrice());
                intent.putExtra(KEY_FAC,kost.getKosFacility());
                intent.putExtra(KEY_DESC,kost.getKosDesc());
                intent.putExtra(KEY_LAT,kost.getKosLatitude());
                intent.putExtra(KEY_LNG,kost.getKosLongitude());
                intent.putExtra(KEY_IMAGE,kost.getPhoto_url());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(intent);
                Toast.makeText(mContext, "ID Kost" + kost.getId(), Toast.LENGTH_SHORT).show();
                Log.d("KostListAdapter", "id Kos : " + kost.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return kosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView namaKos;
        TextView fasilitasKos;
        TextView hargaKos;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgKost);
            namaKos = itemView.findViewById(R.id.tvNamaKost);
            fasilitasKos = itemView.findViewById(R.id.tvFasilitasKost);
            hargaKos = itemView.findViewById(R.id.tvHargaKost);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
