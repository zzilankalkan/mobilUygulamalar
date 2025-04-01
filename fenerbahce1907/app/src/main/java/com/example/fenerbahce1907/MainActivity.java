package com.example.fenerbahce1907;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fenerbahce1907.ui.home.HomeFragment;
import com.example.fenerbahce1907.ui.fixtures.FixturesFragment;
import com.example.fenerbahce1907.ui.news.NewsFragment;
import com.example.fenerbahce1907.ui.login.LoginFragment;
import com.example.fenerbahce1907.ui.profile.ProfileFragment;
import com.example.fenerbahce1907.ui.media.MediaFragment; // 🔹 MEDYA EKLENDİ
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Varsayılan olarak ana sayfa fragmentini yükle
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_fixtures) {
                selectedFragment = new FixturesFragment();
            } else if (item.getItemId() == R.id.nav_news) {
                selectedFragment = new NewsFragment();
            } else if (item.getItemId() == R.id.nav_media) { // 🔹 MEDYA BUTONU EKLENDİ
                selectedFragment = new MediaFragment();
            } else if (item.getItemId() == R.id.nav_fb) {
                if (mAuth.getCurrentUser() != null) {
                    selectedFragment = new ProfileFragment();
                } else {
                    selectedFragment = new LoginFragment();
                }
            }

            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }

            return true;
        });
    }
}
