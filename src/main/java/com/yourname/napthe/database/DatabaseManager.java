package com.yourname.napthe.database;

import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private Connection connection;

    public void connect(JavaPlugin plugin) {
        try {
            File dataFile = new File(plugin.getDataFolder(), "data.db");
            if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
            
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFile.getAbsolutePath());
            Statement st = connection.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY, player TEXT, seri TEXT, status TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
