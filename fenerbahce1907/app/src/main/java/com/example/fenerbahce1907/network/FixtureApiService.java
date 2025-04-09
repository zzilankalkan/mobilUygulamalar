package com.example.fenerbahce1907.network;

import com.example.fenerbahce1907.model.MatchResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FixtureApiService {
    // Türkiye Süper Lig maçları
    @GET("v4/competitions/SA/matches")
    Call<MatchResponse> getSuperLigMatches();

}
