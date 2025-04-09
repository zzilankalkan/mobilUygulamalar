package com.example.fenerbahce1907.ui.fixtures;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.model.Match;
import com.example.fenerbahce1907.model.MatchResponse;
import com.example.fenerbahce1907.network.FixtureApiService;
import com.example.fenerbahce1907.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FixturesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FixtureAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);

        recyclerView = view.findViewById(R.id.recyclerFixtures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchFixturesFromApi();

        return view;
    }

    private void fetchFixturesFromApi() {
        FixtureApiService apiService = RetrofitInstance
                .getRetrofitInstance()
                .create(FixtureApiService.class);

        Call<MatchResponse> call = apiService.getSuperLigMatches(); // Süper Lig maçlarını al

        call.enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(Call<MatchResponse> call, Response<MatchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Match> matchList = response.body().getMatches();

                    // 🔍 Sadece Fenerbahçe'nin oynadığı maçları filtrele
                    // 🔍 Fenerbahçe'nin oynadığı maçları güçlü filtreyle al
                    List<Match> filteredList = new ArrayList<>();
                    // 🔍 Tüm maçlardaki takım isimlerini loglayalım
                    for (Match match : matchList) {
                        Log.d("Takımİsimleri", "🏠 " + match.getHomeTeam().name + " vs " + match.getAwayTeam().name);
                    }
                    String[] keywords = {"fenerbahce", "fenerbahçe", "fenerbahce sk", "fenerbahçe a.ş"};

                    for (Match match : matchList) {
                        String home = match.getHomeTeam().name.toLowerCase();
                        String away = match.getAwayTeam().name.toLowerCase();

                        for (String key : keywords) {
                            if (home.contains(key) || away.contains(key)) {
                                filteredList.add(match);
                                break;
                            }
                        }
                    }


                    // ♻️ Adapter'e filtrelenmiş listeyi ver
                    adapter = new FixtureAdapter(getContext(), filteredList);
                    recyclerView.setAdapter(adapter);

                    Log.d("FixturesFragment", "Fenerbahçe maçları listelendi: " + filteredList.size());
                } else {
                    Log.e("FixturesFragment", "Veri alınamadı: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MatchResponse> call, Throwable t) {
                Log.e("FixturesFragment", "API HATASI: " + t.getMessage());
            }
        });
    }
}
