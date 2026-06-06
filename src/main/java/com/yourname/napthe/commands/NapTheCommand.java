package com.yourname.napthe.commands;

import com.yourname.napthe.NapThePlugin;
import com.yourname.napthe.models.CardEntry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NapTheCommand implements CommandExecutor {
    private final NapThePlugin plugin;

    public NapTheCommand(NapThePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Lệnh này chỉ dành cho người chơi.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            plugin.getGuiManager().openTypeSelector(player);
            return true;
        }

        if (args.length < 4) {
            player.sendMessage("§cUsage: /napthe <loại> <mệnh giá> <seri> <mã thẻ>");
            player.sendMessage("§eVí dụ: /napthe Viettel 10000 123456789 987654321");
            return true;
        }

        String type = args[0];
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("§cMệnh giá phải là một số.");
            return true;
        }
        String serial = args[2];
        String pin = args[3];

        CardEntry card = new CardEntry(type, amount, serial, pin);
        plugin.getCardManager().processCard(player, card);

        return true;
    }
}
