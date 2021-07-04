package me.ryanmood.fateuhc.placeholders.fateuhcplaceholders;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class FateUHCPlaceholders extends JavaPlugin {

    public static FateUHCPlaceholders INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        new PlaceHolderAPIExpansion().register();
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Placeholders have been registered.");
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static FateUHCPlaceholders getInstance() {
        return getPlugin(FateUHCPlaceholders.class);
    }
}
