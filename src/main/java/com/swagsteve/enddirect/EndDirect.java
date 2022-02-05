package com.swagsteve.enddirect;

import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class EndDirect extends JavaPlugin implements Listener {

    //Instance
    private static EndDirect instance;
    public static EndDirect getInstance(){
        return instance;
    }

    @Override
    public void onEnable() {

        //Enable MSG
        System.out.println("[ED] Enabled!");

        //Instance
        instance = this;

        //Config
        this.getConfig().options().copyDefaults();
        this.getConfig().addDefault("Options.IsDragonDead", false);
        this.getConfig().addDefault("Options.DragonKillMessage", "You Have Defeated The Dragon! Travelling To The End Seems Easier Now...");
        this.getConfig().addDefault("Options.DragonKillMessageEnabled", true);
        this.getConfig().addDefault("Setup.Overworld", "world");
        this.getConfig().addDefault("Setup.End", "world_the_end");
        saveDefaultConfig();

        //Events
        Bukkit.getPluginManager().registerEvents(this, this);

        //Commmands
        this.getCommand("ed-reload").setExecutor(new ReloadCommand());

        //Main Code
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if (getConfig().getBoolean("Options.IsDragonDead") == true) {
                    for (Player p :Bukkit.getOnlinePlayers()) {

                        Double x = p.getLocation().getX();
                        Double z = p.getLocation().getZ();

                        if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.NORMAL) && p.getLocation().getY() > 319) {
                            Location endTp = new Location(Bukkit.getWorld(getConfig().getString("Setup.End")),x,20,z);
                            p.teleport(endTp);
                        } else if (p.getLocation().getWorld().getEnvironment().equals(World.Environment.THE_END) && p.getLocation().getY() < -10) {
                            Location ovwTp = new Location(Bukkit.getWorld(getConfig().getString("Setup.Overworld")),x,300,z);
                            p.teleport(ovwTp);
                        }
                    }
                }
            }
        },0L, 10L);
    }

    @EventHandler
    public void onDragonKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon && this.getConfig().getBoolean("Options.IsDragonDead") == false) {

            if (this.getConfig().getBoolean("Options.DragonKillMessageEnabled") == true) {
                if (e.getEntity().getKiller() instanceof Player) {

                    for (Player p : Bukkit.getOnlinePlayers()) {

                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("Options.DragonKillMessage")));

                    }
                }

                getConfig().set("Options.IsDragonDead", true);
                saveConfig();
                reloadConfig();
            }
        }
    }

    @Override
    public void onDisable() {

        //Enable MSG
        System.out.println("[ED] Disabled!");

        saveConfig();

    }
}
