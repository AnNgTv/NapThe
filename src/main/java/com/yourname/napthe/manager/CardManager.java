package com.yourname.napthe.manager;

// Import class chính từ package gốc
import com.yourname.napthe.NapThePlugin;
// Import các class liên quan
import com.yourname.napthe.models.CardEntry;
import com.yourname.napthe.api.Callback;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CardManager {
    private final NapThePlugin plugin;

    // Constructor để truyền plugin vào
    public CardManager(NapThePlugin plugin) {
        this.plugin = plugin;
    }

    public void processCard(Player player, CardEntry card) {
        player.sendMessage(plugin.getConfig().getString("messages.pending").replace("&", "§"));

        plugin.getProvider().sendCard(card, new Callback() {
            @Override
            public void onSuccess(int realAmount) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    rewardPlayer(player.getName(), realAmount);
                });
            }

            @Override
            public void onFailure(String message) {
                String failedMsg = plugin.getConfig().getString("messages.failed")
                        .replace("&", "§");
                player.sendMessage(failedMsg + " (§7" + message + "§c)");
            }
        });
    }

    public void rewardPlayer(String playerName, int realAmount) {
        double rate = plugin.getConfig().getDouble("conversion-rate", 1.0);
        int points = (int) (realAmount * rate);

        Player player = Bukkit.getPlayer(playerName);
        if (player != null) {
            String successMsg = plugin.getConfig().getString("messages.success")
                    .replace("%points%", String.valueOf(points))
                    .replace("&", "§");
            player.sendMessage(successMsg);
        }

        for (String cmd : plugin.getConfig().getStringList("rewards")) {
            String finalCmd = cmd.replace("%player%", playerName)
                    .replace("%amount_fixed%", String.valueOf(points))
                    .replace("%value%", String.valueOf(realAmount));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), finalCmd);
        }
    }
}
