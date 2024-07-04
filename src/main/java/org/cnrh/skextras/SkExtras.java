package org.cnrh.skextras;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import org.bukkit.command.CommandExecutor;
import org.cnrh.skextras.commands.SkExtrasCmd;
import org.cnrh.skextras.listeners.PlayerJoin;
import org.cnrh.skextras.utils.*;

import org.bstats.bukkit.Metrics;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class SkExtras extends JavaPlugin {

    private static SkExtras instance;
    private String version;
    private PluginManager pluginManager;
    private SkriptAddon addon;
    private boolean latest = true;
    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        pluginManager = this.getServer().getPluginManager();
        version = this.getDescription().getVersion();
        if (!this.isSkriptEnabled()) {
            pluginManager.disablePlugin(this);
            return;
        }

        addon = Skript.registerAddon(this);

        this.registerListeners(new PlayerJoin());
        this.loadElements();
        this.loadCommands();

        long fin = System.currentTimeMillis() - start;
        Console.info(Utils.colored(Utils.getPrefix() + " loaded v" + version + " in " + df.format(fin / 1000.0) + " seconds (" +
                fin + "ms)"));
    }

    private void loadCommands() {
        Objects.requireNonNull(getCommand("skextras")).setExecutor((CommandExecutor) new SkExtrasCmd());
    }

    private void loadElements() {
        try {
            addon.loadClasses("com.cnrh.skextras.elements",
                    "expressions",
                    "effects",
                    "events",
                    "conditions");
        } catch (IOException ex) {
            Console.info("Something went horribly wrong!");
            ex.printStackTrace();
        }
    }

    private boolean isSkriptEnabled() {
        Plugin skript = pluginManager.getPlugin("Skript");
        if (skript == null) return false;
        if (!skript.isEnabled()) return false;
        return Skript.isAcceptRegistrations();
    }

    private void registerListeners(Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> this.pluginManager.registerEvents(listener, this));
    }

    public String getVersion() {
        return version;
    }

    public static SkExtras getInstance() {
        return instance;
    }

    public boolean isLatest() {
        return latest;
    }
}