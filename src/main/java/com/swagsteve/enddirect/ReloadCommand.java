package com.swagsteve.enddirect;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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

                EndDirect.getInstance().reloadConfig();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lConfig Successfully Reloaded!"));
                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1, 1);

            } else {
                p.sendMessage("&c&lYou Don't Have Permission To Use This Command!");
            }
        }
        return false;
    }
}
