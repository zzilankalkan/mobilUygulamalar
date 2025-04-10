package com.example.fenerbahce1907.ui.fixtures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.adapter.FixtureAdapter;
import com.example.fenerbahce1907.model.Fixture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PastMatchesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FixtureAdapter fixtureAdapter;
    private List<Fixture> fiksturList = new ArrayList<>();

    public PastMatchesFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_matches, container, false);

        recyclerView = view.findViewById(R.id.recyclerFixtures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fixtureAdapter = new FixtureAdapter(fiksturList);
        recyclerView.setAdapter(fixtureAdapter);

        fetchFixturesFromFirebase();

        return view;
    }

    private void fetchFixturesFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("fikstur")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    fiksturList.clear();  // Önceki verileri temizleyelim
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Fixture fixture = doc.toObject(Fixture.class);  // Firestore'dan gelen veriyi Fixture objesine dönüştür
                        if (fixture != null) {
                            fiksturList.add(fixture);  // Listeye ekle
                        }
                    }
                    fixtureAdapter.notifyDataSetChanged();  // RecyclerView'u güncelle
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Veri alınamadı: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
