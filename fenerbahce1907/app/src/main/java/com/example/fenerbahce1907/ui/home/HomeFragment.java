package com.example.fenerbahce1907.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.databinding.FragmentHomeBinding;

import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Button> categoryButtons;
    private List<View> allLayouts;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Buton listesini oluştur
        categoryButtons = Arrays.asList(
                binding.btnFutbol,
                binding.btnBasketbol,
                binding.btnVoleybol,
                binding.btnKadinFutbol,
                binding.btnKadinBasketbol,
                binding.btnKadinVoleybol
        );

        // Kategori layoutlarını listele
        allLayouts = Arrays.asList(
                binding.layoutFutbol,
                binding.layoutBasketbol,
                binding.layoutVoleybol,
                binding.layoutKadinFutbol,
                binding.layoutKadinBasketbol,
                binding.layoutKadinVoleybol
        );

        // Her buton için tıklama davranışı
        binding.btnFutbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnFutbol);
            showOnlyLayout(binding.layoutFutbol);
        });

        binding.btnBasketbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnBasketbol);
            showOnlyLayout(binding.layoutBasketbol);
        });

        binding.btnVoleybol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnVoleybol);
            showOnlyLayout(binding.layoutVoleybol);
        });

        binding.btnKadinFutbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinFutbol);
            showOnlyLayout(binding.layoutKadinFutbol);
        });

        binding.btnKadinBasketbol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinBasketbol);
            showOnlyLayout(binding.layoutKadinBasketbol);
        });

        binding.btnKadinVoleybol.setOnClickListener(v -> {
            highlightSelectedButton(binding.btnKadinVoleybol);
            showOnlyLayout(binding.layoutKadinVoleybol);
        });

        // Başlangıç olarak FUTBOL aktif olsun
        highlightSelectedButton(binding.btnFutbol);
        showOnlyLayout(binding.layoutFutbol);

        return root;
    }

    private void highlightSelectedButton(Button selectedBtn) {
        for (Button btn : categoryButtons) {
            btn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.transparent));
            btn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
        }

        selectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.fener_yellow));
        selectedBtn.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black));
    }

    private void showOnlyLayout(View selectedLayout) {
        for (View layout : allLayouts) {
            layout.setVisibility(View.GONE);
        }
        selectedLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}