package com.example.fenerbahce1907.ui.login;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fenerbahce1907.R;
import com.example.fenerbahce1907.ui.register.RegisterFragment;
import com.example.fenerbahce1907.ui.welcome.WelcomeFragment;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);

        // Firebase başlat
        mAuth = FirebaseAuth.getInstance();

        // Giriş alanları
        EditText email = root.findViewById(R.id.editTextUsername);  // E-posta
        EditText password = root.findViewById(R.id.editTextPassword);
        Button loginButton = root.findViewById(R.id.buttonLogin);
        TextView registerLink = root.findViewById(R.id.textRegisterLink);

        // Giriş butonu tıklanınca
        loginButton.setOnClickListener(v -> {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            // Giriş kontrolü
            if (!mail.endsWith("@gmail.com") || !Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                Toast.makeText(getActivity(), "Geçerli bir gmail adresi girin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pass.length() < 6) {
                Toast.makeText(getActivity(), "Şifre en az 6 karakter olmalı", Toast.LENGTH_SHORT).show();
                return;
            }

            // Firebase giriş
            mAuth.signInWithEmailAndPassword(mail, pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Giriş başarılı", Toast.LENGTH_SHORT).show();

                            // Hoş geldin ekranına geç
                            Bundle bundle = new Bundle();
                            bundle.putString("username", mail);

                            WelcomeFragment welcomeFragment = new WelcomeFragment();
                            welcomeFragment.setArguments(bundle);

                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragment_container, welcomeFragment)
                                    .addToBackStack(null)
                                    .commit();

                        } else {
                            Toast.makeText(getActivity(), "Giriş başarısız: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // Kayıt Ol bağlantısı
        registerLink.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new RegisterFragment())
                    .addToBackStack(null)
                    .commit();
        });



        return root;
    }
}

