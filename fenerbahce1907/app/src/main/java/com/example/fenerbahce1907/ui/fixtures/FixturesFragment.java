package com.example.fenerbahce1907.ui.fixtures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.adapter.FixtureAdapter;
import com.example.fenerbahce1907.model.Fixture;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FixturesFragment extends Fragment {

    private RecyclerView recyclerView;
    private FixtureAdapter adapter;
    private List<Fixture> fixtureList;
    private FirebaseFirestore db;

    private Button btnGecmis, btnGelecek;

    public FixturesFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fixtures, container, false);

        recyclerView = view.findViewById(R.id.recyclerFixtures);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fixtureList = new ArrayList<>();
        adapter = new FixtureAdapter(fixtureList);
        recyclerView.setAdapter(adapter);

        btnGecmis = view.findViewById(R.id.btn_gecmis);
        btnGelecek = view.findViewById(R.id.btn_gelecek);

        db = FirebaseFirestore.getInstance();

        // Varsayılan olarak tümünü göster
        loadAllFixtures();

        // GEÇMİŞ butonu
        btnGecmis.setOnClickListener(v -> filterFixtures(true));

        // GELECEK butonu
        btnGelecek.setOnClickListener(v -> filterFixtures(false));

        return view;
    }

    private void loadAllFixtures() {
        db.collection("fikstur")
                .orderBy("hafta", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    fixtureList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Fixture fixture = doc.toObject(Fixture.class);
                        fixtureList.add(fixture);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Veri alınamadı: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void filterFixtures(boolean isPast) {
        db.collection("fikstur")
                .orderBy("tarih", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    fixtureList.clear();
                    Date now = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Fixture fixture = doc.toObject(Fixture.class);
                        try {
                            Date matchDate = format.parse(fixture.getTarih());
                            if ((isPast && matchDate.before(now)) || (!isPast && matchDate.after(now))) {
                                fixtureList.add(fixture);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Filtreleme hatası: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
