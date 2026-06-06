package com.yourname.napthe.manager;

import com.yourname.napthe.NapThePlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
    private final NapThePlugin plugin;

    public GuiManager(NapThePlugin plugin) {
        this.plugin = plugin;
    }

    public void openTypeSelector(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, "§0Chọn loại thẻ");

        inv.setItem(11, createItem(Material.PAPER, "§eViettel"));
        inv.setItem(12, createItem(Material.PAPER, "§eMobifone"));
        inv.setItem(13, createItem(Material.PAPER, "§eVinaphone"));
        inv.setItem(14, createItem(Material.PAPER, "§eZing"));
        inv.setItem(15, createItem(Material.PAPER, "§eGarena"));

        player.openInventory(inv);
    }

    private ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
