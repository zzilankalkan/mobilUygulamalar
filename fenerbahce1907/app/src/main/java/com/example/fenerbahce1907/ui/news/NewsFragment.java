package com.example.fenerbahce1907.ui.news;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.model.Article;
import com.example.fenerbahce1907.scraper.NewsScraper;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<Article> articleList = new ArrayList<>();
    private MutableLiveData<List<Article>> liveData = new MutableLiveData<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        recyclerView = view.findViewById(R.id.recyclerNews);
        adapter = new NewsAdapter(getContext(), articleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        observeData();

        return view;
    }

    private void observeData() {
        NewsScraper.getFenerbahceNews(liveData);
        liveData.observe(getViewLifecycleOwner(), articles -> {
            articleList.clear();
            articleList.addAll(articles);
            adapter.notifyDataSetChanged();
        });
    }
}
