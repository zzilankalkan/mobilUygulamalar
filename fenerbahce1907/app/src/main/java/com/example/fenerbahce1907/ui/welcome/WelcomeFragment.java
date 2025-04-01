package com.example.fenerbahce1907.ui.welcome;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.ui.home.HomeFragment;
import com.example.fenerbahce1907.ui.login.LoginFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class WelcomeFragment extends Fragment {

    private static final String TAG = "WelcomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_welcome, container, false);

        // Butonlar
        Button buttonBackHome = root.findViewById(R.id.buttonBackHome);
        Button logoutButton = root.findViewById(R.id.buttonLogout); // Çıkış Yap butonu
        TextView welcomeText = root.findViewById(R.id.welcomeText);

        // Kullanıcıyı al
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            String uid = user.getUid();

            // Firestore'dan kullanıcı adını çek
            db.collection("users").document(uid).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String username = documentSnapshot.getString("username");
                            if (username != null && !username.isEmpty()) {
                                welcomeText.setText("🎉 Hoş Geldin " + username + "!");
                            } else {
                                welcomeText.setText("🎉 Hoş Geldin!");
                            }
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Kullanıcı adı alınamadı: ", e);
                        welcomeText.setText("🎉 Hoş Geldin!");
                    });
        } else {
            welcomeText.setText("🎉 Hoş Geldin!");
        }

        // Ana sayfaya dön
        buttonBackHome.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        });

        // Çıkış yap → tekrar login ekranına dön
        logoutButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Çıkış yapıldı", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        });

        return root;
    }
}
