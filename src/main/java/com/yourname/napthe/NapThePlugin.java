package com.yourname.napthe;

import org.bukkit.plugin.java.JavaPlugin;
import com.yourname.napthe.database.DatabaseManager;
import com.yourname.napthe.api.CardProvider;
import com.yourname.napthe.api.implementations.TheSieuTocAPI;

public class NapThePlugin extends JavaPlugin {
    private DatabaseManager db;
    private CardProvider provider;

    @Override
    public void onEnable() {
        this.db = new DatabaseManager();
        this.db.connect(this);
        this.provider = new TheSieuTocAPI();
        getLogger().info("NapThe Plugin đã khởi động!");
    }

    public CardProvider getProvider() { return provider; }
}
