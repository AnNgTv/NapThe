package com.yourname.napthe;

import org.bukkit.plugin.java.JavaPlugin;
import com.yourname.napthe.database.DatabaseManager;
import com.yourname.napthe.api.CardProvider;
import com.yourname.napthe.api.implementations.TheSieuTocAPI;
import com.yourname.napthe.manager.CardManager;
import com.yourname.napthe.manager.GuiManager;
import com.yourname.napthe.manager.WebManager;
import com.yourname.napthe.listeners.GuiListener;

public class NapThePlugin extends JavaPlugin {
    private DatabaseManager db;
    private CardProvider provider;
    private CardManager cardManager;
    private GuiManager guiManager;
    private WebManager webManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("napthe.html", false);
        this.db = new DatabaseManager();
        this.db.connect(this);
        
        String apiKey = getConfig().getString("api.key");
        this.provider = new TheSieuTocAPI(apiKey);
        this.cardManager = new CardManager(this);
        this.guiManager = new GuiManager(this);
        this.webManager = new WebManager(this);
        this.webManager.start();
        
        getCommand("napthe").setExecutor(new com.yourname.napthe.commands.NapTheCommand(this));
        getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        
        getLogger().info("NapThe Plugin đã khởi động!");
    }

    @Override
    public void onDisable() {
        if (webManager != null) webManager.stop();
    }

    public CardProvider getProvider() { return provider; }
    public CardManager getCardManager() { return cardManager; }
    public GuiManager getGuiManager() { return guiManager; }
}
