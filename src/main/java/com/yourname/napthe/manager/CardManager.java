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
        player.sendMessage("§eĐang gửi thẻ, vui lòng đợi...");

        plugin.getProvider().sendCard(card, new Callback() {
            @Override
            public void onSuccess(int realAmount) {
                // Phải chạy lại ở luồng chính (Sync) để thực hiện lệnh trong game
                Bukkit.getScheduler().runTask(plugin, () -> {
                    player.sendMessage("§aNạp thành công! Mệnh giá: " + realAmount);
                    // Ví dụ: Cộng tiền qua console
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " " + realAmount);
                });
            }

            @Override
            public void onFailure(String message) {
                player.sendMessage("§cThẻ lỗi: " + message);
            }
        });
    }
}
