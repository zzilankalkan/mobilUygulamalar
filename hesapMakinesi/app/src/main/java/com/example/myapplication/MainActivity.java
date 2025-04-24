package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewInput;
    String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInput = findViewById(R.id.textViewInput);
        ViewGroup gridLayout = findViewById(R.id.gridLayout);

        // Tüm butonlara tıklama olayı bağlanıyor
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View view = gridLayout.getChildAt(i);
            if (view instanceof Button) {
                Button button = (Button) view;
                button.setOnClickListener(v -> onButtonClick(button.getText().toString()));
            }
        }
    }

    private void onButtonClick(String value) {
        switch (value) {
            case "=":
                try {
                    input = String.valueOf(eval(input));
                } catch (Exception e) {
                    input = "Hata";
                }
                break;
            case "C":
                input = "";
                break;
            default:
                input += value;
                break;
        }

        textViewInput.setText(input.isEmpty() ? "0" : input);
    }

    // Basit işlem çözümleyici
    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() { ch = (++pos < str.length()) ? str.charAt(pos) : -1; }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // toplama
                    else if (eat('-')) x -= parseTerm(); // çıkarma
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // çarpma
                    else if (eat('/')) x /= parseFactor(); // bölme
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // + işareti
                if (eat('-')) return -parseFactor(); // - işareti

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                return x;
            }
        }.parse();
    }
}
