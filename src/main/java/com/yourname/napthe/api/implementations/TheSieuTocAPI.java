package com.yourname.napthe.api.implementations;

import com.yourname.napthe.api.CardProvider;
import com.yourname.napthe.api.Callback;
import com.yourname.napthe.models.CardEntry;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TheSieuTocAPI implements CardProvider {
    private final String apiKey = "YOUR_API_KEY";

    @Override
    public void sendCard(CardEntry card, Callback callback) {
        HttpClient client = HttpClient.newHttpClient();
        String url = "https://thesieutoc.net/chargingws/v2?APIkey=" + apiKey 
                    + "&type=" + card.getType() + "&menhgia=" + card.getAmount() 
                    + "&seri=" + card.getSerial() + "&pin=" + card.getPin();

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenAccept(res -> {
                JsonObject json = new Gson().fromJson(res.body(), JsonObject.class);
                if (json.get("status").getAsInt() == 00) {
                    callback.onSuccess(json.get("amount").getAsInt());
                } else {
                    callback.onFailure(json.get("msg").getAsString());
                }
            });
    }
}
