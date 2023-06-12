package com.swagsteve.enddirect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (p.isOp()) {
                EndDirect.reloadConfiguration();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&dDC&r] &a&lConfig successfully reloaded!"));
            } else {
                p.sendMessage("&c&lYou don't have permission to use this command!");
            }
        }
        return false;
    }
}
