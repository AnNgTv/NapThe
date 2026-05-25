package com.yourname.napthe.api;

public interface Callback {
    void onSuccess(int realAmount);
    void onFailure(String message);
}
