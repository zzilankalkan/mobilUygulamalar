package com.example.fenerbahce1907.ui.register;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.ui.welcome.WelcomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        EditText username = root.findViewById(R.id.editTextRegisterUsername);
        EditText email = root.findViewById(R.id.editTextRegisterEmail);
        EditText birthdate = root.findViewById(R.id.editTextRegisterBirthdate);
        EditText password = root.findViewById(R.id.editTextRegisterPassword);
        Button registerButton = root.findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(v -> {
            String user = username.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String date = birthdate.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // VALIDASYON
            if (user.length() < 6) {
                Toast.makeText(getActivity(), "Kullanıcı adı en az 6 karakter olmalı", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!mail.endsWith("@gmail.com") || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(getActivity(), "Geçerli bir gmail adresi girin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (date.isEmpty() || !date.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                Toast.makeText(getActivity(), "Doğum tarihi hatalı (gg.aa.yyyy)", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pass.length() < 6 || !pass.matches(".*[a-zA-Z].*")) {
                Toast.makeText(getActivity(), "Şifre en az 6 karakter ve harf içermeli", Toast.LENGTH_SHORT).show();
                return;
            }

            // FIREBASE KAYIT
            mAuth.createUserWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            // 🔹 Firestore'a kayıt için verileri hazırla
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("username", user);
                            userData.put("email", mail);
                            userData.put("birthdate", date);

                            db.collection("users")
                                    .document(userId)
                                    .set(userData)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(getActivity(), "Kayıt başarılı", Toast.LENGTH_SHORT).show();

                                        // Hoş geldin ekranına geç
                                        Bundle bundle = new Bundle();
                                        bundle.putString("username", user);

                                        WelcomeFragment welcomeFragment = new WelcomeFragment();
                                        welcomeFragment.setArguments(bundle);

                                        requireActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragment_container, welcomeFragment)
                                                .addToBackStack(null)
                                                .commit();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(getActivity(), "Firestore kaydı başarısız: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                    );

                        } else {
                            Toast.makeText(getActivity(), "Kayıt başarısız: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        return root;
    }
}
