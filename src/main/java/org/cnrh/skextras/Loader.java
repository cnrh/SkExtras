package org.cnrh.skextras;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

import ch.njol.skript.util.Version;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.cnrh.skextras.utils.Utils;

public class Loader {
    private final SkExtras plugin;
    private final PluginManager pluginManager;
    private final Plugin skriptPlugin;
    private SkriptAddon addon;

    public Loader(SkExtras plugin) {
        this.plugin = plugin;
        this.pluginManager = plugin.getServer().getPluginManager();
        this.skriptPlugin = pluginManager.getPlugin("Skript");
    }

    boolean canLoadPlugin() {
        if (skriptPlugin == null) {
            Utils.log("&cDependency Skript was not found, plugin disabling.");
            return false;
        }
        if (!skriptPlugin.isEnabled()) {
            Utils.log("&cDependency Skript is not enabled, plugin disabling.");
            Utils.log("&cThis could mean SkExtras is being forced to load before Skript.");
            return false;
        }
        Version skriptVersion = Skript.getVersion();
        if (skriptVersion.isSmallerThan(new Version(2, 8))) {
            Utils.log("&cDependency Skript is outdated, plugin disabling.");
            Utils.log("&dSkExtras requires Skript 2.8+ but found Skript" + skriptVersion);
            return false;
        }
        if (!Skript.isAcceptRegistrations()) {
            Utils.log("&cSkript is no longer accepting registrations, addons can no longer be loaded!");
            return false;
        }
        loadSkriptElements();
        return true;
    }

    private void loadSkriptElements() {
        this.addon = Skript.registerAddon(this.plugin);
    }
}
