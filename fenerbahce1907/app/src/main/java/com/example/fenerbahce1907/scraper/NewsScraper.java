package com.example.fenerbahce1907.scraper;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.fenerbahce1907.api.NewsAPI;
import com.example.fenerbahce1907.model.Article;
import com.example.fenerbahce1907.model.NewsResponse;
import com.example.fenerbahce1907.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsScraper {

    private static final String API_KEY = "c32c847109054a918e6edb19521bbb69";

    public static void getFenerbahceNews(MutableLiveData<List<Article>> liveData) {
        NewsAPI newsAPI = RetrofitInstance.getRetrofitInstance().create(NewsAPI.class);

        Call<NewsResponse> call = newsAPI.getNews("fenerbahçe", "tr", API_KEY);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body().getArticles());
                } else {
                    Log.e("NewsScraper", "Yanıt başarısız: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("NewsScraper", "Haber çekme hatası: ", t);
            }
        });
    }
}
