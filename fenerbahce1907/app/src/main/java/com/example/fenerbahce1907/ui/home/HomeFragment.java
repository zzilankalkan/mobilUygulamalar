package com.example.fenerbahce1907.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.fenerbahce1907.model.Kupa;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Button> categoryButtons;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Buton listesi
        categoryButtons = Arrays.asList(
                binding.btnFutbol,
                binding.btnBasketbol,
                binding.btnVoleybol,
                binding.btnKadinFutbol,
                binding.btnKadinBasketbol,
                binding.btnKadinVoleybol
        );

        // Buton tıklamaları ve veri çekimi
        binding.btnFutbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnFutbol);
            tumRecyclerlariGizle();
            binding.layoutFutbol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("futbol", binding.recyclerFutbolKupalar);
        });

        binding.btnBasketbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnBasketbol);
            tumRecyclerlariGizle();
            binding.layoutBasketbol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("basketbol", binding.recyclerBasketbolKupalar);
        });

        binding.btnVoleybol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnVoleybol);
            tumRecyclerlariGizle();
            binding.layoutVoleybol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("voleybol", binding.recyclerVoleybolKupalar);
        });

        binding.btnKadinFutbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinFutbol);
            tumRecyclerlariGizle();
            binding.layoutKadinFutbol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("kadinFutbol", binding.recyclerKadinFutbolKupalar);
        });

        binding.btnKadinBasketbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinBasketbol);
            tumRecyclerlariGizle();
            binding.layoutKadinBasketbol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("kadinBasketbol", binding.recyclerKadinBasketbolKupalar);
        });

        binding.btnKadinVoleybol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinVoleybol);
            tumRecyclerlariGizle();
            binding.layoutKadinVoleybol.setVisibility(View.VISIBLE);
            getKupalarFromFirebase("kadinVoleybol", binding.recyclerKadinVoleybolKupalar);
        });


        // Varsayılan görünüm
        highlightSelectedButton(binding.btnFutbol);
        tumRecyclerlariGizle();
        getKupalarFromFirebase("futbol", binding.recyclerFutbolKupalar);

        return root;
    }

    private void getKupalarFromFirebase(String kategori, androidx.recyclerview.widget.RecyclerView recyclerView) {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("kupalar").child(kategori);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Kupa> liste = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Kupa kupa = data.getValue(Kupa.class);
                    if (kupa != null) {
                        liste.add(kupa);
                    }
                }

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
                recyclerView.setAdapter(new KupaAdapter(requireContext(), liste));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Veri çekme hatası: " + error.getMessage());
            }
        });
    }


    private void tumRecyclerlariGizle() {
        binding.recyclerFutbolKupalar.setVisibility(View.GONE);
        binding.recyclerBasketbolKupalar.setVisibility(View.GONE);
        binding.recyclerVoleybolKupalar.setVisibility(View.GONE);
        binding.recyclerKadinFutbolKupalar.setVisibility(View.GONE);
        binding.recyclerKadinBasketbolKupalar.setVisibility(View.GONE);
        binding.recyclerKadinVoleybolKupalar.setVisibility(View.GONE);
    }

    private void highlightSelectedButton(Button selectedBtn) {
        for (Button btn : categoryButtons) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.fener_yellow));
            btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }

        selectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.dark_blue));
        selectedBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
