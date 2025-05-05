package com.example.bluetoothwificameraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button bluetoothBtn, wifiBtn, cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothBtn = findViewById(R.id.bluetoothBtn);
        wifiBtn = findViewById(R.id.wifiBtn);
        cameraBtn = findViewById(R.id.cameraBtn);

        bluetoothBtn.setOnClickListener(v -> startActivity(new Intent(this, Bluetooth.class)));
        wifiBtn.setOnClickListener(v -> startActivity(new Intent(this, Wifi.class)));
        cameraBtn.setOnClickListener(v -> startActivity(new Intent(this, Camera.class)));
    }
}
