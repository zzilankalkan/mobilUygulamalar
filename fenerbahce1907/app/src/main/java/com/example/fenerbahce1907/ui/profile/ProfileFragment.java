package com.example.fenerbahce1907.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;


import com.example.fenerbahce1907.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.fenerbahce1907.ui.login.LoginFragment;



public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private TextView textUsername, textEmail, textBirthdate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        textUsername = root.findViewById(R.id.textUsername);
        textEmail = root.findViewById(R.id.textEmail);
        textBirthdate = root.findViewById(R.id.textBirthdate);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String userId = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        textUsername.setText("Kullanıcı Adı: " + documentSnapshot.getString("username"));
                        textEmail.setText("E-Posta: " + documentSnapshot.getString("email"));
                        textBirthdate.setText("Doğum Tarihi: " + documentSnapshot.getString("birthdate"));
                    } else {
                        Toast.makeText(getActivity(), "Kullanıcı bilgisi bulunamadı", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), "Veri alınamadı: " + e.getMessage(), Toast.LENGTH_LONG).show()
                );

        Button logoutButton = root.findViewById(R.id.buttonLogout);

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut(); // 🔐 Oturumu kapat
            Toast.makeText(getActivity(), "Oturum kapatıldı", Toast.LENGTH_SHORT).show();

            // Giriş ekranına geri dön
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        });



        return root;
    }

}
