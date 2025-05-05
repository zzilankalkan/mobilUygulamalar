package com.example.bluetoothwificameraapp;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class Wifi extends AppCompatActivity {
    Switch wifiSwitch;
    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        wifiSwitch = findViewById(R.id.wifiSwitch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        wifiSwitch.setChecked(wifiManager.isWifiEnabled());

        wifiSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> wifiManager.setWifiEnabled(isChecked));
    }
}
