package com.example.fenerbahce1907.api;

import com.example.fenerbahce1907.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsAPI {
    @GET("v2/everything")
    Call<NewsResponse> getNews(
            @Query("q") String query,
            @Query("language") String language,
            @Query("apiKey") String apiKey
    );
}
