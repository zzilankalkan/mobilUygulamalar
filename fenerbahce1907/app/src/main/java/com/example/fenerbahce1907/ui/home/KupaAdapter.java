package com.example.fenerbahce1907.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;

import java.util.List;
import com.example.fenerbahce1907.model.Kupa;


public class KupaAdapter extends RecyclerView.Adapter<KupaAdapter.KupaViewHolder> {

    private List<Kupa> kupaListesi;
    private Context context;

    public KupaAdapter(Context context, List<Kupa> kupaListesi) {
        this.context = context;
        this.kupaListesi = kupaListesi;
    }

    @NonNull
    @Override
    public KupaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kupa, parent, false);
        return new KupaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KupaViewHolder holder, int position) {
        Kupa kupa = kupaListesi.get(position);

        holder.kupaAd.setText(kupa.getKupaAdi());
        holder.kupaSayi.setText(String.valueOf(kupa.getKupaSayi()));

        // Drawable üzerinden ikon gösterme
        if (kupa.getIkonUrl() != null && !kupa.getIkonUrl().isEmpty()) {
            int resId = context.getResources().getIdentifier(kupa.getIkonUrl(), "drawable", context.getPackageName());
            if (resId != 0) {
                holder.kupaIcon.setImageResource(resId);
            } else {
                holder.kupaIcon.setImageResource(R.drawable.ic_trophy); // varsayılan ikon
            }
        } else {
            holder.kupaIcon.setImageResource(R.drawable.ic_trophy); // yedek ikon
        }
    }

    @Override
    public int getItemCount() {
        return kupaListesi.size();
    }

    public static class KupaViewHolder extends RecyclerView.ViewHolder {
        ImageView kupaIcon;
        TextView kupaAd, kupaSayi;

        public KupaViewHolder(@NonNull View itemView) {
            super(itemView);
            kupaIcon = itemView.findViewById(R.id.kupaIcon);
            kupaAd = itemView.findViewById(R.id.kupaAd);
            kupaSayi = itemView.findViewById(R.id.kupaSayi);
        }
    }
}
