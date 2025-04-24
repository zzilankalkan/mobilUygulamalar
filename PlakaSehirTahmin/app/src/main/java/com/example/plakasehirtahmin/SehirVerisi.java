package com.example.plakasehirtahmin;

import java.util.HashMap;

public class SehirVerisi {
    public static HashMap<Integer, String> plakaSehirMap = new HashMap<>();

    static {
        plakaSehirMap.put(1, "Adana");
        plakaSehirMap.put(2, "Adıyaman");
        plakaSehirMap.put(3, "Afyonkarahisar");
        // ...
        plakaSehirMap.put(81, "Düzce");
    }
}
