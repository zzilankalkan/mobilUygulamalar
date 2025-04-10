package com.example.fenerbahce1907.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.model.Fixture;

import java.util.List;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {

    private List<Fixture> fixtureList;

    public FixtureAdapter(List<Fixture> fixtureList) {
        this.fixtureList = fixtureList;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fixture, parent, false);
        return new FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        Fixture fixture = fixtureList.get(position);

        holder.hafta.setText("Hafta " + fixture.getHafta());
        holder.takimlar.setText(fixture.getEv() + " - " + fixture.getDeplasman());
        holder.skor.setText(fixture.getSkor());
        holder.tarihSaat.setText(fixture.getTarih() + " | " + fixture.getSaat());
    }

    @Override
    public int getItemCount() {
        return fixtureList.size();
    }

    public static class FixtureViewHolder extends RecyclerView.ViewHolder {
        TextView hafta, takimlar, skor, tarihSaat;

        public FixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            hafta = itemView.findViewById(R.id.txtHafta);
            takimlar = itemView.findViewById(R.id.txtTakimlar);
            skor = itemView.findViewById(R.id.txtSkor);
            tarihSaat = itemView.findViewById(R.id.txtTarih);

        }
    }
}
