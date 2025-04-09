package com.example.fenerbahce1907.ui.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.fenerbahce1907.R;

public class NewsDetailActivity extends AppCompatActivity {
    ImageView newsImage;
    TextView newsTitle, newsDesc;
    Button readMoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        newsImage = findViewById(R.id.detailImage);
        newsTitle = findViewById(R.id.detailTitle);
        newsDesc = findViewById(R.id.detailDesc);
        readMoreButton = findViewById(R.id.readMoreButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String image = intent.getStringExtra("image");
        String url = intent.getStringExtra("url");

        newsTitle.setText(title);
        newsDesc.setText(desc);
        Glide.with(this).load(image).into(newsImage);

        readMoreButton.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });
    }
}
