package com.swagsteve.enddirect;

import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndDirect extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        //Config
        this.getConfig().options().copyDefaults();
        this.getConfig().addDefault("Options.IsDragonDead", false);
        this.getConfig().addDefault("Options.DragonKillMessage", "You Have Defeated The Dragon! Travelling To The End Seems Easier Now...");
        saveDefaultConfig();

        //Events
        Bukkit.getPluginManager().registerEvents(this, this);

        //Main Code
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("Options.IsDragonDead") == true) {
                    for (Player p :Bukkit.getOnlinePlayers()) {

                        Double x = p.getLocation().getX();
                        Double z = p.getLocation().getZ();

                        if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL) && p.getLocation().getY() > 319) {
                            Location endTp = new Location(Bukkit.getWorld("world_the_end"),x,20,z);
                            p.teleport(endTp);
                        } else if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.THE_END) && p.getLocation().getY() < -10) {
                            Location ovwTp = new Location(Bukkit.getWorld("world"),x,300,z);
                            p.teleport(ovwTp);
                        }
                    }
                }
            }
        },0L, 15L);
    }


    @EventHandler
    public void onDragonKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon && this.getConfig().getBoolean("Options.IsDragonDead") == false) {

            if (e.getEntity().getKiller() instanceof Player) {
                Player p = e.getEntity().getKiller().getPlayer();

                p.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + this.getConfig().getString("Options.DragonKillMessage"));
            }

            getConfig().set("Options.IsDragonDead", true);
            saveConfig();
            reloadConfig();

        }
    }

    @Override
    public void onDisable() {

        saveConfig();

    }
}
