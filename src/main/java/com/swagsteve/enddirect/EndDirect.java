package com.swagsteve.enddirect;

import Versions.EndDirect1_13;
import org.bukkit.*;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class EndDirect extends JavaPlugin implements Listener {

    //Instance
    private static EndDirect instance;
    public static EndDirect getInstance(){
        return instance;
    }

    //Version code
    public static EDFunctions EDFunctions;

    //Config cache
    public static Boolean isdragondead, dragonkillmessageenabled;
    public static String dragonkillmessage, overworld, end;
    public static Integer overworld_tp_y_level, end_tp_y_level, end_tp_to, overworld_tp_to;

    @Override
    public void onEnable() {

        //Enable MSG
        getLogger().info("Enabled!");

        //Instance
        instance = this;

        //Config
        try {
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        } catch (Exception e) {
            getLogger().severe("Error loading config, re-generating it...");
            resetConfig();
        }

        //Cache config values
        try {
            cacheConfig();
        } catch (Exception e) {
            resetConfig();
            cacheConfig();
        }

        //Events
        Bukkit.getPluginManager().registerEvents(this, this);

        // Commands
        this.getCommand("ed-reload").setExecutor(new ReloadCommand());

        //Version management
        String version = Bukkit.getVersion();
        boolean b = version.contains("1.13") || version.contains("1.14")
                || version.contains("1.15") || version.contains("1.16")
                || version.contains("1.17") || version.contains("1.18")
                || version.contains("1.19") || version.contains("1.20");

        if (b) {
            EDFunctions = new EndDirect1_13();
        } else {
            getServer().getPluginManager().disablePlugin(this);
            getLogger().info("Unsupported version detected! Disabling plugin...");
        }

        //Main Code
        new BukkitRunnable() {
            @Override
            public void run() {
                //Run check
                EDFunctions.runCheck();
            }
        }.runTaskTimer(this, 0L, 10L);
    }

    @EventHandler
    public void onDragonKill(EntityDeathEvent e) {
        if (e.getEntity() instanceof EnderDragon && !isdragondead) {

            if (dragonkillmessageenabled) {
                if (e.getEntity().getKiller() != null) {

                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', dragonkillmessage));
                    }
                }

                isdragondead = true;
                getConfig().set("Options.isdragondead", true);
                saveConfig();
                reloadConfig();
            }
        }
    }

    @Override
    public void onDisable() {
        //Disable MSG
        getLogger().info("Disabled!");
    }

    //Config methods
    public static void resetConfig() {
        EndDirect.getInstance().getLogger().info("Generating config...");
        EndDirect.getInstance().getConfig().options().copyDefaults(true);
        EndDirect.getInstance().saveDefaultConfig();
        EndDirect.getInstance().reloadConfig();
    }
    public static void cacheConfig() {
        // Booleans
        isdragondead = EndDirect.getInstance().getConfig().getBoolean("Options.isdragondead");
        dragonkillmessageenabled = EndDirect.getInstance().getConfig().getBoolean("Options.dragonkillmessageenabled");

        // Strings
        dragonkillmessage = EndDirect.getInstance().getConfig().getString("Options.dragonkillmessage");
        overworld = EndDirect.getInstance().getConfig().getString("Setup.overworld");
        end = EndDirect.getInstance().getConfig().getString("Setup.end");

        // Integers
        overworld_tp_y_level = EndDirect.getInstance().getConfig().getInt("Setup.overworld-tp-y-level");
        end_tp_y_level = EndDirect.getInstance().getConfig().getInt("Setup.end-tp-y-level");
        end_tp_to = EndDirect.getInstance().getConfig().getInt("Setup.end-tp-to");
        overworld_tp_to = EndDirect.getInstance().getConfig().getInt("Setup.overworld-tp-to");
    }
    public static void reloadConfiguration() {
        try {
            EndDirect.getInstance().reloadConfig();
            cacheConfig();
        } catch (Exception e) {
            resetConfig();
            cacheConfig();
        }
    }
}