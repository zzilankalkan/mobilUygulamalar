package com.example.plakasehirtahmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    TextView txtPlaka;
    EditText edtTahmin;
    Button btnKontrol, btnYeni;
    TextView txtSonuc;

    int dogruPlaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPlaka = findViewById(R.id.txtPlaka);
        edtTahmin = findViewById(R.id.edtTahmin);
        btnKontrol = findViewById(R.id.btnKontrol);
        btnYeni = findViewById(R.id.btnYeni);
        txtSonuc = findViewById(R.id.txtSonuc);

        yeniPlakaSec();

        btnKontrol.setOnClickListener(v -> {
            String tahmin = edtTahmin.getText().toString().trim();
            String dogruSehir = SehirVerisi.plakaSehirMap.get(dogruPlaka);

            if (tahmin.equalsIgnoreCase(dogruSehir)) {
                txtSonuc.setText("Tebrikler! Doğru cevap 🎉");
            } else {
                txtSonuc.setText("Yanlış! Doğru cevap: " + dogruSehir);
            }
        });

        btnYeni.setOnClickListener(v -> {
            yeniPlakaSec();
            edtTahmin.setText("");
            txtSonuc.setText("");
        });
    }

    private void yeniPlakaSec() {
        Random rnd = new Random();
        dogruPlaka = rnd.nextInt(81) + 1;
        txtPlaka.setText(String.format("%02d", dogruPlaka));
    }
}
