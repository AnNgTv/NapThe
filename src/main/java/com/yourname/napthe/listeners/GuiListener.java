package com.yourname.napthe.listeners;

import com.yourname.napthe.NapThePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiListener implements Listener {
    private final NapThePlugin plugin;

    public GuiListener(NapThePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("§0Chọn loại thẻ")) {
            event.setCancelled(true);
            if (event.getCurrentItem() == null) return;
            
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                String type = item.getItemMeta().getDisplayName().substring(2); // Bỏ "§e"
                player.closeInventory();
                player.sendMessage("§aBạn đã chọn loại thẻ: §e" + type);
                player.sendMessage("§aVui lòng dùng lệnh sau để tiếp tục:");
                player.sendMessage("§e/napthe " + type + " <mệnh giá> <seri> <mã thẻ>");
            }
        }
    }
}
