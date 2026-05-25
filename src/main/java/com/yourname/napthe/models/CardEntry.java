package com.yourname.napthe.models;

public class CardEntry {
    private String type, serial, pin;
    private int amount;

    public CardEntry(String type, int amount, String serial, String pin) {
        this.type = type;
        this.amount = amount;
        this.serial = serial;
        this.pin = pin;
    }

    public String getType() { return type; }
    public int getAmount() { return amount; }
    public String getSerial() { return serial; }
    public String getPin() { return pin; }
}
