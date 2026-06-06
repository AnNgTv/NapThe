package com.yourname.napthe.api.implementations;

import com.yourname.napthe.api.CardProvider;
import com.yourname.napthe.api.Callback;
import com.yourname.napthe.models.CardEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class TheSieuTocAPI implements CardProvider {
    private final String apiKey;

    public TheSieuTocAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void sendCard(CardEntry card, Callback callback) {
        String urlString = "https://thesieutoc.net/chargingws/v2?APIkey=" + apiKey 
                    + "&type=" + card.getType() + "&menhgia=" + card.getAmount() 
                    + "&seri=" + card.getSerial() + "&pin=" + card.getPin();

        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");

                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JsonObject json = new Gson().fromJson(response.toString(), JsonObject.class);
                    if (json.get("status").getAsInt() == 0) {
                        callback.onSuccess(json.get("amount").getAsInt());
                    } else {
                        callback.onFailure(json.get("msg").getAsString());
                    }
                } else {
                    callback.onFailure("HTTP Error: " + responseCode);
                }
            } catch (Exception e) {
                callback.onFailure(e.getMessage());
            }
        });
    }
}
