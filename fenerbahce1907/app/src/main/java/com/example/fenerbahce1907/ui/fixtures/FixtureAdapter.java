package com.example.fenerbahce1907.ui.fixtures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.model.Match;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.bumptech.glide.Glide;


public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.FixtureViewHolder> {

    private Context context;
    private List<Match> matchList;

    public FixtureAdapter(Context context, List<Match> matchList) {
        this.context = context;
        this.matchList = matchList;
    }

    @NonNull
    @Override
    public FixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fixture, parent, false);
        return new FixtureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FixtureViewHolder holder, int position) {
        Match match = matchList.get(position);

        holder.textLeague.setText(match.getCompetition().name);
        holder.textHome.setText(match.getHomeTeam().name);
        holder.textAway.setText(match.getAwayTeam().name);
        holder.textDateTime.setText(formatDate(match.getUtcDate()));

        if (match.getScore() != null && match.getScore().fullTime != null
                && match.getScore().fullTime.home != null && match.getScore().fullTime.away != null) {
            holder.textScore.setText(match.getScore().fullTime.home + " - " + match.getScore().fullTime.away);
        } else {
            holder.textScore.setText("-");
        }

        // ✅ Glide ile logoları internetten yükle
        Glide.with(context)
                .load(match.getHomeTeam().crest)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageHome);

        Glide.with(context)
                .load(match.getAwayTeam().crest)
                .placeholder(R.drawable.placeholder)
                .into(holder.imageAway);

    }


    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class FixtureViewHolder extends RecyclerView.ViewHolder {
        TextView textDateTime, textLeague, textHome, textAway, textScore;
        ImageView imageHome, imageAway;

        public FixtureViewHolder(@NonNull View itemView) {
            super(itemView);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            textLeague = itemView.findViewById(R.id.textLeague);
            textHome = itemView.findViewById(R.id.textHome);
            textAway = itemView.findViewById(R.id.textAway);
            textScore = itemView.findViewById(R.id.textScore);
            imageHome = itemView.findViewById(R.id.imageHome);
            imageAway = itemView.findViewById(R.id.imageAway);
        }
    }

    // Tarihi "yyyy-MM-dd" formatından "dd/MM/yyyy HH:mm" formatına çevir
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
            inputFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
            Date date = inputFormat.parse(dateStr);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            return dateStr;
        }
    }

    // Takım adlarına göre drawable eşleştirme
    private int getTeamLogo(String teamName) {
        teamName = teamName.toLowerCase();
        if (teamName.contains("fenerbahçe")) return R.drawable.fener_logo;
        if (teamName.contains("galatasaray")) return R.drawable.gs_logo;
        if (teamName.contains("trabzon")) return R.drawable.trabzon_logo;
        if (teamName.contains("sivasspor")) return R.drawable.sivas_logo;
        return R.drawable.placeholder; // default
    }
}
