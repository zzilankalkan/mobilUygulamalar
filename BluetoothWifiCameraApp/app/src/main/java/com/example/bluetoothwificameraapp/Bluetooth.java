package com.example.bluetoothwificameraapp;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;

public class Bluetooth extends AppCompatActivity {
    Switch bluetoothSwitch;
    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        bluetoothSwitch = findViewById(R.id.bluetoothSwitch);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        bluetoothSwitch.setChecked(bluetoothAdapter != null && bluetoothAdapter.isEnabled());

        bluetoothSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) bluetoothAdapter.enable();
            else bluetoothAdapter.disable();
        });
    }
}
