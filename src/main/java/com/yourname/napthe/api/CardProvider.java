package com.yourname.napthe.api;

import com.yourname.napthe.models.CardEntry;

public interface CardProvider {
    void sendCard(CardEntry card, Callback callback);
}
