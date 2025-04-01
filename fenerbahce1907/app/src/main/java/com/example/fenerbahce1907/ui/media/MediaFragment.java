package com.example.fenerbahce1907.ui.media;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;

import java.util.ArrayList;
import java.util.List;

public class MediaFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_media, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerMedia);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<MediaItem> mediaList = new ArrayList<>();
        mediaList.add(new MediaItem("https://i.imgur.com/YasTfY5.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/xB0GL64.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/sVIua6e.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/XmM78DO.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/iigtQ0c.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/o20IDyJ.jpeg"));
        mediaList.add(new MediaItem("https://i.imgur.com/eLy6obk.jpeg"));

        MediaAdapter adapter = new MediaAdapter(mediaList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
